package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Courier;
import model.User;

public class CourierHandler {

    private Connect db = Connect.getInstance();

    // Function to get all couriers
    public ArrayList<Courier> getAllCouriers() {
        ArrayList<Courier> list = new ArrayList<>();
        String query = "SELECT * FROM Users WHERE role = 'Courier'";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Courier u = new Courier(rs.getString("idUser"), rs.getString("fullName"), rs.getString("email"), rs.getString("password"), rs.getString("phone"), rs.getString("address"), rs.getString("gender"), rs.getString("vehicleType"), rs.getString("vehiclePlate"));
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Function to create courier
    public boolean createCourier(Courier c) {
        String query = "INSERT INTO Users (idUser, fullName, email, password, phone, address, gender, role, vehicleType, vehiclePlate) VALUES (?, ?, ?, ?, ?, ?, ?, 'Courier', ?, ?)";
        try (Connection conn = Connect.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, c.getIdUser());
            ps.setString(2, c.getFullName());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getPassword());
            ps.setString(5, c.getPhone());
            ps.setString(6, c.getAddress());
            ps.setString(7, c.getGender());
            ps.setString(8, c.getVehicleType());
            ps.setString(9, c.getVehiclePlate());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Function to update courier
    public boolean updateCourier(Courier c) {
        String query = "UPDATE Users SET fullName=?, email=?, password=?, phone=?, address=?, gender=?, vehicleType=?, vehiclePlate=? WHERE idUser=?";
        try (Connection conn = Connect.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, c.getFullName());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getPassword());
            ps.setString(4, c.getPhone());
            ps.setString(5, c.getAddress());
            ps.setString(6, c.getGender());
            ps.setString(7, c.getVehicleType());
            ps.setString(8, c.getVehiclePlate());
            ps.setString(9, c.getIdUser());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Function to delete courier
    public boolean deleteCourier(String idUser) {
        String query = "DELETE FROM Users WHERE idUser = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, idUser);
            return ps.executeUpdate() > 0;

        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            return false; 
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Helper to generate ID automatically
    public String generateNewId() {
        String query = "SELECT idUser FROM Users ORDER BY idUser DESC LIMIT 1";
        try (Connection conn = Connect.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                String lastId = rs.getString("idUser");
                int num = Integer.parseInt(lastId.substring(2)) + 1;
                return String.format("U%04d", num);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return "U0001";
    }
}