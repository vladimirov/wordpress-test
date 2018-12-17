package api;

import appmanager.TestBase;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class GitlabApiTest extends TestBase {

    @Test
    public void getProjectIdViaGitlabApi() throws IOException, GitLabApiException {
        Properties properties = new Properties();
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/main/resources/%s.properties", target))));


        GitLabApi gitLabApi = new GitLabApi(properties.getProperty("gitlabHostUrl"), properties.getProperty("gitlabApiToken"));

        Project project = gitLabApi.getProjectApi().getProject("490");

        System.out.println(project.getName());

    }

}
