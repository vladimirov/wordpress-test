package appmanager;

import antlr.Tool;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.internal.Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class HelperBase {

    protected WebDriver driver;
    public WebDriverWait wait;
    public int timeOutInSeconds = 10;
    public WebElement element;


    public HelperBase(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, timeOutInSeconds);

    }

    protected void click(By locator) {
        driver.findElement(locator).click();
    }

//    protected void type(By locator, String text) {
//        click(locator);
//        if (text != null) {
//            String existingText = driver.findElement(locator).getAttribute("value");
//            if (!text.equals(existingText)) {
//                driver.findElement(locator).clear();
//                driver.findElement(locator).sendKeys(text);
//            }
//        }
//    }

    protected void type(By locator, String text) {
        click(locator);
        driver.findElement(locator).sendKeys(text);
    }

    protected void attach(By locator, File file) {
        if (file != null) {
            driver.findElement(locator).sendKeys(file.getAbsolutePath());
        }
    }

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void waitToBePresent(By locator) {
        try {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            System.out.println("ELEMENT " + locator + " HAS BEEN FOUND");
        } catch (NullPointerException ignored) {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            System.out.println("ELEMENT " + locator + " NOT FOUND");
        }
    }

    public void setText(By locator) {
        element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        element.sendKeys(Keys.chord(Keys.CONTROL, "v"), "");
    }

    public void screenShot() {
        File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filename = new SimpleDateFormat("yyyyMMddhhmmss'.png'").format(new Date());
        File dest = new File("C:\\Projects\\Wordpress/" + filename);
        Utils.copyFile(scr, dest);
    }

}
