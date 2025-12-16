package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Delivery;
import model.User;

public class AssignedDeliveriesPage {

    private Scene scene;
    private BorderPane mainLayout;
    private User currentUser; // Courier

    // Components
    private TableView<Delivery> deliveryTable;
    private Label selectedOrderLabel;
    private ComboBox<String> statusComboBox;
    private Button updateBtn;
    private Button backBtn;

    public AssignedDeliveriesPage(User user) {
        this.currentUser = user;
        initialize();
    }

    @SuppressWarnings("unchecked")
	private void initialize() {
    	// Initialize UI
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));

        Label title = new Label("Assigned Deliveries");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        deliveryTable = new TableView<>();
        deliveryTable.setPlaceholder(new Label("No deliveries assigned yet."));

        TableColumn<Delivery, String> orderCol = new TableColumn<>("Order ID");
        orderCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdOrder()));

        TableColumn<Delivery, String> statusCol = new TableColumn<>("Current Status");
        statusCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));

        deliveryTable.getColumns().addAll(orderCol, statusCol);
        deliveryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox formBox = new VBox(15);
        formBox.setPrefWidth(300);
        formBox.setPadding(new Insets(20));
        formBox.setStyle("-fx-background-color: #f4f4f4; -fx-background-radius: 5;");

        Label formTitle = new Label("Update Status");
        formTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        selectedOrderLabel = new Label("Select a delivery...");
        selectedOrderLabel.setStyle("-fx-font-style: italic;");

        Label statusLabel = new Label("New Status:");
        statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("Shipping", "Delivered", "Canceled", "Pending");
        statusComboBox.setMaxWidth(Double.MAX_VALUE);

        updateBtn = new Button("Update Status");
        updateBtn.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");
        updateBtn.setMaxWidth(Double.MAX_VALUE);

        formBox.getChildren().addAll(formTitle, new Separator(), selectedOrderLabel, statusLabel, statusComboBox, updateBtn);

        backBtn = new Button("Back to Menu");

        HBox centerContent = new HBox(20);
        centerContent.getChildren().addAll(deliveryTable, formBox);
        HBox.setHgrow(deliveryTable, Priority.ALWAYS);

        mainLayout.setTop(title);
        mainLayout.setCenter(centerContent);
        mainLayout.setBottom(backBtn);
        
        BorderPane.setMargin(title, new Insets(0, 0, 20, 0));
        BorderPane.setMargin(centerContent, new Insets(0, 0, 20, 0));
        BorderPane.setMargin(backBtn, new Insets(0, 0, 0, 0));

        scene = new Scene(mainLayout, 850, 550);
    }

	public Scene getScene() {
		return scene;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public TableView<Delivery> getDeliveryTable() {
		return deliveryTable;
	}

	public Label getSelectedOrderLabel() {
		return selectedOrderLabel;
	}

	public ComboBox<String> getStatusComboBox() {
		return statusComboBox;
	}

	public Button getUpdateBtn() {
		return updateBtn;
	}

	public Button getBackBtn() {
		return backBtn;
	}
}