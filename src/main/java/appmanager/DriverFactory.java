package appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    public static WebDriver initWindowsDriver(String browser) {
        switch (browser) {
            case "chrome":
            default:
                System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--window-size=1680x1050");
                chromeOptions.setCapability("webdriver.chrome.driver", true);
                return new ChromeDriver(chromeOptions);
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setCapability("marionette", true);
                return new FirefoxDriver(firefoxOptions);
            case "edge":
                System.setProperty("webdriver.edge.driver", "MicrosoftWebDriver.exe");
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setCapability("webdriver.edge.driver", true);
                return new EdgeDriver(edgeOptions);
            case "iphone":
                System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
                Map<String, String> iphoneEmulation = new HashMap<>();
                iphoneEmulation.put("deviceName", "iPhone 6/7/8");
                ChromeOptions iphoneOptions = new ChromeOptions();
                iphoneOptions.addArguments("--headless");
                iphoneOptions.addArguments("--no-sandbox");
                iphoneOptions.addArguments("--disable-dev-shm-usage");
                iphoneOptions.setExperimentalOption("mobileEmulation", iphoneEmulation);
                return new ChromeDriver(iphoneOptions);
            case "ipad":
                System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
                Map<String, String> ipadEmulation = new HashMap<>();
                ipadEmulation.put("deviceName", "iPad");
                ChromeOptions ipadOptions = new ChromeOptions();
                ipadOptions.setExperimentalOption("mobileEmulation", ipadEmulation);
                return new ChromeDriver(ipadOptions);
        }
    }

    public static WebDriver initLinuxDriver(String browser) {
        switch (browser) {
            case "chrome":
            default:
                System.setProperty("webdriver.chrome.driver", "chromedriver");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--window-size=1680x1050");
                chromeOptions.setCapability("webdriver.chrome.driver", true);
                return new ChromeDriver(chromeOptions);
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "geckodriver");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setCapability("marionette", true);
                return new FirefoxDriver(firefoxOptions);
            case "iphone":
                System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
                Map<String, String> iphoneEmulation = new HashMap<>();
                iphoneEmulation.put("deviceName", "iPhone 6/7/8");
                ChromeOptions iphoneOptions = new ChromeOptions();
                iphoneOptions.addArguments("--headless");
                iphoneOptions.setExperimentalOption("mobileEmulation", iphoneEmulation);
                return new ChromeDriver(iphoneOptions);
            case "ipad":
                System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
                Map<String, String> ipadEmulation = new HashMap<>();
                ipadEmulation.put("deviceName", "iPad");
                ChromeOptions ipadOptions = new ChromeOptions();
                ipadOptions.addArguments("--headless");
                ipadOptions.setExperimentalOption("mobileEmulation", ipadEmulation);
                return new ChromeDriver(ipadOptions);

        }
    }

    public static WebDriver initMacDriver(String browser) {
        switch (browser) {
            case "chrome":
            default:
                System.setProperty("webdriver.chrome.driver", "chromedriver-mac");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--window-size=1680x1050");
                chromeOptions.setCapability("webdriver.chrome.driver", true);
                return new ChromeDriver(chromeOptions);
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "geckodriver-mac");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setCapability("marionette", true);
                return new FirefoxDriver(firefoxOptions);
        }
    }
}