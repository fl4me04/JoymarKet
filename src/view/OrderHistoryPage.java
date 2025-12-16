package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.OrderHeader;
import model.OrderDetail;
import model.User;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;

public class OrderHistoryPage {

    private Scene scene;
    private BorderPane mainLayout;
    private User currentUser;

    private TableView<OrderHeader> orderTable;
    private TableView<OrderDetail> detailTable;
    private Label detailLabel;
    private Button backBtn;

    public OrderHistoryPage(User user) {
        this.currentUser = user;
        initialize();
    }

    @SuppressWarnings("unchecked")
	private void initialize() {
    	// Initialize UI
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));

        Label titleLabel = new Label("My Order History");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        orderTable = new TableView<>();
        orderTable.setPlaceholder(new Label("No order history available"));
        orderTable.setPrefHeight(250);

        TableColumn<OrderHeader, String> idCol = new TableColumn<>("Order ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("idOrder"));

        TableColumn<OrderHeader, String> dateCol = new TableColumn<>("Date");

        dateCol.setCellValueFactory(cell -> {
            Date date = cell.getValue().getOrderedAt();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            return new SimpleStringProperty(sdf.format(date));
        });

        TableColumn<OrderHeader, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<OrderHeader, String> totalCol = new TableColumn<>("Total Amount");
        NumberFormat idr = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        totalCol.setCellValueFactory(cell -> new SimpleStringProperty(idr.format(cell.getValue().getTotalAmount())));

        orderTable.getColumns().addAll(idCol, dateCol, statusCol, totalCol);
        orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox detailBox = new VBox(10);
        detailLabel = new Label("Order Details (Select an order above)");
        detailLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        detailTable = new TableView<>();
        detailTable.setPrefHeight(200);
        detailTable.setPlaceholder(new Label("Select an order to view items"));
        
        // Making detail order table
        TableColumn<OrderDetail, String> productCol = new TableColumn<>("Product Name");
        productCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getProduct().getName()));

        TableColumn<OrderDetail, Integer> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));

        TableColumn<OrderDetail, String> priceCol = new TableColumn<>("Price (Unit)");
        priceCol.setCellValueFactory(cell -> new SimpleStringProperty(idr.format(cell.getValue().getProduct().getPrice())));

        detailTable.getColumns().addAll(productCol, qtyCol, priceCol);
        detailTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        detailBox.getChildren().addAll(detailLabel, detailTable);

        backBtn = new Button("Back to Menu");
        
        VBox centerLayout = new VBox(20);
        centerLayout.getChildren().addAll(orderTable, new Separator(), detailBox);
        
        titleLabel.setPadding(new Insets(0, 0, 20, 0));

        mainLayout.setTop(titleLabel);
        mainLayout.setCenter(centerLayout);
        mainLayout.setBottom(backBtn);
        BorderPane.setMargin(backBtn, new Insets(20, 0, 0, 0));
        BorderPane.setAlignment(titleLabel, Pos.CENTER);

        scene = new Scene(mainLayout, 800, 600);
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