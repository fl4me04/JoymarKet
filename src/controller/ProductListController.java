package controller;

import java.util.ArrayList;

import database.CartItemHandler;
import database.ProductHandler;
import javafx.stage.Stage;
import model.Product;
import model.User;
import util.AlertHelper;
import view.MenuPage;
import view.ProductListPage;

public class ProductListController {

    private ProductListPage view;
    private Stage stage;
    private ProductHandler productHandler;
    private CartItemHandler cartHandler;

    public ProductListController(ProductListPage view, Stage stage) {
        this.view = view;
        this.stage = stage;
        this.productHandler = new ProductHandler();
        this.cartHandler = new CartItemHandler();
        
        loadData();
        initializeTriggers();
    }

    // Load Product Data from Database
    private void loadData() {
        ArrayList<Product> products = productHandler.getAllProducts();
        view.getProductTable().getItems().clear();
        view.getProductTable().getItems().addAll(products);
    }

    private void initializeTriggers() {
        // Event when product clicked
        view.getProductTable().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                view.getSelectedProductLabel().setText(newSelection.getName() + " (Stock: " + newSelection.getStock() + ")");
                view.getQuantitySpinner().getValueFactory().setValue(1);
            }
        });

        // Event when Add To Cart Button pressed
        view.getAddToCartBtn().setOnAction(e -> {
            handleAddProduct();
        });

        // Event Back Button
        view.getBackBtn().setOnAction(e -> {
            navigateBack();
        });
    }

    
    // Function to add product
    private void handleAddProduct() {
        Product selectedProduct = view.getProductTable().getSelectionModel().getSelectedItem();
        User currentUser = view.getCurrentUser();
        
        if (selectedProduct == null) {
            AlertHelper.showError("Error", "Select the product!");
            return;
        }

        int quantityToAdd = view.getQuantitySpinner().getValue();
        int stockDatabase = selectedProduct.getStock();
        int quantityInCart = cartHandler.getCurrentQtyInCart(currentUser.getIdUser(), selectedProduct.getId());
        int totalQuantity = quantityInCart + quantityToAdd;
        if (totalQuantity > stockDatabase) {
            int sisaBolehAmbil = stockDatabase - quantityInCart;
            AlertHelper.showError("Stock Limit", 
                "Stock is not available!\n" +
                "Warehouse Stock: " + stockDatabase + "\n" +
                "Already in Cart: " + quantityInCart + "\n" +
                "You only can add: " + (sisaBolehAmbil < 0 ? 0 : sisaBolehAmbil) + " more."
            );
            return;
        }

        boolean success = cartHandler.addToCart(currentUser.getIdUser(), selectedProduct.getId(), quantityToAdd);

        if (success) {
            AlertHelper.showInfo("Success", "Successfully added to cart!");
        } else {
            AlertHelper.showError("Error", "Failed to save in database.");
        }
    }

    private void navigateBack() {
        try {
            MenuPage menuPage = new MenuPage(view.getCurrentUser());
            new MenuController(menuPage, stage);
            stage.setScene(menuPage.getScene());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}