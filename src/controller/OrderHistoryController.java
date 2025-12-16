package controller;

import java.util.ArrayList;
import database.OrderHandler;
import javafx.stage.Stage;
import model.OrderDetail;
import model.OrderHeader;
import view.MenuPage;
import view.OrderHistoryPage;

public class OrderHistoryController {

    private OrderHistoryPage view;
    private Stage stage;
    private OrderHandler orderHandler;

    public OrderHistoryController(OrderHistoryPage view, Stage stage) {
        this.view = view;
        this.stage = stage;
        this.orderHandler = new OrderHandler();

        loadOrderData();
        initializeTriggers();
    }

    private void loadOrderData() {
        ArrayList<OrderHeader> orders = orderHandler.getCustomerOrders(view.getCurrentUser().getIdUser());
        
        view.getOrderTable().getItems().setAll(orders);
    }

    private void initializeTriggers() {
        view.getOrderTable().getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadOrderDetails(newVal.getIdOrder());
                view.getDetailLabel().setText("Order Details: " + newVal.getIdOrder());
            }
        });

        view.getBackBtn().setOnAction(e -> {
            try {
                MenuPage menuPage = new MenuPage(view.getCurrentUser());
                new MenuController(menuPage, stage);
                stage.setScene(menuPage.getScene());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void loadOrderDetails(String idOrder) {
        ArrayList<OrderDetail> details = orderHandler.getOrderDetails(idOrder);
        view.getDetailTable().getItems().setAll(details);
    }
}