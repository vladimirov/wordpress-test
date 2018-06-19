import org.testng.annotations.Test;

public class PageNotFoundTest extends TestBase{

    @Test
    public void testNotFoundPage(){
        app.openPageNotFoundUrl();
        app.site().screenShot();
    }

}
