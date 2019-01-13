package api;

import appmanager.TestBase;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;

public class AddPostDbTest extends TestBase {

    @Test (enabled = false)
    public void testPostCreation() throws Exception {
        app.addPostDb(app.admin().testContent());
        app.openTestPostUrl();
        app.site().screenshotCaptureAllScreen("TestPost");
    }

}
