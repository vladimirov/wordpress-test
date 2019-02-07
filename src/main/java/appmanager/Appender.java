package appmanager;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Issue;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

public class Appender {

    @Option(name = "-id", usage = "Sets a project id")
    public static String projectId;
    public static String path;


    public static void main(String[] args) throws IOException, GitLabApiException {
        new Appender().doAppender(args);
    }

    private void doAppender(String[] args) throws IOException, GitLabApiException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            run();
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
        }
    }

    public void run() throws IOException, GitLabApiException {
        Properties localProperties = new Properties();
        localProperties.load(new FileReader(new File("src/main/resources/local.properties")));
        path = "src/main/resources/" + projectId + ".properties";

        GitLabApi gitLabApi = new GitLabApi(localProperties.getProperty("gitlabHostUrl"), localProperties.getProperty("gitlabApiToken"));
        Issue credentials = gitLabApi.getIssuesApi().getIssue(Integer.parseInt(projectId), 1);
        String creds = credentials.getDescription();
        String webBaseUrl = creds.split("\\[")[2].split("]")[0];
        String webAdminLogin = creds.split("user: ")[1].split("<")[0];
        String webAdminPass = creds.split("password: ")[1].split("<")[0];

        String input =
                "projectId = " + projectId + "\r\n" +
                        "web.baseUrl = " + webBaseUrl + "/" + "\r\n" +
                        "web.adminLogin = " + webAdminLogin + "\r\n" +
                        "web.adminPassword = " + webAdminPass;

        System.out.println();
        System.out.println("Current path is: " + path);
        System.out.println();

        try {
            Files.write(
                    Paths.get(path),
                    input.getBytes(),
                    StandardOpenOption.CREATE_NEW);
            System.out.println(" ---------------------------------------------------------------------------------------------------");
            System.out.println(" CREDENTIALS FOR " + webBaseUrl + " ARE SUCCESSFULLY ADDED ");
            System.out.println(" ---------------------------------------------------------------------------------------------------");
        } catch (FileAlreadyExistsException e) {
            System.out.println(" -----------------------------------------------------------------------------------------------");
            System.out.println(" FILE WITH CREDENTIALS ALREADY EXISTS ");
            System.out.println(" -----------------------------------------------------------------------------------------------");
        }

    }
}
