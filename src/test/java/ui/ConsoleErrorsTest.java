package ui;

import appmanager.TestBase;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.logging.Level;

public class ConsoleErrorsTest extends TestBase {

    @BeforeMethod
    public void  setUp(){
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
    }

    @Test
    public void testMethod() {
        app.openBaseUrl();
        app.site().analyzeLog();

    }


//    @Test
//    public void testConsoleLogging(){
//        app.openBaseUrl();
//        app.site().screenBrowserConsole();
////        app.site().getConsoleErrors();
//    }

}
