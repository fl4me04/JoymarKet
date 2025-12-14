package controller;

import javafx.stage.Stage;
import javafx.scene.control.Alert;

import model.Cart;
import model.CartItem;
import model.Customer;
import model.Product;
import view.AddToCartPage;

import database.TransactionManager;

public class CartController {

    private Cart cart;
    private AddToCartPage view;
    private Stage stage;
    private TransactionManager transactionManager;
    private Customer currentUser;

    public CartController(Cart cart, AddToCartPage view, Stage stage, Customer currentUser) { 
        this.transactionManager = new TransactionManager();
        this.currentUser = currentUser;
        
        Cart loadedCart = transactionManager.loadDraftCart(currentUser.getId());
        
        cart.clearCart(); 
        for (CartItem item : loadedCart.getItems()) {
            cart.getItems().add(item); 
        }

        this.cart = cart;
        this.view = view;
        this.stage = stage;
        
        initController();
    }

    private void initController() {
        for (int i = 0; i < view.getAddBtns().size(); i++) {
            int index = i;

            view.getAddBtns().get(i).setOnAction(e -> {
                Product product = view.getProducts().get(index);
                int qty = view.getQtyInputs().get(index).getValue();

                cart.addToCart(product, qty);
                handleSaveDraft();

                System.out.println("Added and Draft Saved: " + qty + " " + product.getName());
            });
        }

        view.getBackBtn().setOnAction(e -> {
            if (view.getMenuScene() != null) {
                stage.setScene(view.getMenuScene());
            }
        });

        view.getSubmitBtn().setOnAction(e -> handleSubmitOrder());
    }

    private void handleSaveDraft() {
        boolean success = transactionManager.updateDraftTransaction(cart, currentUser.getId());

        if (!success) {
            System.err.println("WARNING: Failed to save cart draft to database!");
        }
    }

    private void handleSubmitOrder() {

        if (cart.getItems().isEmpty()) {
            showAlert(
                Alert.AlertType.WARNING,
                "Cart Empty",
                "Your cart is empty. Please add some products before submitting."
            );
            return;
        }

        System.out.println("Attempting to submit order...");

        boolean success = transactionManager.saveTransaction(cart, currentUser.getId());

        if (success) {
            showAlert(
                Alert.AlertType.INFORMATION,
                "Success",
                "Order submitted successfully! Total: Rp " + cart.getTotal()
            );

            cart.clearCart();

            if (view.getMenuScene() != null) {
                stage.setScene(view.getMenuScene());
            }

        } else {
            showAlert(
                Alert.AlertType.ERROR,
                "Error",
                "Failed to submit order. Check console/database configuration."
            );
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