package appmanager;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Issue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;


public class Appender {

    public static String id = System.getProperty("id");
    public static String path = "src/main/resources/" + id + ".properties";
    public static String pathTarget = "target/classes/" + id + ".properties";

    public static void main(String[] args) throws IOException, GitLabApiException {
        Properties localProperties = new Properties();
        localProperties.load(new FileReader(new File("src/main/resources/local.properties")));

        GitLabApi gitLabApi = new GitLabApi(localProperties.getProperty("gitlabHostUrl"), localProperties.getProperty("gitlabApiToken"));
        Issue credentials = gitLabApi.getIssuesApi().getIssue(Integer.parseInt(id), 1);
        String creds = credentials.getDescription();
        String webBaseUrl = creds.split("\\[")[2].split("]")[0];
        String webAdminLogin = creds.split("user: ")[1].split("<")[0];
        String webAdminPass = creds.split("password: ")[1].split("<")[0];

        String input =
                "projectId = " + id + "\r\n" +
                        "web.baseUrl = " + webBaseUrl + "/" + "\r\n" +
                        "web.adminLogin = " + webAdminLogin + "\r\n" +
                        "web.adminPassword = " + webAdminPass;

        Files.write(
                Paths.get(path),
                input.getBytes(),
                StandardOpenOption.CREATE);

        System.out.println();
        System.out.println("CREDENTIALS ARE SUCCESSFULLY PARSED FROM GITLAB");
        System.out.println();

    }

}