package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Courier;
import model.User;

public class CourierListPage {

    private Scene scene;
    private BorderPane mainLayout;
    private User currentUser;

    private TableView<Courier> courierTable;
    private Button backBtn, addBtn, editBtn, deleteBtn;

    public CourierListPage(User user) {
        this.currentUser = user;
        initialize();
    }

    @SuppressWarnings("unchecked")
	private void initialize() {
    	// Initialize UI
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));

        Label title = new Label("Courier Management");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        courierTable = new TableView<>();
        courierTable.setPlaceholder(new Label("No couriers found."));

        // Making table for courier
        TableColumn<Courier, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdUser()));

        TableColumn<Courier, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFullName()));

        TableColumn<Courier, String> phoneCol = new TableColumn<>("Phone Number");
        phoneCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPhone()));

        TableColumn<Courier, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

        TableColumn<Courier, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress()));
        
        TableColumn<Courier, String> genderCol = new TableColumn<>("Gender");
        genderCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGender()));

        TableColumn<Courier, String> vehicleCol = new TableColumn<>("Vehicle");
        vehicleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVehicleType()));

        TableColumn<Courier, String> plateCol = new TableColumn<>("Plate Number");
        plateCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVehiclePlate()));

        courierTable.getColumns().addAll(idCol, nameCol, phoneCol, emailCol, addressCol, genderCol, vehicleCol, plateCol);
        courierTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        backBtn = new Button("Back to Menu");
        addBtn = new Button("Add Courier");
        editBtn = new Button("Edit Selected");
        deleteBtn = new Button("Delete Selected");
        deleteBtn.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox bottomContainer = new HBox(10, backBtn, spacer, addBtn, editBtn, deleteBtn);
        bottomContainer.setPadding(new Insets(20, 0, 0, 0));

        mainLayout.setTop(title);
        mainLayout.setCenter(courierTable);
        mainLayout.setBottom(bottomContainer);

        BorderPane.setMargin(title, new Insets(0, 0, 20, 0));
        BorderPane.setMargin(courierTable, new Insets(0, 0, 0, 0));
        BorderPane.setAlignment(title, Pos.CENTER);

        scene = new Scene(mainLayout, 800, 500);
    }

	public Scene getScene() {
		return scene;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public TableView<Courier> getCourierTable() {
		return courierTable;
	}

	public Button getBackBtn() {
		return backBtn;
	}

	public Button getAddBtn() {
		return addBtn;
	}

	public Button getEditBtn() {
		return editBtn;
	}

	public Button getDeleteBtn() {
		return deleteBtn;
	}
}