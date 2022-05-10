import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//username and password will be checked individually in the main Program, so we will check them one by one
public class Authenticate {
    public static String username_exists(String username) {
        //this method will check if the username exists in the database and return the username if it exists
        //otherwise it will return null
        Connection c = DBConnection.connect();
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT username FROM users WHERE username = '" + username + "'";
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return username;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //this method will check if the password is correct for the given username
    public static boolean password_correct(String username, String password) {
        //this method will check if the password is correct for the given username
        //if it is correct, it will return true
        //otherwise it will return false
        Connection c = DBConnection.connect();
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT password FROM users WHERE username = '" + username + "'";
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery(sql);
            if(rs.next()) {
                String password_in_db = rs.getString("password");
                if(password_in_db.equals(password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
