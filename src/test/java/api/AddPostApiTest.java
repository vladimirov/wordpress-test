package api;

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
import org.testng.annotations.Test;

import java.io.IOException;

public class AddPostApiTest extends TestBase {

    @Test(enabled = true)
    public void addPostViaApi() throws IOException, PostCreateException {
        String baseUrl = "http://wordpress.local";
        String username = "admin";
        String password = "12345";
        boolean debug = false;

        final Wordpress client = ClientFactory.fromConfig(ClientConfig.of(baseUrl, username, password, false, debug));

        final Post post = PostBuilder.aPost()
                .withTitle(TitleBuilder.aTitle().withRendered("Title from Wordpress Java API").build())
                .withExcerpt(ExcerptBuilder.anExcerpt().withRendered("te").build())
                .withContent(ContentBuilder.aContent().withRendered(app.admin().testContent()).build())
                .build();

        final Post createdPost = client.createPost(post, PostStatus.publish);

        System.out.println(createdPost.getLink());

    }

}
