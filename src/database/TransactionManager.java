package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Cart;
import model.CartItem;
import model.Product;
import java.util.ArrayList;

public class TransactionManager {

    private Connect db = Connect.getInstance();

    public boolean saveTransaction(Cart cart, String userId) {
        // Asumsi: Method ini digunakan untuk FINAL ORDER (Status: Completed/Final)
        String insertTransactionSQL = "INSERT INTO Transactions (UserID, TotalAmount, Status) VALUES (?, ?, 'Completed')";
        String insertDetailSQL = "INSERT INTO TransactionDetails (TransactionID, ProductID, Quantity, Price) VALUES (?, ?, ?, ?)"; 
        
        Connection conn = db.getConnection();
        if (conn == null) {
            System.err.println("Database connection is null. Check Connect class.");
            return false;
        }

        PreparedStatement psTrans = null;
        PreparedStatement psDetail = null;
        ResultSet rs = null;
        int transactionId = -1;

        try {
            conn.setAutoCommit(false);

            psTrans = conn.prepareStatement(insertTransactionSQL, Statement.RETURN_GENERATED_KEYS);
            psTrans.setString(1, userId);
            psTrans.setInt(2, cart.getTotal());
            
            if (psTrans.executeUpdate() == 0) {
                conn.rollback();
                return false;
            }

            rs = psTrans.getGeneratedKeys();
            if (rs.next()) {
                transactionId = rs.getInt(1);
            } else {
                conn.rollback();
                return false;
            }

            psDetail = conn.prepareStatement(insertDetailSQL);
            for (CartItem item : cart.getItems()) {
                psDetail.setInt(1, transactionId);
                psDetail.setString(2, item.getProduct().getId()); 
                psDetail.setInt(3, item.getQuantity());
                psDetail.setInt(4, item.getProduct().getPrice());
                psDetail.addBatch();
            }
            
            psDetail.executeBatch();

            conn.commit(); 
            return true;

        } catch (SQLException e) {
            System.err.println("SQL Error during transaction: " + e.getMessage());
            e.printStackTrace();
            
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
            
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); 
                }
                if (rs != null) rs.close();
                if (psTrans != null) psTrans.close();
                if (psDetail != null) psDetail.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateDraftTransaction(Cart cart, String userId) {
        Connection conn = db.getConnection();
        if (conn == null) return false;

        String selectDraftSQL = "SELECT TransactionID FROM Transactions WHERE UserID = ? AND Status = 'Draft'";
        String deleteDetailSQL = "DELETE FROM TransactionDetails WHERE TransactionID = ?";
        String insertDetailSQL = "INSERT INTO TransactionDetails (TransactionID, ProductID, Quantity, Price) VALUES (?, ?, ?, ?)";
        String insertTransactionSQL = "INSERT INTO Transactions (UserID, TotalAmount, Status) VALUES (?, ?, 'Draft')";
        String updateTransactionSQL = "UPDATE Transactions SET TotalAmount = ? WHERE TransactionID = ?";

        int transactionId = -1;
        
        PreparedStatement psSelect = null;
        PreparedStatement psInsert = null;
        PreparedStatement psUpdate = null;
        PreparedStatement psDelete = null;
        PreparedStatement psDetail = null;
        ResultSet rs = null;

        try {
            conn.setAutoCommit(false);

            psSelect = conn.prepareStatement(selectDraftSQL);
            psSelect.setString(1, userId);
            rs = psSelect.executeQuery();
            if (rs.next()) {
                transactionId = rs.getInt(1);
            }
            rs.close();

            if (transactionId == -1) {
                psInsert = conn.prepareStatement(insertTransactionSQL, Statement.RETURN_GENERATED_KEYS);
                psInsert.setString(1, userId);
                psInsert.setInt(2, cart.getTotal());
                
                if (psInsert.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }
                
                rs = psInsert.getGeneratedKeys();
                if (rs.next()) {
                    transactionId = rs.getInt(1);
                } else {
                    conn.rollback();
                    return false;
                }
                rs.close();
                
            } else {
                psUpdate = conn.prepareStatement(updateTransactionSQL);
                psUpdate.setInt(1, cart.getTotal());
                psUpdate.setInt(2, transactionId);
                psUpdate.executeUpdate();

                psDelete = conn.prepareStatement(deleteDetailSQL);
                psDelete.setInt(1, transactionId);
                psDelete.executeUpdate();
            }

            if (!cart.getItems().isEmpty()) {
                psDetail = conn.prepareStatement(insertDetailSQL);
                for (CartItem item : cart.getItems()) {
                    psDetail.setInt(1, transactionId);
                    psDetail.setString(2, item.getProduct().getId());
                    psDetail.setInt(3, item.getQuantity());
                    psDetail.setInt(4, item.getProduct().getPrice());
                    psDetail.addBatch();
                }
                psDetail.executeBatch();
            }
            
            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("SQL Error during draft update: " + e.getMessage());
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
                if (rs != null) rs.close();
                if (psSelect != null) psSelect.close();
                if (psInsert != null) psInsert.close();
                if (psUpdate != null) psUpdate.close();
                if (psDelete != null) psDelete.close();
                if (psDetail != null) psDetail.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public Product getProductById(String productId) {
        if (productId.equals("P001")) return new Product("P001", "Apple", 5000);
        if (productId.equals("P002")) return new Product("P002", "Banana", 3000);
        if (productId.equals("P003")) return new Product("P003", "Milk", 12000);
        return null;
    }

    public Cart loadDraftCart(String userId) {
        Cart cart = new Cart();
        Connection conn = db.getConnection();
        if (conn == null) return cart;

        String selectDraftIDSQL = "SELECT TransactionID FROM Transactions WHERE UserID = ? AND Status = 'Draft'";
        String selectDetailsSQL = "SELECT ProductID, Quantity, Price FROM TransactionDetails WHERE TransactionID = ?";
        
        PreparedStatement psSelectID = null;
        PreparedStatement psSelectDetails = null;
        ResultSet rsID = null;
        ResultSet rsDetails = null;
        int transactionId = -1;

        try {
            psSelectID = conn.prepareStatement(selectDraftIDSQL);
            psSelectID.setString(1, userId);
            rsID = psSelectID.executeQuery();

            if (rsID.next()) {
                transactionId = rsID.getInt(1);
            } else {
                return cart;
            }
            
            psSelectDetails = conn.prepareStatement(selectDetailsSQL);
            psSelectDetails.setInt(1, transactionId);
            rsDetails = psSelectDetails.executeQuery();

            while (rsDetails.next()) {
                String productId = rsDetails.getString("ProductID");
                int quantity = rsDetails.getInt("Quantity");

                Product product = getProductById(productId);
                
                if (product != null) {
                    cart.getItems().add(new CartItem(product, quantity));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("SQL Error during draft loading: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rsID != null) rsID.close();
                if (rsDetails != null) rsDetails.close();
                if (psSelectID != null) psSelectID.close();
                if (psSelectDetails != null) psSelectDetails.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return cart;
    }
}