import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SmartDevice {
    private final IScreen screen;
    Actuator actuator;
    HeatDetector heatDetector;

    private static final int TURN_ON_COOLER = 1;
    private static final int TURN_OFF_COOLER = 2;
    private static final int READ_TEMPERATURE = 3;

    private static final int STATE_CLOSED = 1;
    // private static final int STATE_OPENING = 2;
    private static final int STATE_STANDBY = 3;
    // private static final int STATE_DETECTING = 4;
    // private static final int STATE_OUT_OF_SERVICE = 5;
    // private static final int STATE_OPERATING = 6;


    public SmartDevice(){
        this.actuator = new Actuator();
        this.heatDetector = new HeatDetector(this);
        this.screen = new Screen();
        close();
    }

    private void select_action(){
        screen.showMessage("Please select an action:");
        screen.showMessage("1. Turn on cooler");
        screen.showMessage("2. Turn off cooler");
        screen.showMessage("3. Read temperature");
        screen.showMessage("4. Close the device");
        Scanner scan = new Scanner(System.in);
        int action = scan.nextInt();
        switch (action){
            case TURN_ON_COOLER:
                actuator.turn_on_cooler();
                break;
            case TURN_OFF_COOLER:
                actuator.turn_off_cooler();
                break;
            case READ_TEMPERATURE:
                screen.showMessage("The temperature is " + heatDetector.get_detected_heat() + " degrees.");
                break;
            case 4:
                screen.showMessage("Exiting...");
                close();
                break;
            default:
                screen.showMessage("Invalid action.");
                select_action();
                break;
        }
    }
    public void start(){
        if(is_open()){
            screen.showMessage("The device is already open.");
            return;
        }
        open();

        screen.showMessage("Welcome to SmartDevice!");
        screen.showMessage("Please enter your username to log in...");
        Scanner scan = new Scanner(System.in);
        String username = scan.nextLine();
        username = Authenticate.username_exists(username);
        if (username == null) {
            screen.showMessage("Username does not exist.");
            scan.close();
            return;
        }
        screen.showMessage("Please enter your password to log in...");
        String password = scan.nextLine();
        if (Authenticate.password_correct(username, password)) {
            screen.showMessage("Welcome " + username + "!");
        } else {
            screen.showMessage("Password is incorrect.");
            return;
        }
        while(is_open()){
            select_action();
        }
    }
    private boolean is_open(){
        String sql = "SELECT * FROM states WHERE is_active = true";
        Connection connection = DBConnection.connect();
        if (connection == null) {
            screen.showMessage("An error occured while connecting to the database.");
            return false;
        }
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                if(rs.getInt("id") == STATE_STANDBY){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private void close(){
        String sql = "SELECT * FROM states WHERE is_active = true";
        Connection connection = DBConnection.connect();
        if (connection == null) {
            screen.showMessage("An error occured while connecting to the database.");
            return;
        }
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                if(rs.getInt("id") == STATE_STANDBY){
                    //set the is_active field for the state with id STATE_STANDBY to false and set the state with id STATE_CLOSED's is_active field to true
                    sql = "UPDATE states SET is_active = false WHERE id = " + STATE_STANDBY;
                    stmt.executeUpdate(sql);
                    sql = "UPDATE states SET is_active = true WHERE id = " + STATE_CLOSED;
                    stmt.executeUpdate(sql);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void open(){
        String sql = "SELECT * FROM states WHERE is_active = true";
        Connection connection = DBConnection.connect();
        if (connection == null) {
            screen.showMessage("An error occured while connecting to the database.");
            return;
        }
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                if(rs.getInt("id") == STATE_CLOSED){
                    //set the is_active field for the state with id STATE_CLOSED to false and set the state with id STATE_STANDBY's is_active field to true
                    sql = "UPDATE states SET is_active = false WHERE id = " + STATE_CLOSED;
                    stmt.executeUpdate(sql);
                    sql = "UPDATE states SET is_active = true WHERE id = " + STATE_STANDBY;
                    stmt.executeUpdate(sql);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
