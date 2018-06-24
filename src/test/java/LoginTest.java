import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends TestBase  {

    @Test
    public void testLoginToAdmin() {
        app.loginToAdmin();
        Assert.assertTrue(app.site().adminBarIsDisplayed());
    }

}
