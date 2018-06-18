package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.net.URI;
import java.net.URISyntaxException;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver driver) {
        super(driver);
    }

//    public void groupPage() {
//        if (isElementPresent(By.tagName("h1"))
//                && driver.findElement(By.tagName("h1")).getText().equals("Groups")
//                && isElementPresent(By.name("new"))) {
//            return;
//        }
//        click(By.linkText("groups"));
//    }

    public String url() throws URISyntaxException {
        URI uri = new URI(String.valueOf(driver.getCurrentUrl()));
        String path = uri.getPath();
        String pageName = path.substring(path.lastIndexOf('/') + 1);
        return String.valueOf(pageName);
    }


    public void gotoHomePage() {
        click(By.linkText("Home"));
    }

    public void gotoPostsPage() {
        click(By.linkText("Posts"));
    }

    public void gotoMediaPage() {
        click(By.linkText("Media"));
    }

    public void gotoPluginsPage() {
        click(By.xpath("//a[@href='plugins.php']"));
    }

    public void gotoUsersPage() {
        click(By.linkText("Users"));
    }

    public void gotoSettingsPage() {
        click(By.linkText("Settings"));
    }

    public void copyTestContent() {
        click(By.id("js-copy-button"));
    }

    public void addNewPostButtonClick() {
        click(By.xpath("//a[@href='post-new.php']"));
    }

}

