package controller;

import database.DeliveryHandler;
import javafx.stage.Stage;
import model.OrderHeader;
import model.User;
import util.AlertHelper;
import view.AssignCourierPage;
import view.MenuPage;

import java.util.ArrayList;

public class AssignCourierController {

    private AssignCourierPage view;
    private Stage stage;
    private DeliveryHandler deliveryHandler;

    public AssignCourierController(AssignCourierPage view, Stage stage) {
        this.view = view;
        this.stage = stage;
        this.deliveryHandler = new DeliveryHandler();

        loadData();
        initializeTriggers();
    }

    private void loadData() {
        ArrayList<OrderHeader> orders = deliveryHandler.getUnassignedOrders();
        view.getOrderTable().getItems().setAll(orders);

        ArrayList<User> couriers = deliveryHandler.getAvailableCouriers();
        view.getCourierComboBox().getItems().setAll(couriers);
    }

    private void initializeTriggers() {
        view.getOrderTable().getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                view.getSelectedOrderLabel().setText(newVal.getIdOrder());
                view.getSelectedOrderLabel().setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
            } else {
                view.getSelectedOrderLabel().setText("Select an order first...");
                view.getSelectedOrderLabel().setStyle("-fx-font-style: italic;");
            }
        });

        view.getAssignBtn().setOnAction(e -> handleAssignment());

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

    private void handleAssignment() {
        OrderHeader selectedOrder = view.getOrderTable().getSelectionModel().getSelectedItem();
        User selectedCourier = view.getCourierComboBox().getValue();

        if (selectedOrder == null) {
            AlertHelper.showError("Selection Error", "Please select an order from the table.");
            return;
        }
        if (selectedCourier == null) {
            AlertHelper.showError("Selection Error", "Please select a courier.");
            return;
        }

        boolean success = deliveryHandler.assignCourier(selectedOrder.getIdOrder(), selectedCourier.getIdUser());

        if (success) {
            AlertHelper.showInfo("Success", "Order " + selectedOrder.getIdOrder() + " assigned to " + selectedCourier.getFullName());
            loadData();
            
            view.getCourierComboBox().getSelectionModel().clearSelection();
        } else {
            AlertHelper.showError("Failed", "Could not assign courier. Database error.");
        }
    }
}