package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Product;

public class ProductManager {

    private Connect db = Connect.getInstance();

   
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        Connection conn = db.getConnection();
        
        if (conn == null) {
            System.err.println("Database connection is null in ProductManager. Check Connect class.");
            return products;
        }

        // Query untuk mengambil ID, Nama, dan Harga produk
        String query = "SELECT ProductID, Name, Price FROM Products";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                String id = rs.getString("ProductID");
                String name = rs.getString("Name");
                int price = rs.getInt("Price");
                
                // Membuat objek Product dan menambahkannya ke list
                products.add(new Product(id, name, price));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching products from database: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Menutup resources
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return products;
    }
}