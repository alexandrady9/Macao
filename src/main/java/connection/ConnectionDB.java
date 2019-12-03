package connection;
import java.sql.*;


public class ConnectionDB {
    private static ConnectionDB connectionDB = null;

    private static Connection myConnection = null;

    private ConnectionDB () { }

    public static ConnectionDB getInstance() {
        if (connectionDB == null) {
            connectionDB = new ConnectionDB();
        }

        return connectionDB;
    }

    public Connection createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/macao", "root", "root");
            return myConnection;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("CONNECTION FAILED");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}




