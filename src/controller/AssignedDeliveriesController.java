package controller;

import database.DeliveryHandler;
import javafx.stage.Stage;
import model.Delivery;
import util.AlertHelper;
import view.AssignedDeliveriesPage;
import view.MenuPage;

import java.util.ArrayList;

public class AssignedDeliveriesController {

    private AssignedDeliveriesPage view;
    private Stage stage;
    private DeliveryHandler deliveryHandler;

    public AssignedDeliveriesController(AssignedDeliveriesPage view, Stage stage) {
        this.view = view;
        this.stage = stage;
        this.deliveryHandler = new DeliveryHandler();

        loadData();
        initializeTriggers();
    }

    private void loadData() {
        String idCourier = view.getCurrentUser().getIdUser();
        
        ArrayList<Delivery> list = deliveryHandler.getDeliveriesByCourier(idCourier);
        view.getDeliveryTable().getItems().setAll(list);
    }

    private void initializeTriggers() {
        view.getDeliveryTable().getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                view.getSelectedOrderLabel().setText("Order ID: " + newVal.getIdOrder());
                view.getStatusComboBox().setValue(newVal.getStatus());
            } else {
                view.getSelectedOrderLabel().setText("Select a delivery...");
            }
        });

        view.getUpdateBtn().setOnAction(e -> handleUpdate());

        view.getBackBtn().setOnAction(e -> navigateToMenu());
    }

    private void handleUpdate() {
        Delivery selected = view.getDeliveryTable().getSelectionModel().getSelectedItem();
        String newStatus = view.getStatusComboBox().getValue();

        if (selected == null) {
            AlertHelper.showError("Error", "Please select a delivery first.");
            return;
        }
        if (newStatus == null || newStatus.isEmpty()) {
            AlertHelper.showError("Error", "Please select a status.");
            return;
        }

        boolean success = deliveryHandler.updateDeliveryStatus(selected.getIdOrder(), newStatus);

        if (success) {
            AlertHelper.showInfo("Success", "Status updated to: " + newStatus);
            loadData();
            view.getDeliveryTable().getSelectionModel().clearSelection();
        } else {
            AlertHelper.showError("Failed", "Database update failed.");
        }
    }

    private void navigateToMenu() {
        try {
            MenuPage menu = new MenuPage(view.getCurrentUser());
            new MenuController(menu, stage);
            stage.setScene(menu.getScene());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}