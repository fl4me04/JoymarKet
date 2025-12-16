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
        return new Product(rs.getString("idProduct"), rs.getString("name"), rs.getDouble("price"), rs.getInt("stock"), rs.getString("category"));
    }
    
    // Function to update stock for admin
    public boolean updateStock(String idProduct, int newStock) {
        String query = "UPDATE Products SET stock = ? WHERE idProduct = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, newStock);
            ps.setString(2, idProduct);
            
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Function to insert new product
    public boolean insertProduct(Product p) {
        String query = "INSERT INTO Products (idProduct, name, price, stock, category) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, p.getIdProduct());
            ps.setString(2, p.getName());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getStock());
            ps.setString(5, p.getCategory());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Function to update product
    public boolean updateProduct(Product p) {
        String query = "UPDATE Products SET name=?, price=?, stock=?, category=? WHERE idProduct=?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getStock());
            ps.setString(4, p.getCategory());
            ps.setString(5, p.getIdProduct());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Function to delete product
    public boolean deleteProduct(String idProduct) {
        String query = "DELETE FROM Products WHERE idProduct=?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, idProduct);
            return ps.executeUpdate() > 0;

        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Function to generate new ProductID
    public String generateNewId() {
        String query = "SELECT idProduct FROM Products ORDER BY idProduct DESC LIMIT 1";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                String lastId = rs.getString("idProduct");
                int num = Integer.parseInt(lastId.substring(2)) + 1;
                return String.format("P%04d", num);
            }
        } catch (Exception e) {}
        return "P0001";
    }
}
