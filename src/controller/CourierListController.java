package controller;

import database.CourierHandler;
import javafx.stage.Stage;
import model.Courier;
import model.User;
import view.CourierFormPage;
import view.CourierListPage;
import view.MenuPage;

import java.util.ArrayList;

public class CourierListController {

    private CourierListPage view;
    private Stage stage;
    private CourierHandler courierHandler;

    public CourierListController(CourierListPage view, Stage stage) {
        this.view = view;
        this.stage = stage;
        this.courierHandler = new CourierHandler();

        loadData();
        initializeTriggers();
    }

    private void loadData() {
        ArrayList<Courier> couriers = courierHandler.getAllCouriers();
        
        view.getCourierTable().getItems().setAll(couriers);
    }

    private void initializeTriggers() {
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
        
        // Add new Courier
        view.getAddBtn().setOnAction(e -> {
            CourierFormPage formPage = new CourierFormPage(view.getCurrentUser(), null);
            new CourierFormController(formPage, stage);
            stage.setScene(formPage.getScene());
        });

        // Edit Courier
        view.getEditBtn().setOnAction(e -> {
            Courier selected = view.getCourierTable().getSelectionModel().getSelectedItem();
            if (selected == null) {
            	return;
            }

            CourierFormPage formPage = new CourierFormPage(view.getCurrentUser(), selected);
            new CourierFormController(formPage, stage);
            stage.setScene(formPage.getScene());
        });

        // Delete Courier
        view.getDeleteBtn().setOnAction(e -> {
            User selected = view.getCourierTable().getSelectionModel().getSelectedItem();
            if (selected == null) {
                util.AlertHelper.showError("Error", "Select a courier to delete.");
                return;
            }
            
            boolean success = courierHandler.deleteCourier(selected.getIdUser());
            if (success) {
                util.AlertHelper.showInfo("Deleted", "Courier removed successfully.");
                loadData(); 
            } else {
                util.AlertHelper.showError("Delete Failed", 
                    "Cannot delete this courier because they have delivery history.\n" + 
                    "Data cannot be removed to maintain transaction integrity.");
            }
        });
    }
}