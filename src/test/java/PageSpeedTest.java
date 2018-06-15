import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.io.IOException;

public class PageSpeedTest extends TestBase{

    @Test
    public void testPageSpeed() throws IOException {
        app.openPageSpeedUrl();
        app.pageSpeed().enterPageUrlToPageSpeed();
        app.pageSpeed().analyzeButtonClick();
        app.pageSpeed().isElementPresent(By.className("tab-title"));
        app.pageSpeed().screenShot();

    }

}
