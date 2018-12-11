package appmanager;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.FileUpload;
import org.gitlab4j.api.models.Project;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
    private DbHelper dbHelper;


    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/main/resources/%s.properties", target))));
        dbHelper = new DbHelper();
        switch (browser) {
            case BrowserType.CHROME: {
                System.setProperty("webdriver.chrome.driver", "C:\\Windows\\chromedriver.exe");
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                driver = new ChromeDriver(capabilities);
                break;
            }
            case BrowserType.FIREFOX: {
                System.setProperty("webdriver.chrome.driver", "C:\\Windows\\geckodriver.exe");
                DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                driver = new FirefoxDriver(capabilities);
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

    public void loginToCRM() {
        driver.get(properties.getProperty("web.crmUrl"));
        loginPage = new LoginPage(driver);
        loginPage.loginToCrm(properties.getProperty("web.crmLogin"), properties.getProperty("web.crmPass"));
        driver.get(properties.getProperty("web.crmUrl"));
    }

    public void openBaseUrl() {
        driver.get(properties.getProperty("web.baseUrl"));
    }

    public void openAdminUrl() {
        driver.get(properties.getProperty("web.baseUrl" + "wp-admin/"));
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

    public void uploadIssueWithDescriptionToGitlab(String issueTitle, String description) throws GitLabApiException {
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
                "Question",
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

    public DbHelper db() {
        return dbHelper;
    }

}