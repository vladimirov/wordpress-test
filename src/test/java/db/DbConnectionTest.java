package db;

import model.PostData;
import model.Posts;
import org.testng.annotations.Test;

import java.sql.*;

public class DbConnectionTest {

    @Test
    public void testDbConnection() {
        Connection conn = null;
        String database = "wordpress.local";

        try {
                conn = DriverManager.getConnection("jdbc:mysql://10.11.1.188/" + database + "?user=root&password=");
            Statement statement = conn.createStatement();
//            ResultSet rs = st.executeQuery("select id, post_title from wp_posts");
//            Posts posts = new Posts();
//            while (rs.next()) {
//                posts.add(new PostData().withId(rs.getInt("id")).withTitle(rs.getString("post_title")));
//            }
//            posts.add(new PostData().withId(rs.getInt("id")).withTitle(rs.getString("post_title")));

//            ResultSet rs = st.executeQuery("INSERT INTO wp_posts (id, post_title) VALUE (555, Test Post)");
            ResultSet resultSet = statement.executeQuery("INSERT INTO wp_posts (id, post_title) VALUE (555, Test Post)");


            resultSet.close();
            statement.close();
            conn.close();
//            System.out.println(posts);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}

