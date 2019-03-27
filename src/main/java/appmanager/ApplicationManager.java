package appmanager;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.FileUpload;
import org.gitlab4j.api.models.Issue;
import org.gitlab4j.api.models.IssueFilter;
import org.gitlab4j.api.models.Project;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;
import pages.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

public class ApplicationManager {
    public String OS = System.getProperty("os.name").toLowerCase();
    private Logger logger = LoggerFactory.getLogger(HelperBase.class);
    public String postTitle = "The quick brown fox jumps over the lazy dog " + System.currentTimeMillis();
    private WebDriver driver;
    public final Properties projectProperties;
    public final Properties localProperties;
    private LoginPage loginPage;
    private SitePage sitePage;
    private AdminPage adminPage;
    private FaviconPage faviconPage;
    private PageSpeedPage pageSpeedPage;
    public static String baseUrl;
    public static String adminLogin;
    public static String adminPassword;
    public static String gitlabHostUrl;
    public static String gitlabApiToken;
    public static String slackApiBotToken;
    public static String pageSpeedUrl;
    public static String projectId;
    public static String databaseUrl;
    public static String databaseUser;
    public static String databasePass;
    private Capabilities capabilities;


    public ApplicationManager() {
        projectProperties = new Properties();
        localProperties = new Properties();
    }

    public void init(String browser) throws IOException {
        if (OS.contains("win")) {
            driver = DriverFactory.initWindowsDriver(browser);
        } else if (OS.contains("nux")) {
            driver = DriverFactory.initLinuxDriver(browser);
        } else if (OS.contains("mac")) {
            driver = DriverFactory.initMacDriver(browser);
        } else {
            System.out.println("Unknown OS");
        }
        driver.manage().window().maximize();
        projectProperties.load(new FileReader(new File(Appender.path)));
        localProperties.load(new FileReader(new File("src/main/resources/local.properties")));
        gitlabHostUrl = localProperties.getProperty("gitlabHostUrl");
        gitlabApiToken = localProperties.getProperty("gitlabApiToken");
        slackApiBotToken = localProperties.getProperty("slackApiBotToken");
        baseUrl = projectProperties.getProperty("web.baseUrl");
        adminLogin = projectProperties.getProperty("web.adminLogin");
        adminPassword = projectProperties.getProperty("web.adminPassword");
        pageSpeedUrl = projectProperties.getProperty("web.pageSpeedUrl");
        projectId = projectProperties.getProperty("projectId");
        databaseUrl = projectProperties.getProperty("databaseUrl");
        databaseUser = projectProperties.getProperty("databaseUser");
        databasePass = projectProperties.getProperty("databasePass");
        adminPage = new AdminPage(driver);
        sitePage = new SitePage(driver);
        faviconPage = new FaviconPage(driver);
        pageSpeedPage = new PageSpeedPage(driver);
        capabilities = ((RemoteWebDriver) driver).getCapabilities();
    }

    public void stop() {
        driver.quit();
    }

    public String browserName() {
        return StringUtils.capitalize(capabilities.getBrowserName());
    }

    public String postTitle() {
        return postTitle;
    }

    public String browserVersion() {
        return capabilities.getVersion().toString();
    }

    public void loginToAdmin() {
        driver.get(baseUrl + "wp-admin/");
        loginPage = new LoginPage(driver);
        loginPage.loginToAdmin(adminLogin, adminPassword);
    }

    public void openBaseUrl() {
        driver.get(baseUrl);
    }

    public void openPostsPageInAdmin() {
        driver.get(baseUrl + "wp-admin/edit.php");
    }

    public void openTestPostUrl() {
        driver.get(baseUrl + postTitle.toLowerCase().replaceAll(" ", "-"));
    }

    public void openPageNotFoundUrl() {
        driver.get(baseUrl + "404");
    }

    public void openSearchPageUrl() {
        driver.get(baseUrl + "?s=");
    }

    public void openThemesPage() {
        driver.get(baseUrl + "wp-admin/themes.php");
    }

    public void openSettingsPage() {
        driver.get(baseUrl + "wp-admin/options-general.php");
    }

    public void openPermalinksSettings() {
        driver.get(baseUrl + "wp-admin/options-permalink.php");
    }

    public SitePage site() {
        return sitePage;
    }

    public AdminPage admin() {
        return adminPage;
    }

    public FaviconPage faviconPage() {
        return faviconPage;
    }

    public LoginPage loginPage() {
        return loginPage;
    }

    public PageSpeedPage pageSpeedPage() {
        return pageSpeedPage;
    }

