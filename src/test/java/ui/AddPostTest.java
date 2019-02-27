package ui;

import appmanager.TestBase;

import com.afrozaar.wordpress.wpapi.v2.Wordpress;
import com.afrozaar.wordpress.wpapi.v2.config.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.config.ClientFactory;
import com.afrozaar.wordpress.wpapi.v2.exception.PostCreateException;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.PostStatus;
import com.afrozaar.wordpress.wpapi.v2.model.builder.ContentBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.ExcerptBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.PostBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.TitleBuilder;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static appmanager.ApplicationManager.baseUrl;
import static appmanager.ApplicationManager.adminLogin;
import static appmanager.ApplicationManager.adminPassword;

public class AddPostTest extends TestBase {

    @Test()
    public void addTestPostViaApi() throws IOException, PostCreateException {
        final Wordpress client = ClientFactory.fromConfig(ClientConfig.of(baseUrl, adminLogin, adminPassword, false, false));

        final Post post = PostBuilder.aPost()
                .withTitle(TitleBuilder.aTitle().withRendered("Title_from_Wordpress_Java_API").build())
                .withExcerpt(ExcerptBuilder.anExcerpt().withRendered("").build())
                .withContent(ContentBuilder.aContent().withRendered(app.admin().testContent()).build())
                .build();

        final Post createdPost = client.createPost(post, PostStatus.publish);

        System.out.println(createdPost.getLink());

    }

}
