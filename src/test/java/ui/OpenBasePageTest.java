package ui;

import appmanager.TestBase;
import org.testng.annotations.Test;

public class OpenBasePageTest extends TestBase {

    @Test(enabled = false)
    public void openBasePage() throws Exception {
        app.openBaseUrl();
//        app.site().screenshotCapture("BasePage");
        app.site().screenshotCaptureAllScreen("BaseAll");
    }
}
