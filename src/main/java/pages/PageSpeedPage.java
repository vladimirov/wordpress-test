package pages;

import appmanager.HelperBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
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
    private By desktopTabLocator = By.xpath("//div[text()='Desktop']");
    private By percentageLocator = By.xpath("//div[@class='lh-gauge__percentage']");

    public void enterPageUrlToPageSpeed() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/main/resources/%s.properties", target))));
//        type(urlInputLocator, properties.getProperty("web.baseUrl"));
        //TODO Remove web.baseUrlToTestPageSpeed
        type(urlInputLocator, properties.getProperty("web.baseUrlToTestPageSpeed"));
    }

    public void analyzeButtonClick() {
        click(analyzeButtonLocator);
    }

//    public void waitTillAnalyzing() {
//        try {
//            waitToBePresent(progressStatusLocator);
//        } catch (Exception e) {
//            waitToBePresent(progressStatusLocator);
//        }
//    }

    public void percentageIsPresent(){
        try {
            elementHasValue(percentageLocator);
        } catch (Exception e){
            elementHasValue(percentageLocator);
        } finally {
            elementHasValue(percentageLocator);
        }
    }

    public void desktopTabClick() {
        try {
            click(desktopTabLocator);
        } catch (Exception e) {
            waitToBePresent(desktopTabLocator);
            click(desktopTabLocator);
        }
    }

    public String desktopPercent() {
        try {
            List<WebElement> percents = driver.findElements(percentageLocator);
            return percents.get(1).getText();
        } catch (IndexOutOfBoundsException e) {
            List<WebElement> percents = driver.findElements(percentageLocator);
            return percents.get(1).getText();
        }
    }

}
