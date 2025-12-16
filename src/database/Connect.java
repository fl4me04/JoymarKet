package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class Connect {

	private final String USERNAME = "root";
    private final String PASSWORD = "";
    private final String DATABASE = "joymarket";
    private final String HOST = "localhost:3306";
    private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

    private Connection con;
    private Statement st;
    private static Connect instance;
    
    private Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load JDBC driver
            con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD); // Establish connection
            st = con.createStatement(); // Create Statement object
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to connect to database.");
            System.exit(0);
        }
    }
    
    public static Connect getInstance() {
        if (instance == null) {
            instance = new Connect();
        }
        return instance;
    }

    // Getter untuk mendapatkan objek Connection (Penting untuk Transaksi Manual)
    public Connection getConnection() {
    	try {
            return DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Membuat PreparedStatement yang bisa mengembalikan ID yang dibuat otomatis (Generated Keys)
    public PreparedStatement prepareStatement(String query, int returnGeneratedKeys) {
        try {
            return con.prepareStatement(query, returnGeneratedKeys);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
  

    // Method to run SELECT queries
    public ResultSet select(String query) {
        try {
            return st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to run INSERT/UPDATE/DELETE queries
    public void exec(String query) {
        try {
            st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Method for parameterized PreparedStatement
    public PreparedStatement prepareStatement(String query) {
        try {
            return con.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}