package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.CartItem;
import model.Product;

public class CartItemHandler {

	private Connect db = Connect.getInstance();
	
	
	// Function check Item quantity in cart
    public int getCurrentQtyInCart(String idCustomer, String idProduct) {
        String query = "SELECT count FROM cartitems WHERE idCustomer = ? AND idProduct = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, idCustomer);
            ps.setString(2, idProduct);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Function add to cart while checking items in the cart
    public boolean addToCart(String idCustomer, String idProduct, int quantity) {
        // Check items currently in the cart
        int currentQty = getCurrentQtyInCart(idCustomer, idProduct);
        
        if (currentQty > 0) {
            String query = "UPDATE cartitems SET count = ? WHERE idCustomer = ? AND idProduct = ?";
            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(query)) {
                
                ps.setInt(1, currentQty + quantity);
                ps.setString(2, idCustomer);
                ps.setString(3, idProduct);
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            String query = "INSERT INTO cartitems (idCustomer, idProduct, count) VALUES (?, ?, ?)";
            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(query)) {
                
                ps.setString(1, idCustomer);
                ps.setString(2, idProduct);
                ps.setInt(3, quantity);
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
	
	// Function to getAllCartItems
    public ArrayList<CartItem> getCartItems(String idCustomer) {
        ArrayList<CartItem> cartList = new ArrayList<>();
        
        // Join Table to get Product Id, Stock, Category and Count
        String query = "SELECT c.idCustomer, c.idProduct, c.count, " +
                       "p.name, p.price, p.stock, p.category " +
                       "FROM cartitems c " +
                       "JOIN products p ON c.idProduct = p.idProduct " +
                       "WHERE c.idCustomer = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, idCustomer);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(
                        rs.getString("idProduct"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("category")
                    );

                    CartItem item = new CartItem(
                        rs.getString("idCustomer"),
                        rs.getString("idProduct"),
                        rs.getInt("count")
                    );
  
                    item.setProduct(product);
                    
                    cartList.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartList;
    }

    // Function to edit cart items
    public boolean editCartItem(String idCustomer, String idProduct, int count) {
        String query = "UPDATE cartitems SET count = ? WHERE idCustomer = ? AND idProduct = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, count);
            ps.setString(2, idCustomer);
            ps.setString(3, idProduct);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Function to delete cart items
    public boolean deleteCartItem(String idCustomer, String idProduct) {
        String query = "DELETE FROM cartitems WHERE idCustomer = ? AND idProduct = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, idCustomer);
            ps.setString(2, idProduct);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
