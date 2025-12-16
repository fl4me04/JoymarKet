package controller;

import database.OrderHandler;
import javafx.stage.Stage;
import model.OrderDetail;
import model.OrderHeader;
import view.AllOrdersPage;
import view.MenuPage;

import java.util.ArrayList;

public class AllOrdersController {

    private AllOrdersPage view;
    private Stage stage;
    private OrderHandler orderHandler;

    public AllOrdersController(AllOrdersPage view, Stage stage) {
        this.view = view;
        this.stage = stage;
        this.orderHandler = new OrderHandler();

        loadData();
        initializeTriggers();
    }

    private void loadData() {
        ArrayList<OrderHeader> orders = orderHandler.getAllOrders();
        view.getOrderTable().getItems().setAll(orders);
    }

    private void initializeTriggers() {
        view.getOrderTable().getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                view.getDetailLabel().setText("Details for Order: " + newVal.getIdOrder() + " (Customer: " + newVal.getIdCustomer() + ")");
                loadDetails(newVal.getIdOrder());
            } else {
                view.getDetailLabel().setText("Order Details");
                view.getDetailTable().getItems().clear();
            }
        });

        view.getBackBtn().setOnAction(e -> navigateToMenu());
    }

    private void loadDetails(String idOrder) {
        ArrayList<OrderDetail> details = orderHandler.getOrderDetails(idOrder);
        view.getDetailTable().getItems().setAll(details);
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