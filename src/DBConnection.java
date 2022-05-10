import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection connect() {
        Connection c = null;
        // System.out.println("Connecting to database...");
        try{
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/nyavt_proje_db",
                "postgres", "13221881q_WE");
            // System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("An error occured while connecting to the database.");
            return null;
        }
        // System.out.println("Opened database successfully");
        return c;
    }
}
