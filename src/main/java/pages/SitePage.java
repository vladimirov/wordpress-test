package pages;

import appmanager.HelperBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;

public class SitePage extends HelperBase {

    public SitePage(WebDriver driver) {
        super(driver);
    }

    public String name = "Name " + System.currentTimeMillis();

    public void getConsoleErrors() {
        Logs logs = driver.manage().logs();
        LogEntries logEntries = logs.get(LogType.BROWSER);
        for (LogEntry logEntry : logEntries) {
            if (logEntry.getMessage().toLowerCase().contains("failed to load resource")) {
                System.out.println("Error Message in Console:" + logEntry.getMessage());
            } else if (logEntry.getMessage().toLowerCase().contains("warning")) {
                System.out.println("Warning Message in Console:" + logEntry.getMessage());
            } else {
                System.out.println("Information Message in Console:" + logEntry.getMessage());
            }
        }
    }

}

