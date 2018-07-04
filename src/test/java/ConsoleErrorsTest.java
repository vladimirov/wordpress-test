import org.testng.annotations.Test;

public class ConsoleErrorsTest extends TestBase{

    @Test
    public void testConsoleLogging(){
        app.openBaseUrl();
//        app.site().makeClick();
        app.site().screenBrowserConsole();
    }

}
