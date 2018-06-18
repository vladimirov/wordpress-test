import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.io.IOException;

public class PageSpeedTest extends TestBase{

    @Test
    public void testPageSpeed() throws IOException {
        app.openPageSpeedUrl();
        app.pageSpeed().enterPageUrlToPageSpeed();
        app.pageSpeed().analyzeButtonClick();
        app.pageSpeed().waitToBePresent(By.cssSelector("div.speed-report-card"));
        app.pageSpeed().screenShot();
    }

}
