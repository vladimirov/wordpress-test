package appmanager;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Issue;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

public class Appender {

//    public static void main(String[] args) throws IOException, GitLabApiException {
    public void appendLogger() throws IOException, GitLabApiException {
        Properties properties = new Properties();
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/main/resources/%s.properties", target))));

        System.out.print("\nEnter project ID: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int projectId = Integer.parseInt(reader.readLine());

        GitLabApi gitLabApi = new GitLabApi(properties.getProperty("gitlabHostUrl"), properties.getProperty("gitlabApiToken"));
        Issue credentials = gitLabApi.getIssuesApi().getIssue(projectId, 313);
        String input = "projectId = " + projectId + "\r\n" + credentials.getDescription();
        Files.write(
                Paths.get("src/main/resources/local.properties"),
                input.getBytes(),
                StandardOpenOption.APPEND);
    }

}
