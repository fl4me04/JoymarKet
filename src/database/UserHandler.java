package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Admin;
import model.Courier;
import model.Customer;
import model.User;

public class UserHandler {
	
	// Function to get user by id
	public User getUser(String idUser) {
		String query = "SELECT * FROM users WHERE idUser = ?";
	    User user = null;
	    
	    try {
	        PreparedStatement ps = Connect.getInstance().prepareStatement(query);
	        ps.setString(1, idUser);
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            String name = rs.getString("fullName");
	            String email = rs.getString("email");
	            String password = rs.getString("password");
	            String phone = rs.getString("phone");
	            String address = rs.getString("address");
	            String role = rs.getString("role");
	            String gender = rs.getString("gender");
	            
	            // LOGIKA POLYMORPHISM (Sama seperti Login)
	            if (role.equalsIgnoreCase("Customer")) {
	                double balance = rs.getDouble("balance");
	                user = new Customer(idUser, name, email, password, phone, address, gender, balance);
	            } 
	            else if (role.equalsIgnoreCase("Admin")) {
	                String emerg = rs.getString("emergencyContact");
	                user = new Admin(idUser, name, email, password, phone, address, gender, emerg);
	            }
	            else {
	                user = new User(idUser, name, email, password, phone, address, role, gender);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return user;
	}
	
	// Function to edit profile
	public boolean editProfile(String idUser, String fullName, String email, String password, String phone, String address, String gender) {
	    String query = "UPDATE users SET fullName=?, email=?, password=?, phone=?, address=?, gender=? WHERE idUser=?";
	    try {
	        PreparedStatement ps = Connect.getInstance().prepareStatement(query);
	        ps.setString(1, fullName);
	        ps.setString(2, email);
	        ps.setString(3, password);
	        ps.setString(4, phone);
	        ps.setString(5, address);
	        ps.setString(6, gender);
	        ps.setString(7, idUser);
	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	// Function to add new user
	public boolean addNewUser(String fullName, String email, String password, String phone, String address, String gender) {
        String query = "INSERT INTO users (fullName, email, password, phone, address, gender, role, balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, phone);
            ps.setString(5, address);
            ps.setString(6, gender);
            ps.setString(7, "Customer");
            ps.setDouble(8, 0.0);
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	// Function to check email availability for register
	public boolean checkEmailAvailability(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            return !rs.next(); 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	// Function login
	public User login(String email, String password) {
	    String query = "SELECT * FROM users WHERE email = ? AND password = ?";
	    User user = null;

	    try {
	        PreparedStatement ps = Connect.getInstance().prepareStatement(query);
	        ps.setString(1, email);
	        ps.setString(2, password);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            String id = rs.getString("idUser");
	            String name = rs.getString("fullName");
	            String phone = rs.getString("phone");
	            String address = rs.getString("address");
	            String role = rs.getString("role");
	            String gender = rs.getString("gender");
	            
	            // Checking role
	            if (role.equalsIgnoreCase("Customer")) {
	                double balance = rs.getDouble("balance");
	                user = new Customer(id, name, email, password, phone, address, gender, balance);
	            } else if (role.equalsIgnoreCase("Admin")) {
	                String emerg = rs.getString("emergencyContact");
	                user = new Admin(id, name, email, password, phone, address, gender, emerg);
	            } else if (role.equalsIgnoreCase("Courier")) {
	            	String vehType = rs.getString("vehicleType");
	            	String vehPlate = rs.getString("vehiclePlate");
	            	user = new Courier(id, name, email, password, phone, address, gender, vehType, vehPlate);
	            } else {
	                user = new User(id, name, email, password, phone, address, role, gender);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return user;
	}

	// Function for check email availability for update
	public boolean checkEmailAvailabilityForUpdate(String email, String currentId) {
	    String query = "SELECT * FROM users WHERE email = ? AND idUser != ?";
	    try {
	        PreparedStatement ps = Connect.getInstance().prepareStatement(query);
	        ps.setString(1, email);
	        ps.setString(2, currentId);
	        ResultSet rs = ps.executeQuery();
	        
	        return !rs.next();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}
