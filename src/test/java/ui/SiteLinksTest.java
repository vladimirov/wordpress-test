package ui;

import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.testng.annotations.Test;

import java.io.IOException;

public class SiteLinksTest extends TestBase {

    @Test
    public void checkLinksOnSite() throws IOException, GitLabApiException {
        String title = "Yoast SEO plugin is missing";
        app.checkIfIssueExists(title);
        if (!app.site().sitemapPageIsAdded()) {
            app.uploadIssueWithDescriptionToGitlab(
                    title,
                    app.projectProperties.getProperty("web.baseUrl") + "sitemap_index.xml" + "\n\r" + "\n\r" +
                            "Sitemap is missing because Yoast SEO plugin isn't installed");
        }

        String titleLinks = "Site has invalid links";
        app.checkIfIssueExists(titleLinks);
        if (app.site().sitemapPageIsAdded() && app.site().responseCodeIsValid() ) {
            app.uploadIssueWithDescriptionToGitlab(
                    titleLinks,
                     app.site().brokenLink + "\n\r");
        }
    }
}



