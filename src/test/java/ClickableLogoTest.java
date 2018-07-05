import org.testng.Assert;
import org.testng.annotations.Test;

public class ClickableLogoTest extends TestBase {

    @Test
    public void testClickableLogo() {
        app.openBaseUrl();


        Assert.assertTrue(app.site().adminBarIsDisplayed());
    }

}