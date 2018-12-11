package db;

import model.PostData;
import model.Posts;
import org.testng.annotations.Test;

import java.sql.*;

public class DbConnectionTest {

    @Test
    public void testDbConnection() {
        Connection conn = null;
        try {
//            conn = DriverManager.getConnection("jdbc:mysql://10.11.1.188/wordpress.local?user=root&password=");
            conn = DriverManager.getConnection("jdbc:mysql://192.168.1.5/wordpress.local?user=root&password=");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select id, post_title from wp_posts");
//            boolean rs = st.execute("insert into wp-posts (post_title, post_content) values (SQL, Test)");
            Posts posts = new Posts();
            while (rs.next()) {
                posts.add(new PostData().withId(rs.getInt("id")).withTitle(rs.getString("post_title")));
            }
            posts.add(new PostData().withId(rs.getInt("id")).withTitle(rs.getString("post_title")));

            rs.close();
            st.close();
            conn.close();
//            System.out.println(posts);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}

