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
    public String checkout(User user, ArrayList<CartItem> cartItems, Promo promoUsed, double finalTotal) {
        Connection conn = db.getConnection();
        String generatedOrderId = null; 
        
        if (!(user instanceof Customer)) {
            return "Error: Only Customers can perform checkout!";
        }
        
        Customer customer = (Customer) user;
        
        try {
            conn.setAutoCommit(false);

            if (customer.getBalance() < finalTotal) {
                return "Insufficient Balance!";
            }

            String sqlHeader = "INSERT INTO OrderHeaders (idCustomer, idPromo, status, totalAmount) VALUES (?, ?, 'Pending', ?)";
            
            try (PreparedStatement ps = conn.prepareStatement(sqlHeader, java.sql.Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, user.getIdUser());
                ps.setString(2, promoUsed != null ? promoUsed.getIdPromo() : null); 
                ps.setDouble(3, finalTotal);
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedOrderId = rs.getString(1);
                    }
                }
            }
            
            if (generatedOrderId == null) {
                String fetchId = "SELECT idOrder FROM OrderHeaders WHERE idCustomer = ? ORDER BY orderedAt DESC LIMIT 1";
                try (PreparedStatement psFetch = conn.prepareStatement(fetchId)) {
                    psFetch.setString(1, user.getIdUser());
                    try(ResultSet rs = psFetch.executeQuery()) {
                        if(rs.next()) generatedOrderId = rs.getString("idOrder");
                    }
                }
           }

            if (generatedOrderId == null) {
                throw new SQLException("Failed to retrieve generated Order ID.");
            }

            // Insert, update, and delete order
            String sqlDetail = "INSERT INTO OrderDetails (idOrder, idProduct, qty) VALUES (?, ?, ?)";
            String sqlStock = "UPDATE Products SET stock = stock - ? WHERE idProduct = ?";
            String sqlDelCart = "DELETE FROM CartItems WHERE idCustomer = ? AND idProduct = ?";

            try (PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
                 PreparedStatement psStock = conn.prepareStatement(sqlStock);
                 PreparedStatement psCart = conn.prepareStatement(sqlDelCart)) {

                for (CartItem item : cartItems) {
                    String prodId = item.getProduct().getId();
                    int qty = item.getCount();

                    psDetail.setString(1, generatedOrderId);
                    psDetail.setString(2, prodId);
                    psDetail.setInt(3, qty);
                    psDetail.addBatch();

                    psStock.setInt(1, qty);
                    psStock.setString(2, prodId);
                    psStock.addBatch();

                    psCart.setString(1, user.getIdUser());
                    psCart.setString(2, prodId);
                    psCart.addBatch();
                }
                
                psDetail.executeBatch();
                psStock.executeBatch();
                psCart.executeBatch();
            }

            // Cut User Balance
            String sqlSaldo = "UPDATE Users SET balance = balance - ? WHERE idUser = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlSaldo)) {
                ps.setDouble(1, finalTotal);
                ps.setString(2, user.getIdUser());
                ps.executeUpdate();
            }

            customer.setBalance(customer.getBalance() - finalTotal);
            
            conn.commit();
            return "SUCCESS";

        } catch (Exception e) {
        	try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return "Transaction Failed: " + e.getMessage();
        } finally {
            try { conn.setAutoCommit(true); conn.close(); } catch (Exception e) {}
        }
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
                    model.OrderDetail detail = new model.OrderDetail(
                        rs.getString("idOrder"),
                        rs.getString("idProduct"),
                        rs.getInt("qty")
                    );

                    // Make Object Product then input to detail
                    model.Product product = new model.Product(
                        rs.getString("idProduct"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("category")
                    );
                    detail.setProduct(product);

                    list.add(detail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}