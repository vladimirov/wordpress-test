package appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ApplicationManager {
    private final Properties properties;
    WebDriver driver;

    private SessionHelper sessionHelper;
    private SiteHelper siteHelper;
    private String browser;
    private PageSpeedHelper pageSpeedHelper;
    private AdminHelper adminHelper;
    private DbHelper dbHelper;
//    private ContactHelper contactHelper;
//    private GroupHelper groupHelper;

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

        pageSpeedHelper = new PageSpeedHelper(driver);
        adminHelper = new AdminHelper(driver);
        siteHelper = new SiteHelper(driver);
    }

    public void loginToAdmin() {
        driver.get(properties.getProperty("web.adminUrl"));
        sessionHelper = new SessionHelper(driver);
        sessionHelper.loginToAdmin(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
    }

    public void loginToCRM() {
        driver.get(properties.getProperty("web.crmUrl"));
        sessionHelper = new SessionHelper(driver);
        sessionHelper.loginToCrm(properties.getProperty("web.crmLogin"), properties.getProperty("web.crmPass"));
        driver.get(properties.getProperty("web.crmUrl"));
    }

    public void openBaseUrl() {
        driver.get(properties.getProperty("web.baseUrl"));
    }

    public void openPageSpeedUrl() {
        driver.get(properties.getProperty("web.pageSpeedUrl"));
    }

    public void openPageNotFoundUrl() {
        driver.get(properties.getProperty("web.baseUrl") + "404");
    }

    public void stop() {
        driver.quit();
    }

    public SiteHelper site() {
        return siteHelper;
    }

    public PageSpeedHelper pageSpeed() {
        return pageSpeedHelper;
    }

    public AdminHelper admin() {
        return adminHelper;
    }

//    public GroupHelper group() {
//        return groupHelper;
//    }

//    public ContactHelper getContactHelper() {
//        return contactHelper;
//    }

//    public ContactHelper contact() {
//        return contactHelper;
//    }

    public DbHelper db() {
        return dbHelper;
    }

}