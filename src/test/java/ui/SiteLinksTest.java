package ui;

import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.testng.annotations.Test;

import java.io.IOException;

public class SiteLinksTest extends TestBase {

    @Test
    public void checkLinksOnSite() throws IOException, GitLabApiException {

        //TODO Add checking of Yoast SEO plugin
        if (!app.site().responseCodeIs200()) {
            app.uploadIssueWithDescriptionToGitlab(
                    "Site has invalid links",
                    app.site().locInternal + "\n\r");
        }
    }
}



