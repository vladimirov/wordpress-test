package api;

import com.ullink.slack.simpleslackapi.*;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SlackApiTest {

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(new File("src/main/resources/gitlab.properties")));
        String slackApiToken = properties.getProperty("slackApiToken");
        String slackApiBotToken = properties.getProperty("slackApiBotToken");

//        SlackSession session = SlackSessionFactory.getSlackSessionBuilder(slackApiToken).build();
//        session.connect();
//        //Send message to a Channel
//        SlackChannel channel = session.findChannelByName("simple-slack-api");
//        session.sendMessage(channel, "Hey there");
//        session.disconnect();

        SlackSession session = SlackSessionFactory.createWebSocketSlackSession(slackApiBotToken);
        session.connect();
        SlackChannel channel = session.findChannelByName("simple-slack-api"); //make sure bot is a member of the channel.
        session.sendMessage(channel, "hi im a bot" );
        session.disconnect();

    }

}
