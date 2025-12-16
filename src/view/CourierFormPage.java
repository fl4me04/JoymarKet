package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Courier;
import model.User;

public class CourierFormPage {

    private Scene scene;
    private BorderPane mainLayout;
    private User currentUser;

    private TextField idField;
    private TextField nameField;
    private TextField emailField;
    private PasswordField passField;
    private TextField phoneField;
    private TextArea addressArea;
    private ComboBox<String> genderBox;
    private Button saveBtn;
    private Button backBtn;
    
    private ComboBox<String> vehicleTypeBox;
    private TextField plateField;
    
    private Courier courierToEdit;

    public CourierFormPage(User admin, Courier courierToEdit) {
        this.currentUser = admin;
        this.courierToEdit = courierToEdit;
        initialize();
    }

    private void initialize() {
    	// Initialize UI
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));

        Label title = new Label(courierToEdit == null ? "Add New Courier" : "Edit Courier");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        idField = new TextField();
        idField.setDisable(true);
        
        nameField = new TextField();
        emailField = new TextField();
        passField = new PasswordField();
        phoneField = new TextField();
        addressArea = new TextArea();
        addressArea.setPrefHeight(60);
        
        genderBox = new ComboBox<>();
        genderBox.getItems().addAll("Male", "Female");

        vehicleTypeBox = new ComboBox<>();
        vehicleTypeBox.getItems().addAll("Motorcycle", "Car", "Van", "Truck");
        vehicleTypeBox.setPromptText("Select Type");

        plateField = new TextField();
        plateField.setPromptText("e.g., B 1234 XYZ");
        
        grid.addRow(0, new Label("ID:"), idField);
        grid.addRow(1, new Label("Name:"), nameField);
        grid.addRow(2, new Label("Email:"), emailField);
        grid.addRow(3, new Label("Password:"), passField);
        grid.addRow(4, new Label("Phone:"), phoneField);
        grid.addRow(5, new Label("Address:"), addressArea);
        grid.addRow(6, new Label("Gender:"), genderBox);
        grid.addRow(7, new Label("Vehicle Type:"), vehicleTypeBox);
        grid.addRow(8, new Label("License Plate:"), plateField);

        saveBtn = new Button(courierToEdit == null ? "Create Courier" : "Save Changes");
        saveBtn.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");
        
        backBtn = new Button("Cancel");

        HBox buttonBox = new HBox(10, backBtn, saveBtn);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        
        VBox centerBox = new VBox(20, title, grid, buttonBox);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setMaxWidth(700);

        mainLayout.setCenter(centerBox);
        scene = new Scene(mainLayout, 800, 500);

        // Set text for edited courier
        if (courierToEdit != null) {
            idField.setText(courierToEdit.getIdUser());
            nameField.setText(courierToEdit.getFullName());
            emailField.setText(courierToEdit.getEmail());
            passField.setText(courierToEdit.getPassword());
            phoneField.setText(courierToEdit.getPhone());
            addressArea.setText(courierToEdit.getAddress());
            genderBox.setValue(courierToEdit.getGender());
            vehicleTypeBox.setValue(courierToEdit.getVehicleType());
            plateField.setText(courierToEdit.getVehiclePlate());
        }
    }

	public Scene getScene() {
		return scene;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public TextField getIdField() {
		return idField;
	}

	public TextField getNameField() {
		return nameField;
	}

	public TextField getEmailField() {
		return emailField;
	}

	public PasswordField getPassField() {
		return passField;
	}

	public TextField getPhoneField() {
		return phoneField;
	}

	public TextArea getAddressArea() {
		return addressArea;
	}

	public ComboBox<String> getGenderBox() {
		return genderBox;
	}

	public Button getSaveBtn() {
		return saveBtn;
	}

	public Button getBackBtn() {
		return backBtn;
	}

	public ComboBox<String> getVehicleTypeBox() {
		return vehicleTypeBox;
	}

	public TextField getPlateField() {
		return plateField;
	}

	public Courier getCourierToEdit() {
		return courierToEdit;
	}
}