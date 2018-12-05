package ui;

import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.logging.Level;

public class ConsoleErrorsTest extends TestBase {

    @BeforeMethod
    public void setUp() {
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
    }

    @Test
    public void testMethod() throws GitLabApiException {
        app.openBaseUrl();
        app.uploadIssueWithDescriptionToGitlab("Errors in browser console are displayed", app.site().analyzeLog());
    }

}
