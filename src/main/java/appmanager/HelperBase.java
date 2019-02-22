package appmanager;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.internal.Utils;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashSet;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class HelperBase {

    public Logger logger = LoggerFactory.getLogger(HelperBase.class);

    protected WebDriver driver;
    public WebDriverWait wait;
    public int timeOutInSeconds = 10;
    public WebElement element;
    public String setLink = null;

    public HelperBase(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, timeOutInSeconds);
    }

    protected void click(By locator) {
        logger.info("CLICK ON ELEMENT: " + locator);
        try {
            element = wait.until(presenceOfElementLocated(locator));
            element.click();
        } catch (StaleElementReferenceException ignored) {
            element = wait.until(presenceOfElementLocated(locator));
            element.click();
        }
    }

    protected void type(By locator, String text) {
        logger.info("SEND TO " + locator + " KEYS " + text);
        try {
            element = wait.until(presenceOfElementLocated(locator));
            element.click();
            element.sendKeys(text);
        } catch (StaleElementReferenceException ignored) {
            element = wait.until(presenceOfElementLocated(locator));
            element.click();
            element.sendKeys(text);
        }
    }

    protected void sendKeys(By locator, String text) {
        logger.info("SEND KEYS TO ELEMENT: " + locator);
        try {
            element = wait.until(presenceOfElementLocated(locator));
            element.click();
            element.sendKeys(text);
        } catch (StaleElementReferenceException ignored) {
            element = wait.until(presenceOfElementLocated(locator));
            element.click();
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
            element = wait.until(visibilityOfElementLocated(locator));
        } catch (NullPointerException ignored) {
            logger.info("ELEMENT HAS BEEN FOUND: " + locator);
            element = wait.until(presenceOfElementLocated(locator));
        }
    }

    public void waitTillElementIsClickable(By locator) {
        logger.info("WAITING TILL ELEMENT IS CLICKABLE: " + locator);
        try {
            logger.info("ELEMENT IS CLICKABLE: " + locator);
            element = wait.until(elementToBeClickable(locator));
        } catch (TimeoutException e) {
            logger.info("ELEMENT IS CLICKABLE: " + locator);
            element = wait.until(elementToBeClickable(locator));
        }
    }

    public void waitTillElementIsVisible(By locator) {
        logger.info("WAITING TILL ELEMENT IS VISIBLE: " + locator);
        try {
            logger.info("ELEMENT IS VISIBLE: " + locator);
            element = wait.until(visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.info("ELEMENT IS VISIBLE: " + locator);
            element = wait.until(visibilityOfElementLocated(locator));
        }
    }

    public void waitTillElementIsNotVisible(By locator) {
        logger.info("WAIT TILL ELEMENT IS NOT VISIBLE: " + locator);
        wait.until(invisibilityOfElementLocated(locator));
    }

    public void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }

    public void waitForPageLoadComplete(WebDriver driver, int specifiedTimeout) {
        Wait<WebDriver> wait = new WebDriverWait(driver, specifiedTimeout);
        wait.until(driver1 -> String
                .valueOf(((JavascriptExecutor) driver1).executeScript("return document.readyState"))
                .equals("complete"));
    }

    protected void hoverOnElement(By locator) {
        logger.info("HOVER ON ELEMENT: " + locator);
        element = driver.findElement(locator);
        (new Actions(driver)).moveToElement(element).perform();
    }

    public void screenshotCapture(String screenshotName) {
        logger.info("SCREENSHOT CAPTURING...");
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File("test-screenshots/" + screenshotName + "-" + Appender.id + ".png");
        Utils.copyFile(screenshot, dest);
    }

    public void screenshotCaptureAllScreen(String screenshotName) throws IOException {
        logger.info("SCREENSHOT ALL SCREEN CAPTURING...");
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
        try {
            ImageIO.write(screenshot.getImage(), "PNG", new File("test-screenshots/" + screenshotName + "-" + Appender.id + ".png"));
        } catch (Exception e) {
//            new File("test-screenshots/").mkdirs();
            ImageIO.write(screenshot.getImage(), "PNG", new File("test-screenshots/" + screenshotName + "-" + Appender.id + ".png"));
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

    public boolean isElementVisible(By locator) {
        logger.info("TRYING TO FOUND ELEMENT: " + locator);
        try {
            logger.info("ELEMENT HAS BEEN FOUND: " + locator);
            element = wait.until(presenceOfElementLocated(locator));
            logger.info("ELEMENT HAS BEEN FOUND: " + locator);
            return true;
        } catch (NoSuchElementException e) {
            logger.info("CANT FOUND ELEMENT: " + locator);
            return false;
        }
    }

    public boolean isElementPresent(By locator) {
        logger.info("TRYING TO FOUND ELEMENT: " + locator);
        element = wait.until(presenceOfElementLocated(locator));
        logger.info("ELEMENT HAS BEEN FOUND: " + locator);
        return true;
    }

    protected boolean isTextDisplayed(By locator, String text) {
        logger.info("WAIT ELEMENT TO BE PRESENT: " + locator);
        element = wait.until(presenceOfElementLocated(locator));
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
        element = wait.until(presenceOfElementLocated(locator));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView();", element);
    }

    protected void scrollToFooter() {
        logger.info("SCROLL DOWN TO THE FOOTER");
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    protected boolean textIsDisplayed(By locator, String text) {
        logger.info("WAIT ELEMENT TO BE PRESENT: " + locator);
        element = wait.until(presenceOfElementLocated(locator));
        logger.info("ACTUAL TEXT:   " + element.getText());
        logger.info("EXPECTED TEXT: " + text);
        return element.getText().equals(text);
    }

    protected void openBrowserConsole() {
        logger.info("OPENING BROWSER CONSOLE...");
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.SHIFT, "j"));
    }

    protected boolean elementHasAttribute(By locator, String expectedValue, String attribute) {
        logger.info("WAIT ELEMENT TO BE PRESENT: " + locator);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        logger.info("EXPECTED VALUE: " + expectedValue);
        logger.info("ACTUAL VALUE:   " + element.getAttribute(attribute));
        return element.getAttribute("value").equals(expectedValue);
    }

    public boolean elementHasValue(By locator) {
        logger.info("WAITING TILL ELEMENT " + locator + " HAS VALUE");
        element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return element.getText().length() > 0;
    }

    public String getElementAttribute(By locator, String attribute) {
        logger.info("WAIT ELEMENT TO BE PRESENT: " + locator);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        logger.info("GETTING ELEMENT ATTRIBUTE: " + attribute);
        return element.getAttribute(attribute);
    }

    public String getElementAttributeValue(By locator) {
        logger.info("WAIT ELEMENT TO BE PRESENT: " + locator);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return element.getAttribute("value");
    }

    public String getElementText(By locator) {
        logger.info("WAIT ELEMENT TO BE PRESENT: " + locator);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return element.getText();
    }

    public HashSet<String> hrefHashSet(String url) throws IOException {
        HashSet<String> hashSet = new HashSet<>();
        Elements sitemaps = getElementsFromXml(url + "sitemap_index.xml");
        for (Element sitemap : sitemaps) {
            Elements sitemapUrls = getElementsFromXml(sitemap.text());
            for (Element sitemapUrl : sitemapUrls) {
                Elements hrefs = getElementsFromHtml(sitemapUrl.text());
                for (Element href : hrefs) {
                    String link = href.attr("href");
                    if (!link.contains("#") && !link.contains("tel") && !link.contains("@") && !link.isEmpty()) {
                        hashSet.add(link);
                    }
                }
            }
        }
        return hashSet;
    }

    public static Elements getElementsFromXml(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements xmlLinks = doc.select("loc");
        return xmlLinks;
    }

    public static Elements getElementsFromHtml(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements href = doc.select("a[href]");
        return href;
    }

    public static Integer responseCodeNumber(String hrefUrl) throws IOException {
        URL url = new URL(hrefUrl);
        HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();
        httpURLConnect.setConnectTimeout(7000);
        httpURLConnect.connect();
        return httpURLConnect.getResponseCode();
    }

    public static String responseMessageText(String hrefUrl) throws IOException {
        URL url = new URL(hrefUrl);
        HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();
        httpURLConnect.setConnectTimeout(3000);
        httpURLConnect.connect();
        return httpURLConnect.getResponseMessage();
    }

    protected void verifyUrl(String linkUrl) {
        try {
            URL url = new URL(linkUrl);
            HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();
            httpURLConnect.setConnectTimeout(3000);
            httpURLConnect.connect();
            if (httpURLConnect.getResponseCode() == 200) {
                System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage());
            }
            if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
                System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage() + " - " + HttpURLConnection.HTTP_NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String consoleLog() {
        String errorMessage = "";
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            errorMessage += "* [ ] " + new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage() + "\n\r";
        }
        logger.info("SEARCH FOR ERROR MESSAGES..." + "\n" + errorMessage);
        return errorMessage;
    }

    public void pasteText(By locator, String text) {
        logger.info("INSERTING TEXT TO TEXTAREA " + locator);
        try {
            element = wait.until(presenceOfElementLocated(locator));
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].value = arguments[1];", element, text);
        } catch (StaleElementReferenceException ignored) {
            element = wait.until(presenceOfElementLocated(locator));
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].value = arguments[1];", element, text);
        }
    }

    public String pageLinkForGitlab() {
        return driver.getCurrentUrl() + "\n";
    }

}

