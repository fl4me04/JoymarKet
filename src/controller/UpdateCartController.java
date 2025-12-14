package controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Cart;
import model.CartItem;
import view.UpdateCartPage;

public class UpdateCartController {

    private Cart cart;
    private UpdateCartPage view;
    private Stage stage;

    public UpdateCartController(Cart cart, UpdateCartPage view, Stage stage) {
        this.cart = cart;
        this.view = view;
        this.stage = stage;
        initController();
    }

    private void initController() {
        // Handle Back button
        view.getBackBtn().setOnAction(e -> {
            if (view.getMenuScene() != null) {
                stage.setScene(view.getMenuScene());
            }
        });
        
        // Handle Delete buttons
        for (int i = 0; i < view.getDeleteBtns().size(); i++) {
            int index = i;
            view.getDeleteBtns().get(i).setOnAction(e -> {
                // Hapus item dari Cart model
                CartItem item = cart.getItems().get(index);
                cart.removeItem(item.getProduct().getId());
                showAlert(Alert.AlertType.INFORMATION, "Item Removed", item.getProduct().getName() + " has been removed from your cart.");
                
                // Kembali ke menu setelah hapus untuk me-refresh UI
                stage.setScene(view.getMenuScene());
            });
        }
        
        // Handle Save Changes button
        if (view.getSaveChangesBtn() != null) {
            view.getSaveChangesBtn().setOnAction(e -> {
                handleSave();
            });
        }
    }
    
    private void handleSave() {
        for (int i = 0; i < cart.getItems().size(); i++) {
            CartItem item = cart.getItems().get(i);
            int newQty = view.getQtyInputs().get(i).getValue();
            
            if (newQty <= 0) {
                // Jika kuantitas 0, hapus item
                cart.removeItem(item.getProduct().getId());
            } else {
                // Update kuantitas
                cart.updateItemQuantity(item.getProduct().getId(), newQty);
            }
        }
        
        showAlert(Alert.AlertType.INFORMATION, "Success", "Cart changes saved successfully!");
        
        // Kembali ke menu untuk me-refresh UI
        if (view.getMenuScene() != null) {
            stage.setScene(view.getMenuScene());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}