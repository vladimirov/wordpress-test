package appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    public static WebDriver initDriver(String browser) {
        switch (browser) {
            case "chrome":
            default:
                System.setProperty("webdriver.chrome.driver", "C:\\Windows\\chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setCapability("webdriver.chrome.driver", true);
                return new ChromeDriver(chromeOptions);
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "C:\\Windows\\geckodriver.exe");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setCapability("marionette", true);
                return new FirefoxDriver(firefoxOptions);
            case "edge":
                System.setProperty("webdriver.edge.driver", "C:\\Windows\\MicrosoftWebDriver.exe");
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setCapability("webdriver.edge.driver", true);
                return new EdgeDriver(edgeOptions);
        }
    }
}