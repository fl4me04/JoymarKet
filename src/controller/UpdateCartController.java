package controller;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import database.CartItemHandler;
import javafx.stage.Stage;
import model.CartItem;
import model.User;
import util.AlertHelper;
import view.CheckoutPage;
import view.MenuPage;
import view.UpdateCartPage;

public class UpdateCartController {

    private UpdateCartPage view;
    private Stage stage;
    private CartItemHandler cartHandler;
    private User currentUser;

    public UpdateCartController(UpdateCartPage view, Stage stage) {
        this.view = view;
        this.stage = stage;
        this.currentUser = view.getCurrentUser();
        this.cartHandler = new CartItemHandler();

        loadCartData();
        initializeTriggers();
    }

    // Function to load Cart from Database
    private void loadCartData() {
        ArrayList<CartItem> items = cartHandler.getCartItems(currentUser.getIdUser());
        
        view.getCartTable().getItems().clear();
        view.getCartTable().getItems().addAll(items);
        
        calculateGrandTotal(items);
    }

    // Function to calculate grand total items
    private void calculateGrandTotal(ArrayList<CartItem> items) {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        
        // Change into Rupiah Format
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        String formattedTotal = currencyFormat.format(total);
        
        view.getTotalPriceLabel().setText("Grand Total: " + formattedTotal);
    }

    // Function to Initialize Trigger
    private void initializeTriggers() {
        // Select Which Items to be edited
        view.getCartTable().getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                view.getSelectedItemLabel().setText(newVal.getProduct().getName());
                view.getQuantitySpinner().getValueFactory().setValue(newVal.getCount());
            }
        });

        // Update Item Handler
        view.getUpdateBtn().setOnAction(e -> {
            handleUpdateItem();
        });

        // Delete Button Handler
        view.getDeleteBtn().setOnAction(e -> {
            handleDeleteItem();
        });
        
        view.getCheckoutBtn().setOnAction(e -> {
        	navigateToCheckout();
        });

        // Back to Menu Button
        view.getBackBtn().setOnAction(e -> navigateBack());
    }

    private void navigateToCheckout() {
    	try {
			User currentUser = view.getCurrentUser();
			
			CheckoutPage checkoutPage = new CheckoutPage(currentUser);
		    new CheckoutController(checkoutPage, stage);
		    stage.setScene(checkoutPage.getScene());
		    stage.setTitle("Checkout");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Function to Handle Update Item
    private void handleUpdateItem() {
        CartItem selectedItem = view.getCartTable().getSelectionModel().getSelectedItem();
        
        if (selectedItem == null) {
            AlertHelper.showError("Selection Error", "Please select an item to update!");
            return;
        }

        int newQty = view.getQuantitySpinner().getValue();
        int currentStock = selectedItem.getProduct().getStock();

        if (newQty <= 0) {
            AlertHelper.showError("Invalid Quantity", "Quantity must be greater than 0.");
            return;
        }
        
        if (newQty > currentStock) {
            AlertHelper.showError("Stock Limit", "Not enough stock! Max: " + currentStock);
            return;
        }

        boolean success = cartHandler.editCartItem(currentUser.getIdUser(), selectedItem.getProduct().getIdProduct(), newQty);

        if (success) {
            AlertHelper.showInfo("Success", "Cart updated successfully!");
            loadCartData();
        } else {
            AlertHelper.showError("Failed", "Failed to update cart.");
        }
    }

    // Function to Handle Delete Item
    private void handleDeleteItem() {
        CartItem selectedItem = view.getCartTable().getSelectionModel().getSelectedItem();
        
        if (selectedItem == null) {
            AlertHelper.showError("Selection Error", "Please select an item to remove!");
            return;
        }

        boolean success = cartHandler.deleteCartItem(currentUser.getIdUser(), selectedItem.getProduct().getIdProduct());

        if (success) {
            AlertHelper.showInfo("Success", "Item removed from cart.");
            view.getCartTable().getSelectionModel().clearSelection();
            view.getSelectedItemLabel().setText("Select an item to edit");            
            loadCartData();
        } else {
            AlertHelper.showError("Failed", "Failed to remove item.");
        }
    }

    private void navigateBack() {
        try {
            MenuPage menuPage = new MenuPage(currentUser);
            new MenuController(menuPage, stage);
            stage.setScene(menuPage.getScene());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}