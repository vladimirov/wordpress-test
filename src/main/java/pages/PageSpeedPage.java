package pages;

import appmanager.HelperBase;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PageSpeedPage extends HelperBase {

    private final Properties properties;

    public PageSpeedPage(WebDriver driver) {
        super(driver);
        properties = new Properties();
    }

    private By analyzeButtonLocator = By.className("analyze-cell");
    private By urlInputLocator = By.name("url");
    private By progressStatusLocator = By.cssSelector("div.jfk-progressStatus");
    private By speedReportCardLocator = By.cssSelector("div.speed-report-card");
    private By errorBarLocator = By.cssSelector("div.error-bar");

    public void enterPageUrlToPageSpeed() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/main/resources/%s.properties", target))));
        type(urlInputLocator, properties.getProperty("web.baseUrl"));
        System.out.println(properties.getProperty("web.baseUrl"));
    }

    public void analyzeButtonClick() {
        click(analyzeButtonLocator);
    }

    public void waitTillAnalyzing(){
        try{
            waitToBePresent(progressStatusLocator);
            waitTillElementIsNotVisible(progressStatusLocator);
        } catch (TimeoutException e) {
            waitTillElementIsNotVisible(progressStatusLocator);
        }
    }

    public boolean speedReportCardIsDisplayed() {
        return isElementPresent(speedReportCardLocator);
    }

    public boolean errorBarIsDisplayed() {
        return isElementPresent(errorBarLocator);
    }


}
