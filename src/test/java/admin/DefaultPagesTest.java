package admin;

import appmanager.TestBase;
import org.testng.annotations.Test;

import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DefaultPagesTest extends TestBase {

    @Test
    public void testDefaultPages() throws URISyntaxException, InterruptedException {
        app.loginToAdmin();

        app.admin().gotoHomePage();
        assertThat(app.admin().url(), equalTo("index.php"));

        app.admin().gotoPostsPage();
        assertThat(app.admin().url(), equalTo("edit.php"));

        app.admin().gotoMediaPage();
        assertThat(app.admin().url(), equalTo("upload.php"));

        app.admin().gotoPluginsPage();
        assertThat(app.admin().url(), equalTo("plugins.php"));

        app.admin().gotoUsersPage();
        assertThat(app.admin().url(), equalTo("users.php"));

        app.admin().gotoSettingsPage();
        assertThat(app.admin().url(), equalTo("options-general.php"));

    }

}
