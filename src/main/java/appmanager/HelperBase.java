package appmanager;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.internal.Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HelperBase {

    Logger logger = LoggerFactory.getLogger(HelperBase.class);

    protected WebDriver driver;
    public WebDriverWait wait;
    public int timeOutInSeconds = 10;
    public WebElement element;
    protected JavascriptExecutor jse = (JavascriptExecutor) driver;

    public HelperBase(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, timeOutInSeconds);
    }

    protected void click(By locator) {
        logger.info("CLICK ON ELEMENT: " + locator);
        try {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            element.click();
        } catch (StaleElementReferenceException ignored) {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            element.click();
        }
    }

    protected void type(By locator, String text) {
        logger.info("SEND TO " + locator + " KEYS " + text);
        try {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            element.click();
            element.clear();
            element.sendKeys(text);
        } catch (StaleElementReferenceException ignored) {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            element.click();
            element.clear();
            element.sendKeys(text);
        }
    }

    protected void submit(By locator) {
        logger.info("SUBMIT ELEMENT: " + locator);
        driver.findElement(locator).submit();
    }

    protected void attach(By locator, File file) {
        if (file != null) {
            driver.findElement(locator).sendKeys(file.getAbsolutePath());
        }
    }

    public void waitToBePresent(By locator) {
        try {
            logger.info("ELEMENT HAS BEEN FOUND: " + locator);
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (NullPointerException ignored) {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            logger.info("ELEMENT HAS NOT BEEN FOUND: " + locator);
        }
    }

    public void screenShot() {
        File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filename = new SimpleDateFormat("yyyyMMddhhmmss'.png'").format(new Date());
        File dest = new File("C:\\Projects\\Wordpress/" + filename);
        Utils.copyFile(scr, dest);
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

    protected boolean isTextDisplayed(String text, By locator) {
        logger.info("WAIT ELEMENT TO BE PRESENT: " + locator);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        logger.info("ACTUAL TEXT:   " + element.getText());
        logger.info("EXPECTED TEXT: " + text);
        return element.getText().equals(text);
    }

    protected void scrollUp() {
        logger.info("SCROLL UP");
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,-250)", "");

    }

    protected void scrollTillElementIsVisible(By locator) {
        logger.info("SCROLL TILL ELEMENT IS VISIBLE: " + locator);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView();", element);
    }

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

