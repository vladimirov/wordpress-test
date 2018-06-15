import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DefaultPagesTest extends TestBase {

    @Test
    public void testDefaultPages() throws URISyntaxException {
        app.openAdminUrl();

        app.goTo().gotoHomePage();
        assertThat(app.goTo().url(), equalTo("index.php"));

        app.goTo().gotoPostsPage();
        assertThat(app.goTo().url(), equalTo("edit.php"));

        app.goTo().gotoMediaPage();
        assertThat(app.goTo().url(), equalTo("upload.php"));

//        app.goTo().gotoPagesPage();
//        System.out.println(app.goTo().url());
//        assertThat(app.goTo().url(), equalTo("edit.php?post_type=page"));

        app.goTo().gotoPluginsPage();
        assertThat(app.goTo().url(), equalTo("plugins.php"));

        app.goTo().gotoUsersPage();
        assertThat(app.goTo().url(), equalTo("users.php"));

        app.goTo().gotoSettingsPage();
        assertThat(app.goTo().url(), equalTo("options-general.php"));

    }

}
