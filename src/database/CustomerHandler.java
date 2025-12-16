package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerHandler {

    private Connect db = Connect.getInstance();

    // Function for top up balance
    public boolean topUpBalance(String idUser, double amount) {
        if (amount <= 0) return false;

        String query = "UPDATE Users SET balance = balance + ? WHERE idUser = ?";
        
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setDouble(1, amount);
            ps.setString(2, idUser);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}