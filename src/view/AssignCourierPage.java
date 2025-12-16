package view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.CartItem;
import model.OrderHeader;
import model.User;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AssignCourierPage {

    private Scene scene;
    private BorderPane mainLayout;
    private User currentUser;

    private TableView<OrderHeader> orderTable;
    private Label selectedOrderLabel;
    private ComboBox<User> courierComboBox;
    private Button assignBtn;
    private Button backBtn;

    public AssignCourierPage(User user) {
        this.currentUser = user;
        initialize();
    }

    @SuppressWarnings("unchecked")
	private void initialize() {
    	// Initialize UI
    	NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));

        Label title = new Label("Assign Orders to Courier");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        orderTable = new TableView<>();
        orderTable.setPlaceholder(new Label("No unassigned orders."));

        // Making Table for Order
        TableColumn<OrderHeader, String> idCol = new TableColumn<>("Order ID");
        idCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getIdOrder()));

        TableColumn<OrderHeader, String> dateCol = new TableColumn<>("Date");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        dateCol.setCellValueFactory(d -> new SimpleStringProperty(sdf.format(d.getValue().getOrderedAt())));

        TableColumn<OrderHeader, Double> amountCol = new TableColumn<>("Total");
        amountCol.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getTotalAmount()));
        
        // Formatting Amount Column into Rupiah
        amountCol.setCellFactory(column -> new TableCell<OrderHeader, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(item));
                }
            }
        });

        orderTable.getColumns().addAll(idCol, dateCol, amountCol);
        orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox formBox = new VBox(15);
        formBox.setPrefWidth(300);
        formBox.setPadding(new Insets(20));
        formBox.setStyle("-fx-background-color: #f4f4f4; -fx-background-radius: 5;");

        Label formTitle = new Label("Assignment Form");
        formTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        selectedOrderLabel = new Label("Select an order first...");
        selectedOrderLabel.setStyle("-fx-font-style: italic;");
        
        Label courierLabel = new Label("Select Courier:");
        courierComboBox = new ComboBox<>();
        courierComboBox.setPromptText("Choose Courier");
        courierComboBox.setMaxWidth(Double.MAX_VALUE);
        
        // Showing Available Courier
        courierComboBox.setCellFactory(param -> new ListCell<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(item.getFullName() + " (ID: " + item.getIdUser() + ")");
            }
        });
        
        courierComboBox.setButtonCell(new ListCell<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(item.getFullName());
            }
        });

        assignBtn = new Button("Assign Courier");
        assignBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-weight: bold;");
        assignBtn.setMaxWidth(Double.MAX_VALUE);

        formBox.getChildren().addAll(formTitle, new Separator(), new Label("Selected Order:"), selectedOrderLabel, courierLabel, courierComboBox, assignBtn);

        backBtn = new Button("Back to Menu");

        HBox centerContent = new HBox(20);
        centerContent.getChildren().addAll(orderTable, formBox);
        HBox.setHgrow(orderTable, Priority.ALWAYS);

        mainLayout.setTop(title);
        mainLayout.setCenter(centerContent);
        mainLayout.setBottom(backBtn);

        BorderPane.setMargin(title, new Insets(0, 0, 20, 0));
        BorderPane.setMargin(centerContent, new Insets(0, 0, 20, 0));

        scene = new Scene(mainLayout, 900, 600);
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

	public Label getSelectedOrderLabel() {
		return selectedOrderLabel;
	}

	public ComboBox<User> getCourierComboBox() {
		return courierComboBox;
	}

	public Button getAssignBtn() {
		return assignBtn;
	}

	public Button getBackBtn() {
		return backBtn;
	}
}