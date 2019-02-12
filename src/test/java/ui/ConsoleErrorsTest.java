package ui;

import appmanager.Appender;
import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.Level;

public class ConsoleErrorsTest extends TestBase {

//    @BeforeMethod
//    protected void checkEnvironment() throws GitLabApiException {
//        if (app.issueIsAlreadyOpen("Errors in browser console are displayed")) {
//            throw new SkipException("Skipping test because issue with the same name is already open");
//        }
//    }

    @BeforeTest
    public void setUp() {
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
    }

    @Test
    public void consoleErrors() throws GitLabApiException {
        app.openBaseUrl();
        String errors = app.site().consoleLog();
        if (errors.length() > 0) {
            app.uploadIssueWithDescriptionToGitlab("Errors in browser console are displayed", errors);
        }

    }

}
