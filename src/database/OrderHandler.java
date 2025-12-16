package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.CartItem;
import model.Customer;
import model.OrderHeader;
import model.Promo;
import model.User;

public class OrderHandler {

    private Connect db = Connect.getInstance();

    // Function to checkout
    public String checkout(User user, ArrayList<CartItem> cartItems, Promo promo, double grandTotal) {
        Connection conn = null;
        PreparedStatement psOrder = null;
        PreparedStatement psDetail = null;
        PreparedStatement psUpdateStock = null;
        PreparedStatement psUpdateBalance = null;
        PreparedStatement psClearCart = null;

        if (user instanceof Customer) {
            Customer c = (Customer) user;
            if (c.getBalance() < grandTotal) {
                return "Insufficient balance!";
            }
        }

        try {
            conn = db.getConnection();
            conn.setAutoCommit(false);

            String newOrderId = generateOrderId(conn);

            String queryHeader = "INSERT INTO OrderHeaders (idOrder, idCustomer, idPromo, status, orderedAt, totalAmount) VALUES (?, ?, ?, 'Pending', NOW(), ?)";
            psOrder = conn.prepareStatement(queryHeader);
            psOrder.setString(1, newOrderId);
            psOrder.setString(2, user.getIdUser());
            psOrder.setString(3, promo == null ? null : promo.getIdPromo());
            psOrder.setDouble(4, grandTotal);
            psOrder.executeUpdate();

            String queryDetail = "INSERT INTO OrderDetails (idOrder, idProduct, qty) VALUES (?, ?, ?)";
            
            String queryStock = "UPDATE Products SET stock = stock - ? WHERE idProduct = ?"; 

            psDetail = conn.prepareStatement(queryDetail);
            psUpdateStock = conn.prepareStatement(queryStock);

            for (CartItem item : cartItems) {
                psDetail.setString(1, newOrderId);
                psDetail.setString(2, item.getProduct().getIdProduct());
                psDetail.setInt(3, item.getCount());
                psDetail.addBatch();

                psUpdateStock.setInt(1, item.getCount());
                psUpdateStock.setString(2, item.getProduct().getIdProduct());
                psUpdateStock.addBatch();
            }

            psDetail.executeBatch();
            psUpdateStock.executeBatch();

            if (user instanceof Customer) {
                String queryBalance = "UPDATE Users SET balance = balance - ? WHERE idUser = ?";
                psUpdateBalance = conn.prepareStatement(queryBalance);
                psUpdateBalance.setDouble(1, grandTotal);
                psUpdateBalance.setString(2, user.getIdUser());
                psUpdateBalance.executeUpdate();
                
                ((Customer) user).setBalance(((Customer) user).getBalance() - grandTotal);
            }

            String queryClearCart = "DELETE FROM CartItems WHERE idCustomer = ?";
            psClearCart = conn.prepareStatement(queryClearCart);
            psClearCart.setString(1, user.getIdUser());
            psClearCart.executeUpdate();

            conn.commit();
            return "SUCCESS";

        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return "Database Error: " + e.getMessage();
        } finally {
            try { if (psOrder != null) psOrder.close(); } catch (Exception e) {}
            try { if (psDetail != null) psDetail.close(); } catch (Exception e) {}
            try { if (psUpdateStock != null) psUpdateStock.close(); } catch (Exception e) {}
            try { if (psUpdateBalance != null) psUpdateBalance.close(); } catch (Exception e) {}
            try { if (psClearCart != null) psClearCart.close(); } catch (Exception e) {}
            try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (Exception e) {}
        }
    }

    // Helper to generate OrderID
    private String generateOrderId(Connection conn) throws SQLException {
        String query = "SELECT idOrder FROM OrderHeaders ORDER BY orderedAt DESC LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String lastId = rs.getString("idOrder"); 
                int num = Integer.parseInt(lastId.substring(2)) + 1;
                return String.format("PO%03d", num);
            }
        }
        return "PO001";
    }

    // Function to take all customer orders
    public ArrayList<OrderHeader> getCustomerOrders(String idCustomer) {
        ArrayList<OrderHeader> list = new ArrayList<>();
        String query = "SELECT * FROM OrderHeaders WHERE idCustomer = ? ORDER BY orderedAt DESC";
        
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, idCustomer);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                	list.add(new OrderHeader(rs.getString("idOrder"), rs.getString("idCustomer"), rs.getString("idPromo"), rs.getString("status"), rs.getTimestamp("orderedAt"), rs.getDouble("totalAmount")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Function to get order details
    public ArrayList<model.OrderDetail> getOrderDetails(String idOrder) {
        ArrayList<model.OrderDetail> list = new ArrayList<>();
        // Joining Product table
        String query = "SELECT od.idOrder, od.idProduct, od.qty, p.name, p.price, p.category, p.stock " +
                       "FROM OrderDetails od " +
                       "JOIN Products p ON od.idProduct = p.idProduct " +
                       "WHERE od.idOrder = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, idOrder);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    // Make OrderDetail object
                    model.OrderDetail detail = new model.OrderDetail(rs.getString("idOrder"), rs.getString("idProduct"), rs.getInt("qty"));

                    // Make Object Product then input to detail
                    model.Product product = new model.Product(rs.getString("idProduct"), rs.getString("name"), rs.getDouble("price"), rs.getInt("stock"), rs.getString("category"));
                    detail.setProduct(product);

                    list.add(detail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Function to get all orders
    public ArrayList<OrderHeader> getAllOrders() {
        ArrayList<OrderHeader> list = new ArrayList<>();
        String query = "SELECT * FROM OrderHeaders ORDER BY orderedAt DESC";
        
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while(rs.next()) {
                list.add(new OrderHeader(rs.getString("idOrder"), rs.getString("idCustomer"), rs.getString("idPromo"), rs.getString("status"), rs.getTimestamp("orderedAt"), rs.getDouble("totalAmount")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}