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

    public static int projectId;
    public static String path = "src/main/resources/local.properties";

    public static void main(String[] args) throws IOException, GitLabApiException {
        Properties gitlabProperties = new Properties();
        gitlabProperties.load(new FileReader(new File("src/main/resources/gitlab.properties")));

        System.out.print("\nEnter project ID: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        projectId = Integer.parseInt(reader.readLine());

        GitLabApi gitLabApi = new GitLabApi(gitlabProperties.getProperty("gitlabHostUrl"), gitlabProperties.getProperty("gitlabApiToken"));
        Issue credentials = gitLabApi.getIssuesApi().getIssue(projectId, 1);//issue id should be always 1
        PrintWriter writer = new PrintWriter(path);
        writer.print("");
        writer.close();

        String creds = credentials.getDescription();
        String webBaseUrl = creds.split("\\[")[2].split("]")[0];
        String webAdminLogin = creds.split("user: ")[1].split("<")[0];
        String webAdminPass = creds.split("password: ")[1].split("<")[0];

        String input = "projectId = " + projectId + "\r\n" + "web.baseUrl = " + webBaseUrl + "/" + "\r\n" + "web.adminLogin = " + webAdminLogin + "\r\n" + "web.adminPassword = " + webAdminPass;
        Files.write(
                Paths.get(path),
                input.getBytes(),
                StandardOpenOption.APPEND);
        System.out.println("\r\nSITE CREDENTIALS ARE SUCCESSFULLY ADDED\r\n");
    }
}
