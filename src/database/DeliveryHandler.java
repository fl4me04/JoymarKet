package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Delivery;
import model.OrderHeader;
import model.User;

public class DeliveryHandler {

    private Connect db = Connect.getInstance();

    // Function to take unassigned orders
    public ArrayList<OrderHeader> getUnassignedOrders() {
        ArrayList<OrderHeader> list = new ArrayList<>();
        String query = "SELECT * FROM OrderHeaders WHERE idOrder NOT IN (SELECT idOrder FROM Deliveries) ORDER BY orderedAt DESC";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while(rs.next()) {
                list.add(new OrderHeader(rs.getString("idOrder"), rs.getString("idCustomer"), rs.getString("idPromo"), rs.getString("status"), new java.util.Date(rs.getTimestamp("orderedAt").getTime()), rs.getDouble("totalAmount")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Function to get list of available couriers
    public ArrayList<User> getAvailableCouriers() {
        ArrayList<User> list = new ArrayList<>();
        String query = "SELECT * FROM Users WHERE role = 'Courier'";
        
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while(rs.next()) {
                User u = new User(rs.getString("idUser"), rs.getString("fullName"), rs.getString("email"), rs.getString("password"), rs.getString("phone"), rs.getString("address"), rs.getString("role"), rs.getString("gender"));
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Function to create delivery
    public boolean assignCourier(String idOrder, String idCourier) {
        String query = "INSERT INTO Deliveries (idOrder, idCourier, status) VALUES (?, ?, 'Shipping')";
        
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, idOrder);
            ps.setString(2, idCourier);
            
            int rows = ps.executeUpdate();
            
            if (rows > 0) {
                updateOrderStatus(idOrder, "Processed");
            }
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Function to update order status
    private void updateOrderStatus(String idOrder, String newStatus) {
        String query = "UPDATE OrderHeaders SET status = ? WHERE idOrder = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, newStatus);
            ps.setString(2, idOrder);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    // Function to get deliveries by courier
    public ArrayList<Delivery> getDeliveriesByCourier(String idCourier) {
        ArrayList<Delivery> list = new ArrayList<>();
        String query = "SELECT * FROM Deliveries WHERE idCourier = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, idCourier);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    list.add(new Delivery(rs.getString("idOrder"), rs.getString("idCourier"), rs.getString("status")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Function to update delivery status
    public boolean updateDeliveryStatus(String idOrder, String newStatus) {
        String queryDelivery = "UPDATE Deliveries SET status = ? WHERE idOrder = ?";
        String queryOrder = "UPDATE OrderHeaders SET status = ? WHERE idOrder = ?";
        
        Connection conn = db.getConnection();
        try {
            conn.setAutoCommit(false);

            // Update Delivery
            try (PreparedStatement ps1 = conn.prepareStatement(queryDelivery)) {
                ps1.setString(1, newStatus);
                ps1.setString(2, idOrder);
                ps1.executeUpdate();
            }

            // Update Order Header Status
            String orderStatus = newStatus.equals("Delivered") ? "Completed" : newStatus;
            
            try (PreparedStatement ps2 = conn.prepareStatement(queryOrder)) {
                ps2.setString(1, orderStatus);
                ps2.setString(2, idOrder);
                ps2.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) {}
            e.printStackTrace();
            return false;
        } finally {
            try { conn.setAutoCommit(true); conn.close(); } catch (Exception e) {}
        }
    }
}