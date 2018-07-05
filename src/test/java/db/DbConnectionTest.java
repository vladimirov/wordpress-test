package db;

import model.PostData;
import model.Posts;
import org.testng.annotations.Test;

import java.sql.*;

public class DbConnectionTest {

    @Test
    public void testDbConnection() {
        Connection conn = null;
        String database = "wordpress-test";

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database + "?user=root&password=");

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select id, post_title from wp_posts");
            Posts posts = new Posts();
            while(rs.next()) {
                posts.add(new PostData().withId(rs.getInt("id")).withTitle(rs.getString("post_title")));
            }
            rs.close();
            st.close();
            conn.close();
            System.out.println(posts);
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

}
