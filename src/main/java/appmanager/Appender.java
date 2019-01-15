package appmanager;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Issue;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Appender {

    public static void main(String[] args) throws IOException, GitLabApiException {
        Properties gitlabProperties = new Properties();
        gitlabProperties.load(new FileReader(new File("src/main/resources/gitlab.properties")));




        System.out.print("\nEnter project ID: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int projectId = Integer.parseInt(reader.readLine());

        GitLabApi gitLabApi = new GitLabApi(gitlabProperties.getProperty("gitlabHostUrl"), gitlabProperties.getProperty("gitlabApiToken"));
//        Issue credentials = gitLabApi.getIssuesApi().getIssue(projectId, 313);
        Issue credentials = gitLabApi.getIssuesApi().getIssue(projectId, 1);
        PrintWriter writer = new PrintWriter("src/main/resources/local.properties");
        writer.print("");
        writer.close();

        String creds = credentials.getDescription();
        Pattern p = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",Pattern.CASE_INSENSITIVE);
//        Matcher m = p.matcher(creds);
//        m.group(1);

//        String input = "projectId = " + projectId + "\r\n" + credentials.getDescription();
//        String input = "projectId = " + projectId + "\r\n" + creds;
        String input = "projectId = " + projectId + "\r\n" + p.matcher(creds).group(1);
        Files.write(
                Paths.get("src/main/resources/local.properties"),
                input.getBytes(),
                StandardOpenOption.APPEND);
        System.out.println("\r\nSITE CREDENTIALS ARE SUCCESSFULLY ADDED FROM GITLAB\r\n");

        //TODO Add assertion that properties are added to local.properties
    }

//https://stackoverflow.com/questions/5713558/detect-and-extract-url-from-a-string

}
