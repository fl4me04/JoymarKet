package controller;

import database.ProductHandler;
import javafx.stage.Stage;
import model.Product;
import util.AlertHelper;
import view.EditStockPage;
import view.MenuPage;
import view.ProductFormPage; // Pastikan form page dari step sebelumnya ada

public class EditStockController {

    private EditStockPage view;
    private Stage stage;
    private ProductHandler handler;

    public EditStockController(EditStockPage view, Stage stage) {
        this.view = view;
        this.stage = stage;
        this.handler = new ProductHandler();

        loadData();
        initializeTriggers();
    }

    private void loadData() {
        view.getTable().getItems().setAll(handler.getAllProducts());
    }

    private void initializeTriggers() {
        // Add Product
        view.getAddBtn().setOnAction(e -> {
            // Opening page to add new user
            ProductFormPage form = new ProductFormPage(view.getCurrentUser(), null);
            new ProductFormController(form, stage);
            stage.setScene(form.getScene());
        });

        // Edit Product
        view.getEditBtn().setOnAction(e -> {
            Product selected = view.getTable().getSelectionModel().getSelectedItem();
            if (selected == null) {
                AlertHelper.showError("Warning", "Select a product to edit.");
                return;
            }
            // Opening page with selected user to edit
            ProductFormPage form = new ProductFormPage(view.getCurrentUser(), selected);
            new ProductFormController(form, stage);
            stage.setScene(form.getScene());
        });

        // Delete Product
        view.getDeleteBtn().setOnAction(e -> {
            Product selected = view.getTable().getSelectionModel().getSelectedItem();
            if (selected == null) {
                AlertHelper.showError("Warning", "Select a product to delete.");
                return;
            }
            
            boolean success = handler.deleteProduct(selected.getIdProduct());
            
            if (success) {
                AlertHelper.showInfo("Deleted", "Product deleted successfully.");
                loadData(); 
            } else {
                AlertHelper.showError("Delete Failed", 
                    "Cannot delete this product because it has transaction history.\n" +
                    "Consider setting the Stock to 0 instead.");
            }
        });

        // Back to Menu
        view.getBackBtn().setOnAction(e -> {
            try {
                MenuPage menu = new MenuPage(view.getCurrentUser());
                new MenuController(menu, stage);
                stage.setScene(menu.getScene());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}