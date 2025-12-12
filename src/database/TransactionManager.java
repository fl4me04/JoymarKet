package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Cart;
import model.CartItem;

public class TransactionManager {

    private Connect db = Connect.getInstance();

    public boolean saveTransaction(Cart cart, String userId) {
        
        String insertTransactionSQL = "INSERT INTO Transactions (UserID, TotalAmount) VALUES (?, ?)";
        String insertDetailSQL = "INSERT INTO TransactionDetails (TransactionID, ProductID, Quantity, Price) VALUES (?, ?, ?, ?, ?)";
        
        Connection conn = db.getConnection();
        PreparedStatement psTrans = null;
        PreparedStatement psDetail = null;
        ResultSet rs = null;
        int transactionId = -1;

        try {
            conn.setAutoCommit(false); // Start transaction (ACID)

            // 1. Insert Main Transaction
            psTrans = db.prepareStatement(insertTransactionSQL, Statement.RETURN_GENERATED_KEYS);
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

            // 2. Insert Transaction Details
            psDetail = db.prepareStatement(insertDetailSQL);
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
            // Close resources and reset AutoCommit
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
}