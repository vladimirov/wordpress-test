package db;

import appmanager.TestBase;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;

public class AddPostDbTest extends TestBase {

    @Test
    public void testPostCreation() {
//        Posts before = app.db().posts();
//        System.out.println("Number of posts is: " + before.size());
//        PostData post = new PostData().withId(Integer.MAX_VALUE).withTitle("Test DB Creation");

        try {
            // create a mysql database connection
            String myDriver = "com.mysql.cj.jdbc.Driver";
            String myUrl = "jdbc:mysql://192.168.1.5/wordpress.local";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");
            // the mysql insert statement
            String query = "insert into wp_posts (post_title, post_content, post_excerpt, to_ping, pinged, post_content_filtered) " +
                    "values (?, ?, ?, ?, ?, ?)";
            // create the mysql insert prepared statement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, "Test Post JDBC");
            preparedStmt.setString(2, "Test Post JDBC");
            preparedStmt.setString(3, "");
            preparedStmt.setString(4, "");
            preparedStmt.setString(5, "");
            preparedStmt.setString(6, "");
            // execute the prepared statement
            preparedStmt.execute();
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

}

//https://alvinalexander.com/java/java-mysql-insert-example-preparedstatement