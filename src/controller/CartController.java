package controller;

import javafx.stage.Stage;
import model.Cart;
import model.Product;
import view.AddToCartPage;

import database.TransactionManager; 
import javafx.scene.control.Alert;  

public class CartController {

    private Cart cart;
    private AddToCartPage view;
    private Stage stage;
    private TransactionManager transactionManager;

    public CartController(Cart cart, AddToCartPage view, Stage stage) {
        this.cart = cart;
        this.view = view;
        this.stage = stage;
        this.transactionManager = new TransactionManager(); // Inisialisasi Transaction Manager
        initController();
    }

    private void initController() {
        // Handle Add buttons 
        for (int i = 0; i < view.getAddBtns().size(); i++) {
            int index = i;
            view.getAddBtns().get(i).setOnAction(e -> {
                Product p = view.getProducts().get(index);
                int qty = view.getQtyInputs().get(index).getValue();
                cart.addToCart(p, qty);
                System.out.println("Added " + qty + " " + p.getName() + " to cart");
            });
        }

        // Handle Back button (Kode yang sudah ada)
        view.getBackBtn().setOnAction(e -> {
            if (view.getMenuScene() != null) {
                stage.setScene(view.getMenuScene());
            }
        });
        
        // Handle Submit Order Button (Penambahan baru)

        view.getSubmitBtn().setOnAction(e -> {
            handleSubmitOrder();
        });
    }
    
    private void handleSubmitOrder() {
        if (cart.getItems().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Cart Empty", "Your cart is empty. Please add some products before submitting.");
            return;
        }

       
        String currentUserId = "1"; 
        
        System.out.println("Attempting to submit order...");

        
        boolean success = transactionManager.saveTransaction(cart, currentUserId); 

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Order submitted successfully! Total: Rp " + cart.getTotal());
            cart.clearCart(); // Kosongkan keranjang setelah berhasil
            
           
            if (view.getMenuScene() != null) {
                stage.setScene(view.getMenuScene());
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to submit order. Check console/database configuration.");
        }
    }

    /**
     * Helper method untuk menampilkan notifikasi Alert.
     */
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}