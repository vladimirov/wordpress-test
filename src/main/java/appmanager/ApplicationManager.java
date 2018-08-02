package appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ApplicationManager {
    public final Properties properties;
    WebDriver driver;

    private LoginPage loginPage;
    private SitePage sitePage;
    private String browser;
    private PageSpeedPage pageSpeedPage;
    private AdminPage adminPage;
    private FaviconPage faviconPage;
//    private DbHelper dbHelper;

    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/main/resources/%s.properties", target))));

//        dbHelper = new DbHelper();

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
        driver.get(properties.getProperty("web.baseUrl") + "?s=test");
    }


    public void openTestUrl()  {
        driver.get(properties.getProperty("web.test"));
    }


    public void stop() {
        driver.quit();
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

//    public DbHelper db() {
//        return dbHelper;
//    }

}