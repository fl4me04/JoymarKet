package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Promo;

public class PromoHandler {
    
    private Connect db = Connect.getInstance();

    // Function to get all discount that available
    public ArrayList<Promo> getAllPromos() {
        ArrayList<Promo> list = new ArrayList<>();
        String query = "SELECT * FROM Promos";
        
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while(rs.next()) {
                list.add(new Promo(
                    rs.getString("idPromo"),
                    rs.getString("code"),
                    rs.getString("headline"),
                    rs.getDouble("discountPercentage")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Function to find discount by code
    public Promo getPromo(String code) {
        String query = "SELECT * FROM Promos WHERE code = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Promo(
                        rs.getString("idPromo"),
                        rs.getString("code"),
                        rs.getString("headline"),
                        rs.getDouble("discountPercentage")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}