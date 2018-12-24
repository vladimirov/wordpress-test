package api;

import appmanager.TestBase;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.testng.annotations.Test;

import static appmanager.ApplicationManager.gitlabApiToken;
import static appmanager.ApplicationManager.gitlabHostUrl;
import static appmanager.ApplicationManager.projectId;

public class GitlabApiTest extends TestBase {

    @Test
    public void getProjectIdViaGitlabApi() throws GitLabApiException {
        GitLabApi gitLabApi = new GitLabApi(gitlabHostUrl, gitlabApiToken);
        Project project = gitLabApi.getProjectApi().getProject(projectId);
        System.out.println(project.getName());

    }

}
