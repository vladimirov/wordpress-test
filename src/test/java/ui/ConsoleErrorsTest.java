package ui;

import appmanager.TestBase;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.GitLabApiException;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import java.util.logging.Level;

public class ConsoleErrorsTest extends TestBase {

    @Test
    public void consoleErrors() throws GitLabApiException {
        String title = "Errors in browser console are displayed";
        app.checkIfIssueExists(title);
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        app.openBaseUrl();
        String errors = app.site().consoleLog();
        if (errors.length() > 0) {
            app.uploadIssueWithDescriptionToGitlab(
                    title,
                    "**Browser**: " + app.browserName() + "\n" + app.browserVersion() + "\n\n" +
                            "**OS**: " + StringUtils.capitalize(app.OS) + "\n\n" +
                            "**Screen size**: " + app.site().screenSize() + "\n\n" +
                            "**Link**: " + app.site().pageLinkForGitlab() + "\n\n" +
                            errors);
        }
    }

}
