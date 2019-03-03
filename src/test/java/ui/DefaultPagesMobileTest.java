package ui;

import appmanager.TestBase;
import org.testng.annotations.Test;

public class DefaultPagesMobileTest extends TestBase {

    @Test
    public void testDefaultPages() throws Exception {
        String title = "iPhone - Default pages layout screenshots in " + app.browserName() + " browser";
        app.checkIfIssueExists(title);
        //Search Page
        app.openSearchPageUrl();
        app.site().getPageReady();
        String searchPageScreenshot = "SearchPageTest";
//        app.site().screenshotCaptureAllScreen(searchPageScreenshot);
        app.site().screenshotCapture(searchPageScreenshot);
        String checkboxSearchLink = "* [ ] Search Page " + app.site().pageLinkForGitlab();
        String markdownSearchPage = app.getGitlabFileMarkdown(searchPageScreenshot);
        //404 Page
        app.openPageNotFoundUrl();
        app.site().getPageReady();
        String pageNotFoundScreenshot = "404PageTest";
//        app.site().screenshotCaptureAllScreen(pageNotFoundScreenshot);
        app.site().screenshotCapture(pageNotFoundScreenshot);
        String markdown404 = app.getGitlabFileMarkdown(pageNotFoundScreenshot);
        String checkbox404Link = "* [ ] 404 Page " + app.site().pageLinkForGitlab();
        //Test Post Page
//        app.admin().openTestPostUrl();
//        String testPostScreenshot = "TestPost";
//        app.site().screenshotCaptureAllScreen(testPostScreenshot);
//        String checkboxTestPostLink = "* [ ] Test Post " + app.site().pageLinkForGitlab();
//        String markdownTestPost = app.getGitlabFileMarkdown(testPostScreenshot);

//        app.uploadIssueWithDescriptionToGitlab(
//                "Default pages layout screenshots in " + app.browserName() + " browser",
//                "Browser: " + app.browserName() + "\n" + "Version: " + app.browserVersion() + "\n" +
//                        checkboxSearchLink + checkbox404Link + checkboxTestPostLink + markdownSearchPage + "\n" + markdown404 + "\n" + markdownTestPost,
//                "Questions");

        //Without Test Post Test
//        app.uploadIssueWithDescriptionToGitlab(
//                title,
//                "Browser: " + app.browserName() + "\n" + "Version: " + app.browserVersion() + "\n" +
//                        checkboxSearchLink + checkbox404Link + "\n" + markdownSearchPage + "\n" + markdown404 + "\n");


        //Appending issue
        app.updateIssueOnGitlab(
                "Default pages layout screenshots in CHROME browser",
                "\n" + "iPhone" + "\n" + markdownSearchPage + "\n" + markdown404 + "\n");


    }

}
