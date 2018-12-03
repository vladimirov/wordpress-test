package pages;

import appmanager.HelperBase;
import org.openqa.selenium.By;
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
    private By reportSummaryLocator = By.cssSelector("div.report-summary");
    private By errorBarLocator = By.cssSelector("div.error-bar");
    private By desktopTabLocator = By.xpath("//div[contains(@class,'goog-tab goog-tab-selected')]");
    private By percentageLocator = By.cssSelector("div.lh-gauge__percentage");

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

    public void desktopTabClick() {
        click(desktopTabLocator);
    }

    public boolean reportSummaryIsDisplayed() {
        return isElementPresent(reportSummaryLocator);
    }

    public boolean errorBarIsDisplayed() {
        return isElementPresent(errorBarLocator);
    }

    public String percentValue(){
        return getElementText(percentageLocator);
    }

}
