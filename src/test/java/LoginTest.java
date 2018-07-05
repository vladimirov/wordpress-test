import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends TestBase {

    @Test
    public void testLoginToAdmin() {
        TestBase.app.loginToAdmin();
        Assert.assertTrue(TestBase.app.site().adminBarIsDisplayed());
    }

}
