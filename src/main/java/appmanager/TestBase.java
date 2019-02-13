package appmanager;

import org.gitlab4j.api.GitLabApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;


public class TestBase {

    protected static final ApplicationManager app = new ApplicationManager();
    Logger logger = LoggerFactory.getLogger(TestBase.class);
    public static String title;

//    @DataProvider(name = "test1")
//    public Object[] checkingExistingIssues() {
//        return new Object[]{
//                "Errors in browser console are displayed",
//                "Default pages layout screenshots in CHROME browser",
//                "Tagline has default text in admin",
//                "Favicon is missing",
//                "Errors in browser console are displayed",
//                "Yoast SEO plugin is missing",
//                "Site has invalid links",
//                "Theme screenshot is missing in admin",
//                "PageSpeed Desktop percentage value is"
//        };
//    }

//    @Test(dataProvider = "test1")
//    public void setTitle(String title){
//        TestBase.title = title;
//    }

    @BeforeSuite
    public void credentialsAdding() throws IOException, GitLabApiException {
        Appender.credentialsParsingFromGitlab();
    }

    @BeforeTest
    @Parameters("browser")
    public void setUp(@Optional("") String browser, ITestContext context) throws Exception {
        app.init(browser);
        context.setAttribute("app", app);


    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method m) throws GitLabApiException {
        logger.info("START TEST " + m.getName());
//        app.checkIssuesWithStateOpened();//TODO
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method m) {
        logger.info("STOP TEST " + m.getName());
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() throws IOException, GitLabApiException {
        app.sendSlackNotify();
        app.deleteProjectPropertiesFile();
    }

}
