package ui;

import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class SiteLinksTest extends TestBase {

    @Test
    public void checkLinksOnSite() throws IOException, GitLabApiException {
//        String title = "Yoast SEO plugin is missing";
//        app.checkIfIssueExists(title);
//        if (!app.site().sitemapPageIsAdded()) {
//            app.uploadIssueWithDescriptionToGitlab(
//                    title,
//                    app.projectProperties.getProperty("web.baseUrl") + "sitemap_index.xml" + "\n\r" + "\n\r" +
//                            "Sitemap is missing because Yoast SEO plugin isn't installed");
//        }

        String titleLinks = "Site has invalid links";
        app.checkIfIssueExists(titleLinks);
        HashSet<String> hashSet;
//        list = app.site().brokenLinks();
        hashSet = app.site().brokenHashSet();
        String description = String.valueOf(hashSet).replaceAll("[\\[\\]]", "");
        if (hashSet.size() > 0) {
            app.uploadIssueWithDescriptionToGitlab(
                    titleLinks,
                    description + "\n\r");
        }

//        app.site().hrefHashSet();
//        app.site().sitemapUrls();
//        app.site().brokenHashSet();



    }
}