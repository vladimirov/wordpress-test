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
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;


public class Appender {

    @Option(name = "-id", usage = "Sets a project id")
    public static String projectId;
    public static String path = "src/main/resources/local.properties";

    public static void main(String[] args) throws IOException, GitLabApiException {
        new Appender().doAppender(args);
    }

    private void doAppender(String[] args) throws IOException, GitLabApiException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            this.run();
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
        }
    }

    public void run() throws IOException, GitLabApiException {
        Properties gitlabProperties = new Properties();
        gitlabProperties.load(new FileReader(new File("src/main/resources/gitlab.properties")));
        int id = Integer.parseInt(projectId);

        GitLabApi gitLabApi = new GitLabApi(gitlabProperties.getProperty("gitlabHostUrl"), gitlabProperties.getProperty("gitlabApiToken"));
        Issue credentials = gitLabApi.getIssuesApi().getIssue(id, 1);
        new PrintWriter(path).print("");

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
                StandardOpenOption.APPEND);
        System.out.println(" -------------------------------------------------------");
        System.out.println(" CREDENTIALS FOR " + webBaseUrl + " ARE SUCCESSFULLY ADDED");
        System.out.println(" -------------------------------------------------------");
    }

}
