package controller;

import database.ProductHandler;
import javafx.stage.Stage;
import model.Product;
import util.AlertHelper;
import view.EditStockPage;
import view.ProductFormPage;

public class ProductFormController {

    private ProductFormPage view;
    private Stage stage;
    private ProductHandler handler;

    public ProductFormController(ProductFormPage view, Stage stage) {
        this.view = view;
        this.stage = stage;
        this.handler = new ProductHandler();

        if (view.getProductToEdit() == null) {
            view.getIdField().setText(handler.generateNewId());
        }

        initializeTriggers();
    }

    private void initializeTriggers() {
        view.getSaveBtn().setOnAction(e -> handleSave());
        view.getCancelBtn().setOnAction(e -> backToList());
    }

    private void handleSave() {
        try {
            String id = view.getIdField().getText();
            String name = view.getNameField().getText();
            String cat = view.getCategoryBox().getValue();
            int stock = view.getStockSpinner().getValue();
            
            if (name.isEmpty() || view.getPriceField().getText().isEmpty() || cat == null) {
                AlertHelper.showError("Error", "All fields must be filled.");
                return;
            }

            double price = Double.parseDouble(view.getPriceField().getText());
            if (price < 0) {
                AlertHelper.showError("Error", "Price cannot be negative.");
                return;
            }

            Product p = new Product(id, name, price, stock, cat);
            boolean success;

            if (view.getProductToEdit() == null) {
                success = handler.insertProduct(p);
            } else {
                success = handler.updateProduct(p);
            }

            if (success) {
                AlertHelper.showInfo("Success", "Product saved successfully!");
                backToList();
            } else {
                AlertHelper.showError("Failed", "Database error.");
            }

        } catch (NumberFormatException e) {
            AlertHelper.showError("Invalid Input", "Price must be a valid number.");
        }
    }

    private void backToList() {
        EditStockPage page = new EditStockPage(view.getCurrentUser());
        new EditStockController(page, stage);
        stage.setScene(page.getScene());
    }
}