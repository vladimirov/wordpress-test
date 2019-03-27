package ui;

import appmanager.TestBase;
import org.apache.commons.exec.OS;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.message.internal.StringBuilderUtils;
import org.testng.annotations.Test;

public class DefaultPagesMobileTest extends TestBase {

    @Test
    public void testDefaultPages() throws Exception {
        String title = "iPhone 6/7/8 - Default pages layout screenshots in " + app.browserName() + " browser";
        app.checkIfIssueExists(title);
        //Search Page
        app.openSearchPageUrl();
        app.site().getPageReady();
        String searchPageScreenshot = "SearchPageTest";
        app.site().screenshotCapture(searchPageScreenshot);
//        app.site().screenshotCaptureEntirePage(searchPageScreenshot);
        String checkboxSearchLink = "* [ ] Search Page " + app.site().pageLinkForGitlab();
        String markdownSearchPage = app.getGitlabFileMarkdown(searchPageScreenshot);
        //404 Page
        app.openPageNotFoundUrl();
        app.site().getPageReady();
        String pageNotFoundScreenshot = "404PageTest";
        app.site().screenshotCapture(pageNotFoundScreenshot);
//        app.site().screenshotCaptureEntirePage(pageNotFoundScreenshot);
        String markdown404 = app.getGitlabFileMarkdown(pageNotFoundScreenshot);
        String checkbox404Link = "* [ ] 404 Page " + app.site().pageLinkForGitlab();

        //Without Test Post Test
        app.uploadIssueWithDescriptionToGitlab(
                title,
                "Question",
                "**Browser**: " + app.browserName() + "\n" + app.browserVersion() + "\n\n" +
                        "**OS**: " + StringUtils.capitalize(app.OS) + "\n\n" +
                        "**Screen size**: " + app.site().screenSize() + "\n\n" + "\n" +
                        checkboxSearchLink + checkbox404Link + "\n" + markdownSearchPage + "\n" + markdown404 + "\n");


        //Update existing issue
//        app.updateIssueOnGitlab(
//                "Default pages layout screenshots in CHROME browser",
//                "\n" + "iPhone" + "\n" + markdownSearchPage + "\n" + markdown404 + "\n");


    }

}