    public void addPostDb(String content) {
        logger.info("CREATING TEST POST IN DATABASE ");
        try {
            String myDriver = "com.mysql.cj.jdbc.Driver";
            Class.forName(myDriver);
            Connection conn = DriverManager
                    .getConnection(databaseUrl, databaseUser, databasePass);
            Calendar calendar = Calendar.getInstance();
            Date date = new Date(calendar.getTime().getTime());
            String query = "insert into wp_posts " +
                    "(post_title, post_date, post_content, post_excerpt, to_ping, pinged, post_content_filtered, post_name) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, postTitle);
            preparedStmt.setDate(2, date);
            preparedStmt.setString(3, content);
            preparedStmt.setString(4, "");
            preparedStmt.setString(5, "");
            preparedStmt.setString(6, "");
            preparedStmt.setString(7, "");
            preparedStmt.setString(8, postTitle.toLowerCase().replaceAll(" ", "-"));
            preparedStmt.execute();
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    public String getGitlabFileMarkdown(String screenshotName) throws GitLabApiException {
        logger.info("UPLOADING SCREENSHOT TO GITLAB ");
        GitLabApi gitLabApi = new GitLabApi(gitlabHostUrl, gitlabApiToken);
        Project project = gitLabApi.getProjectApi().getProject(projectId);
        FileUpload upload = gitLabApi.getProjectApi().uploadFile(project, new File("test-screenshots/" + screenshotName + "-" + Appender.id + ".png"));
        return upload.getMarkdown();
    }

    public void uploadIssueWithDescriptionToGitlab(String issueTitle, String label, String description) throws GitLabApiException {
        logger.info("UPLOADING ISSUE TO GITLAB WITH DESCRIPTION...");
        GitLabApi gitLabApi = new GitLabApi(gitlabHostUrl, gitlabApiToken);
        Project project = gitLabApi.getProjectApi().getProject(projectId);
        gitLabApi.getIssuesApi().createIssue(
                project.getId(),
                issueTitle,
                description,
                null,
                null,
                null,
                label,
                null,
                null,
                null,
                null);
    }

    public void uploadIssueWithScreenshotToGitlab(String issueTitle, String screenshotName) throws GitLabApiException {
        logger.info("UPLOADING ISSUE TO GITLAB WITH SCREENSHOT...");
        GitLabApi gitLabApi = new GitLabApi(gitlabHostUrl, gitlabApiToken);
        Project project = gitLabApi.getProjectApi().getProject(projectId);
        FileUpload upload = gitLabApi.getProjectApi().uploadFile(project, new File("test-screenshots/" + screenshotName + "-" + Appender.id + ".png"));
        String screenUrl = upload.getUrl();
        gitLabApi.getIssuesApi().createIssue(
                project.getId(),
                issueTitle,
                upload.getMarkdown(),
                null,
                null,
                null,
                "Automation Tests",
                null,
                null,
                null,
                null);
    }

    public void updateIssueOnGitlab(String issueTitle, String description) throws GitLabApiException {
        logger.info("UPDATING ISSUE ON GITLAB...");
        issueTitle = "Default pages layout screenshots in CHROME browser";
        GitLabApi gitLabApi = new GitLabApi(gitlabHostUrl, gitlabApiToken);
        Project project = gitLabApi.getProjectApi().getProject(projectId);
        gitLabApi.getIssuesApi().updateIssue(
                project.getId(),
                0,
                issueTitle,
                description,
                false,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public void sendSlackNotify() throws IOException, GitLabApiException {
        GitLabApi gitLabApi = new GitLabApi(gitlabHostUrl, gitlabApiToken);
        Project project = gitLabApi.getProjectApi().getProject(projectId);
        SlackSession session = SlackSessionFactory.createWebSocketSlackSession(slackApiBotToken);
        session.connect();
        SlackChannel channel = session.findChannelByName("simple-tests"); //make sure bot is a member of the channel.
        session.sendMessage(channel, "Project *" + project.getName() + "* has been automatically tested. " +
                "Please go to Gitlab to see test results " + project.getWebUrl() + "/issues");
        session.disconnect();
    }

    public void sendReport() throws IOException, GitLabApiException {
        GitLabApi gitLabApi = new GitLabApi(gitlabHostUrl, gitlabApiToken);
        Project project = gitLabApi.getProjectApi().getProject(projectId);
        SlackSession session = SlackSessionFactory.createWebSocketSlackSession(slackApiBotToken);
        session.connect();
        SlackChannel channel = session.findChannelByName("simple-tests"); //make sure bot is a member of the channel.
        session.sendFile(channel, Files.readAllBytes(Paths.get("report-" + Reporter.id + ".pdf")), "Report");
        session.disconnect();
    }

    public void deleteProjectPropertiesFile() throws IOException {
        Files.delete(Paths.get(Appender.path));
        if (!new File(Appender.path).exists()) {
            logger.info("PROJECT PROPERTIES ARE SUCCESSFULLY DELETED");
        }
    }

    public List<String> getIssueTitles() throws GitLabApiException {
        GitLabApi gitLabApi = new GitLabApi(gitlabHostUrl, gitlabApiToken);
//        List<Issue> issues = gitLabApi.getIssuesApi().getIssues(projectId, new IssueFilter().withState(Constants.IssueState.OPENED));//Get all opened issue
        List<Issue> issues = gitLabApi.getIssuesApi().getIssues(projectId, new IssueFilter().withScope(Constants.IssueScope.CREATED_BY_ME));//Gets all issues created by Automation Bot
        List<String> issueTitles = new ArrayList<>();
        for (Issue issue : issues) {
            issueTitles.add(issue.getTitle());
        }
        return issueTitles;
    }

    public void checkIfIssueExists(String title) throws GitLabApiException {
        if (getIssueTitles().contains(title)) {
            logger.info("ISSUE IS ON GITLAB - " + "\"" + title + "\"");
            throw new SkipException("");
        }
    }

}
