package ui;

import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashSet;

public class SiteLinksTest extends TestBase {

    @Test
    public void checkLinksOnSite() throws IOException, GitLabApiException {
        String title = "Yoast SEO plugin is missing";
        app.checkIfIssueExists(title);
        if (!app.site().sitemapPageIsAdded()) {
            app.uploadIssueWithDescriptionToGitlab(
                    title,
                    "Automation Tests",
                    app.projectProperties.getProperty("web.baseUrl") + "sitemap_index.xml" + "\n\r" + "\n\r" +
                            "Sitemap is missing because Yoast SEO plugin isn't installed");
        }

        String titleLinks = "Site has invalid links";
        app.checkIfIssueExists(titleLinks);
        HashSet<String> hashSet = app.site().brokenUrlsHashSet();
        if (hashSet.size() > 0) {
            app.uploadIssueWithDescriptionToGitlab(
                    titleLinks,
                    "Automation Tests",
                    String.valueOf(hashSet).replaceAll("[\\[\\]]", "").replaceAll(",", "\n\r\n\r"));
        }

//        if (app.site().sitemapUrlsWithBrokenLinks().size() > 0) {
//            app.uploadIssueWithDescriptionToGitlab(
//                    titleLinks,
//                    String.valueOf(app.site().sitemapUrlsWithBrokenLinks()).replaceAll("[\\[\\]]", ""));
//        }
    }
}