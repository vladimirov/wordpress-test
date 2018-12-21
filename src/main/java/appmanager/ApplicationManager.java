package appmanager;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.FileUpload;
import org.gitlab4j.api.models.Issue;
import org.gitlab4j.api.models.Project;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Collections;
import java.util.Properties;

public class ApplicationManager {
    public final Properties properties;
    WebDriver driver;
    Logger logger = LoggerFactory.getLogger(HelperBase.class);
    private LoginPage loginPage;
    private SitePage sitePage;
    private String browser;
    private PageSpeedPage pageSpeedPage;
    private AdminPage adminPage;
    private FaviconPage faviconPage;
    public String postTitle = "The quick brown fox jumps over the lazy dog " + System.currentTimeMillis();


    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException, GitLabApiException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/main/resources/%s.properties", target))));
        switch (browser) {
            case BrowserType.CHROME: {
                System.setProperty("webdriver.chrome.driver", "C:\\Windows\\chromedriver.exe");
                ChromeOptions options = new ChromeOptions();
//                options.addArguments("--headless");
                options.setCapability("webdriver.chrome.driver", true);
                driver = new ChromeDriver(options);
                break;
            }
            case BrowserType.FIREFOX: {
                System.setProperty("webdriver.gecko.driver", "C:\\Windows\\geckodriver.exe");
                FirefoxOptions options = new FirefoxOptions();
                options.setCapability("marionette", true);
                driver = new FirefoxDriver(options);
                break;
            }
            case BrowserType.IE:
                driver = new InternetExplorerDriver();
                break;
        }
        driver.manage().window().maximize();

        pageSpeedPage = new PageSpeedPage(driver);
        adminPage = new AdminPage(driver);
        sitePage = new SitePage(driver);
        faviconPage = new FaviconPage(driver);
    }

    public void stop() {
        driver.quit();
    }

    public void loginToAdmin() {
        driver.get(properties.getProperty("web.baseUrl") + "wp-admin/");
        loginPage = new LoginPage(driver);
        loginPage.loginToAdmin(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
    }

    public void openBaseUrl() {
        driver.get(properties.getProperty("web.baseUrl"));
    }

    public void openTestPostUrl() {
        driver.get(properties.getProperty("web.baseUrl") + postTitle.toLowerCase().replaceAll(" ", "-"));
    }

    public void openPageSpeedUrl() {
        driver.get(properties.getProperty("web.pageSpeedUrl"));
    }

    public void openPageNotFoundUrl() {
        driver.get(properties.getProperty("web.baseUrl") + "404");
    }

    public void openSearchPageUrl() {
        driver.get(properties.getProperty("web.baseUrl") + "?s=");
    }

    public SitePage site() {
        return sitePage;
    }

    public PageSpeedPage pageSpeed() {
        return pageSpeedPage;
    }

    public AdminPage admin() {
        return adminPage;
    }

    public FaviconPage faviconPage() {
        return faviconPage;
    }

    public void uploadIssueWithDescriptionToGitlab(String issueTitle, String description, String label) throws GitLabApiException {
        logger.info("UPLOADING ISSUE TO GITLAB WITH DESCRIPTION");
        GitLabApi gitLabApi = new GitLabApi(properties.getProperty("gitlabHostUrl"), properties.getProperty("gitlabApiToken"));
        Project project = gitLabApi.getProjectApi().getProject(properties.getProperty("projectId"));
        gitLabApi.getIssuesApi().createIssue(
                project.getId(),
                issueTitle,
                description,
                null,
                Collections.singletonList(17),
                null,
                label,
                null,
                null,
                null,
                null);
    }

    public void uploadIssueWithScreenshotToGitlab(String issueTitle, String screenshotName) throws GitLabApiException {
        logger.info("UPLOADING ISSUE TO GITLAB WITH SCREENSHOT");
        GitLabApi gitLabApi = new GitLabApi(properties.getProperty("gitlabHostUrl"), properties.getProperty("gitlabApiToken"));
        Project project = gitLabApi.getProjectApi().getProject(properties.getProperty("projectId"));
        FileUpload upload = gitLabApi.getProjectApi().uploadFile(project, new File("test-screenshots/" + screenshotName + ".png"));
        gitLabApi.getIssuesApi().createIssue(
                project.getId(),
                issueTitle,
                upload.getMarkdown(),
                null,
                Collections.singletonList(17),
                null,
                "Question",
                null,
                null,
                null,
                null);
    }

    public String getGitlabFileMarkdown(String screenshotName) throws GitLabApiException {
        logger.info("UPLOADING SCREENSHOT TO GITLAB ");
        GitLabApi gitLabApi = new GitLabApi(properties.getProperty("gitlabHostUrl"), properties.getProperty("gitlabApiToken"));
        Project project = gitLabApi.getProjectApi().getProject(properties.getProperty("projectId"));
        FileUpload upload = gitLabApi.getProjectApi().uploadFile(project, new File("test-screenshots/" + screenshotName + ".png"));
        return upload.getMarkdown();
    }

    public void addPostDb(String content) {
        logger.info("CREATING TEST POST IN DATABASE ");
        try {
            // create a mysql database connection
            String myDriver = "com.mysql.cj.jdbc.Driver";
            Class.forName(myDriver);
            Connection conn = DriverManager
                    .getConnection(properties.getProperty("databaseUrl"), properties.getProperty("databaseUser"), properties.getProperty("databasePass"));
            // create a sql date object so we can use it in our INSERT statement
            Calendar calendar = Calendar.getInstance();
            Date date = new Date(calendar.getTime().getTime());
            // the mysql insert statement
            String query = "insert into wp_posts " +
                    "(post_title, post_date, post_content, post_excerpt, to_ping, pinged, post_content_filtered, post_name) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?)";
            // create the mysql insert prepared statement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, postTitle);
            preparedStmt.setDate(2, date);
            preparedStmt.setString(3, content);
            preparedStmt.setString(4, "");
            preparedStmt.setString(5, "");
            preparedStmt.setString(6, "");
            preparedStmt.setString(7, "");
            preparedStmt.setString(8, postTitle.toLowerCase().replaceAll(" ", "-"));
            // execute the prepared statement
            preparedStmt.execute();
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }
}