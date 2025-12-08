package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connect;
import javafx.scene.control.Alert.AlertType;
import model.Admin;
import model.Courier;
import model.Customer;
import model.User;

public class UserHandler {
	
	public User getUser(String idUser) {
		User user = null;
		String query = "SELECT * FROM users where idUser = ?";
		
		try {
			PreparedStatement ps = Connect.getInstance().prepareStatement(query);
			ps.setString(1, idUser);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				user = new User(rs.getString("idUser"), rs.getString("fullName"), rs.getString("email"), rs.getString("password"), rs.getString("phone"), rs.getString("address"), rs.getString("gender"), rs.getString("role"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public boolean editProfile(String idUser, String fullName, String email, String password, String phone, String address) {
        String query = "UPDATE users SET fullName=?, email=?, password=?, phone=?, address=? WHERE idUser=?";
        
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, phone);
            ps.setString(5, address);
            ps.setString(6, idUser);
            
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
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
}
