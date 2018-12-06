package ui;

import appmanager.HelperBase;
import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.logging.Level;

public class ConsoleErrorsTest extends TestBase {

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
