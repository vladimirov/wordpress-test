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
    private NavigationHelper navigationHelper;
    private String browser;
//    private DbHelper dbHelper;
//    private ContactHelper contactHelper;
//    private GroupHelper groupHelper;

    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/main/resources/%s.properties", target))));

//        dbHelper = new DbHelper();

        if (browser.equals(BrowserType.CHROME)) {
            System.setProperty("webdriver.chrome.driver", "C:\\Projects\\Addressbook\\src\\main\\resources\\chromedriver.exe");
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            driver = new ChromeDriver(capabilities);
        } else if (browser.equals(BrowserType.FIREFOX)) {
            System.setProperty("webdriver.firefox.driver", "C:\\Projects\\Addressbook\\src\\main\\resources\\geckodriver.exe");
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            driver = new FirefoxDriver(capabilities);
        } else if (browser.equals(BrowserType.IE)) {
            driver = new InternetExplorerDriver();
        }

        driver.manage().window().maximize();
        driver.get(properties.getProperty("web.baseUrl"));
        sessionHelper = new SessionHelper(driver);
//        groupHelper = new GroupHelper(driver);
//        contactHelper = new ContactHelper(driver);
        navigationHelper = new NavigationHelper(driver);
        sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));

    }

    public void stop() {
        driver.quit();
    }

    public NavigationHelper goTo() {
        return navigationHelper;
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

//    public DbHelper db() {
//        return dbHelper;
//    }

}