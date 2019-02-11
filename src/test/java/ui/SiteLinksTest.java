package ui;

import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.testng.annotations.Test;

import java.io.IOException;

public class SiteLinksTest extends TestBase {

    @Test
    public void checkLinksOnSite() throws IOException, GitLabApiException {

        if (!app.site().sitemapPageIsAdded()) {
            app.uploadIssueWithDescriptionToGitlab(
                    "Yoast SEO plugin is missing",
                    app.projectProperties.getProperty("web.baseUrl") + "sitemap_index.xml" + "\n\r" +
                            "Sitemap is missing because Yoast SEO plugin isn't added");
        }

        if (app.site().sitemapPageIsAdded() && !app.site().responseCodeIs200()) {
            app.uploadIssueWithDescriptionToGitlab(
                    "Site has invalid links",
                    app.site().locInternal + "\n\r");
        }
    }
}



