package api;

import appmanager.TestBase;
import com.afrozaar.wordpress.wpapi.v2.Wordpress;
import com.afrozaar.wordpress.wpapi.v2.config.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.config.ClientFactory;
import com.afrozaar.wordpress.wpapi.v2.exception.PostCreateException;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.PostStatus;
import com.afrozaar.wordpress.wpapi.v2.model.Taxonomy;
import com.afrozaar.wordpress.wpapi.v2.model.builder.ContentBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.ExcerptBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.PostBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.TitleBuilder;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static appmanager.ApplicationManager.*;

public class InvalidLinksTest extends TestBase {

    @Test(enabled = true)
    public void invalidLinksCheck() throws IOException, PostCreateException {

        final Wordpress client = ClientFactory.fromConfig(ClientConfig.of(baseUrl, adminLogin, adminPassword, false, false));

        final Post post = PostBuilder.aPost()
                .withTitle(TitleBuilder.aTitle().withRendered("Title_from_Wordpress_Java_API").build())
                .withExcerpt(ExcerptBuilder.anExcerpt().withRendered("").build())
                .withContent(ContentBuilder.aContent().withRendered(app.admin().testContent()).build())
                .build();

        final Post createdPost = client.createPost(post, PostStatus.publish);

        List<Taxonomy> taxonomies = client.getTaxonomies();

        for (Taxonomy taxonomy : taxonomies) {
            System.out.println(taxonomy.getDescription());
        }
    }

}
