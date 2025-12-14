package controller;

import javafx.stage.Stage;
import model.Cart;
import model.Customer;
import model.Product;
import view.UpdateCartPage;

import database.TransactionManager;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class UpdateCartController {

    private Cart cart;
    private UpdateCartPage view;
    private Stage stage;
    private TransactionManager transactionManager;
    private Customer currentUser;

    public UpdateCartController(Cart cart, UpdateCartPage view, Stage stage, Customer currentUser) {
        this.cart = cart;
        this.view = view;
        this.stage = stage;
        this.currentUser = currentUser;
        this.transactionManager = new TransactionManager();
        initController();
    }

    private void initController() {
       
        for (int i = 0; i < view.getAddBtns().size(); i++) {
            int index = i;
            view.getAddBtns().get(i).setOnAction(e -> {
                Product product = view.getProducts().get(index);
                int qty = view.getAddQtyInputs().get(index).getValue();

                cart.addToCart(product, qty);
                handleSaveDraft();
                
                
                view.refreshItems(); 
           
                initController(); 
                
                showAlert(Alert.AlertType.INFORMATION, "Added", qty + " " + product.getName() + " added. Draft saved.");
            });
        }
        
        
        view.getSaveChangesBtn().setOnAction(e -> {
            handleUpdateQuantities();
            handleSaveDraft();
            
            showAlert(
                Alert.AlertType.INFORMATION, 
                "Success", 
                "Cart updated and draft saved successfully! Total: Rp " + cart.getTotal()
            );
            
            if (view.getMenuScene() != null) {
                stage.setScene(view.getMenuScene());
            }
        });
        
       
        for (int i = 0; i < view.getDeleteBtns().size(); i++) {
            Button deleteBtn = view.getDeleteBtns().get(i);
            int index = i;
            
            deleteBtn.setOnAction(e -> {
                cart.removeItem(cart.getItems().get(index).getProduct());
                
                handleSaveDraft();

                showAlert(
                    Alert.AlertType.INFORMATION, 
                    "Removed", 
                    "Item removed. Reloading view..."
                );
                
                
                view.refreshItems();
                
                initController(); 
            });
        }
        
        view.getBackBtn().setOnAction(e -> {
            if (view.getMenuScene() != null) {
                stage.setScene(view.getMenuScene());
            }
        });
    }

    private void handleUpdateQuantities() {
        for (int i = 0; i < cart.getItems().size(); i++) {
            int newQty = view.getUpdateQtyInputs().get(i).getValue();
            cart.updateItemQuantity(cart.getItems().get(i).getProduct(), newQty);
        }
    }

    private void handleSaveDraft() {
        boolean success = transactionManager.updateDraftTransaction(cart, currentUser.getId());

        if (!success) {
            System.err.println("WARNING: Failed to save cart draft to database from Update Cart!");
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