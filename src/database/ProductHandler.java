package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Product;

public class ProductHandler {

	private Connect db = Connect.getInstance();
	
	// Function to get All Products
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> list = new ArrayList<>();
        String query = "SELECT * FROM products";
        
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Function to get product details
    public Product getProduct(String idProduct) {
        String query = "SELECT * FROM products WHERE idProduct = ?";
        Product product = null;
        
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, idProduct);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    product = mapToProduct(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    // Function to update product stock
    public boolean editProductStock(String idProduct, int newStock) {
        String query = "UPDATE products SET stock = ? WHERE idProduct = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, newStock);
            ps.setString(2, idProduct);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper to mapping ResultSet into Object Product
    private Product mapToProduct(ResultSet rs) throws SQLException {
        return new Product(
            rs.getString("idProduct"),
            rs.getString("name"),
            rs.getDouble("price"),
            rs.getInt("stock"),
            rs.getString("category")
        );
    }

}
