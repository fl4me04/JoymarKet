package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.OrderDetail;
import model.OrderHeader;
import model.User;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AllOrdersPage {

    private Scene scene;
    private BorderPane mainLayout;
    private User currentUser;

    private TableView<OrderHeader> orderTable;
    private TableView<OrderDetail> detailTable;
    private Label detailLabel;
    private Button backBtn;

    public AllOrdersPage(User user) {
        this.currentUser = user;
        initialize();
    }

    @SuppressWarnings("unchecked")
	private void initialize() {
    	// Initialize UI
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));

        // Header
        Label title = new Label("View All Orders");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        orderTable = new TableView<>();
        orderTable.setPlaceholder(new Label("No orders found in the system."));
        orderTable.setPrefHeight(300);

        // Making table for order
        TableColumn<OrderHeader, String> idCol = new TableColumn<>("Order ID");
        idCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getIdOrder()));

        TableColumn<OrderHeader, String> custCol = new TableColumn<>("Customer ID");
        custCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getIdCustomer()));

        TableColumn<OrderHeader, String> dateCol = new TableColumn<>("Date");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        dateCol.setCellValueFactory(d -> new SimpleStringProperty(sdf.format(d.getValue().getOrderedAt())));

        TableColumn<OrderHeader, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getStatus()));
        
        // Formatting status column UI
        statusCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (item.equalsIgnoreCase("Pending")) setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
                    else if (item.equalsIgnoreCase("Completed")) setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    else setStyle("-fx-font-weight: bold;");
                }
            }
        });

        TableColumn<OrderHeader, String> totalCol = new TableColumn<>("Total Amount");
        NumberFormat idr = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        totalCol.setCellValueFactory(d -> new SimpleStringProperty(idr.format(d.getValue().getTotalAmount())));

        orderTable.getColumns().addAll(idCol, custCol, dateCol, statusCol, totalCol);
        orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox detailBox = new VBox(10);
        detailLabel = new Label("Order Details");
        detailLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        detailTable = new TableView<>();
        detailTable.setPrefHeight(200);
        detailTable.setPlaceholder(new Label("Select an order above to view details"));

        // Making table for detail order
        TableColumn<OrderDetail, String> productCol = new TableColumn<>("Product");
        productCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getProduct().getName()));

        TableColumn<OrderDetail, Integer> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));

        TableColumn<OrderDetail, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(d -> new SimpleStringProperty(idr.format(d.getValue().getProduct().getPrice())));

        detailTable.getColumns().addAll(productCol, qtyCol, priceCol);
        detailTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        detailBox.getChildren().addAll(detailLabel, detailTable);

        backBtn = new Button("Back to Menu");

        VBox centerLayout = new VBox(20);
        centerLayout.getChildren().addAll(orderTable, new Separator(), detailBox);

        mainLayout.setTop(title);
        mainLayout.setCenter(centerLayout);
        mainLayout.setBottom(backBtn);
        
        BorderPane.setMargin(title, new Insets(0, 0, 20, 0));
        BorderPane.setMargin(backBtn, new Insets(20, 0, 0, 0));
        BorderPane.setAlignment(title, Pos.CENTER);

        scene = new Scene(mainLayout, 900, 650);
    }

	public Scene getScene() {
		return scene;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public TableView<OrderHeader> getOrderTable() {
		return orderTable;
	}

	public TableView<OrderDetail> getDetailTable() {
		return detailTable;
	}

	public Label getDetailLabel() {
		return detailLabel;
	}

	public Button getBackBtn() {
		return backBtn;
	}
}