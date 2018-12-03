package ui;

import appmanager.TestBase;
import org.testng.annotations.Test;

public class ConsoleErrorsTest extends TestBase {

    @Test
    public void testConsoleLogging(){
//        app.openBaseUrl();
//        app.site().screenBrowserConsole();
        app.site().getConsoleErrors();
    }

}
