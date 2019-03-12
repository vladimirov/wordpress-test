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

    @BeforeSuite
    public void credentialsAdding() throws IOException, GitLabApiException {
        Appender.credentialsParsingFromGitlab();
        Reporter.reportPropertiesCreation();
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
    public void logTestStart(Method m) {
        logger.info("START TEST " + m.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method m) {
        logger.info("STOP TEST " + m.getName());
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() throws IOException, GitLabApiException {
        app.sendSlackNotify();
//        app.sendReport();
        app.deleteProjectPropertiesFile();
    }

}
