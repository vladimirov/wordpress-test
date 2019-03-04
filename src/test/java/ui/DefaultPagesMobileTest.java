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
//        app.site().screenshotCapture(searchPageScreenshot);
        app.site().screenshotCaptureEntirePage(searchPageScreenshot);
        String checkboxSearchLink = "* [ ] Search Page " + app.site().pageLinkForGitlab();
        String markdownSearchPage = app.getGitlabFileMarkdown(searchPageScreenshot);
        //404 Page
        app.openPageNotFoundUrl();
        app.site().getPageReady();
        String pageNotFoundScreenshot = "404PageTest";
//        app.site().screenshotCapture(pageNotFoundScreenshot);
        app.site().screenshotCaptureEntirePage(pageNotFoundScreenshot);
        String markdown404 = app.getGitlabFileMarkdown(pageNotFoundScreenshot);
        String checkbox404Link = "* [ ] 404 Page " + app.site().pageLinkForGitlab();

        //Without Test Post Test
        app.uploadIssueWithDescriptionToGitlab(
                title,
                "Browser: " + app.browserName() + "\n" + "Version: " + app.browserVersion() + "\n" +
                        checkboxSearchLink + checkbox404Link + "\n" + markdownSearchPage + "\n" + markdown404 + "\n");


        //Update existing issue
//        app.updateIssueOnGitlab(
//                "Default pages layout screenshots in CHROME browser",
//                "\n" + "iPhone" + "\n" + markdownSearchPage + "\n" + markdown404 + "\n");


    }

}
