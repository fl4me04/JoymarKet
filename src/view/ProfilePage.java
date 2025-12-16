package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Admin;
import model.Courier;
import model.User;

public class ProfilePage {

    private Scene scene;
    private BorderPane mainLayout;
    private User currentUser;

    private TextField nameField, emailField, phoneField;
    private PasswordField passField;
    private TextArea addressArea;
    private ComboBox<String> genderBox;
    
    private TextField emergencyField;

    private ComboBox<String> vehicleTypeBox;
    private TextField plateField;

    private Button saveBtn, backBtn;

    public ProfilePage(User user) {
        this.currentUser = user;
        initialize();
    }

    private void initialize() {
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));

        Label title = new Label("Edit Profile (" + currentUser.getRole() + ")");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);

        nameField = new TextField(currentUser.getFullName());
        emailField = new TextField(currentUser.getEmail());
        passField = new PasswordField();
        passField.setText(currentUser.getPassword()); // Pre-fill password
        phoneField = new TextField(currentUser.getPhone());
        addressArea = new TextArea(currentUser.getAddress());
        addressArea.setPrefHeight(60);
        
        genderBox = new ComboBox<>();
        genderBox.getItems().addAll("Male", "Female");
        genderBox.setValue(currentUser.getGender());

        int row = 0;
        grid.addRow(row++, new Label("Name:"), nameField);
        grid.addRow(row++, new Label("Email:"), emailField);
        grid.addRow(row++, new Label("Password:"), passField);
        grid.addRow(row++, new Label("Phone:"), phoneField);
        grid.addRow(row++, new Label("Address:"), addressArea);
        grid.addRow(row++, new Label("Gender:"), genderBox);
        
        // Role Specific Field
        if (currentUser instanceof Admin) {
            Admin admin = (Admin) currentUser;
            emergencyField = new TextField(admin.getEmergencyContact());
            
            grid.addRow(row++, new Label("Emergency Contact:"), emergencyField);
            
        } else if (currentUser instanceof Courier) {
            Courier courier = (Courier) currentUser;
            
            vehicleTypeBox = new ComboBox<>();
            vehicleTypeBox.getItems().addAll("Motorcycle", "Car", "Van", "Truck");
            vehicleTypeBox.setValue(courier.getVehicleType());
            
            plateField = new TextField(courier.getVehiclePlate());
            
            grid.addRow(row++, new Separator(), new Separator());
            grid.addRow(row++, new Label("Vehicle Type:"), vehicleTypeBox);
            grid.addRow(row++, new Label("License Plate:"), plateField);
        }

        saveBtn = new Button("Save Changes");
        saveBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-weight: bold;");
        backBtn = new Button("Back");

        HBox btnBox = new HBox(10, backBtn, saveBtn);
        btnBox.setAlignment(Pos.CENTER_RIGHT);

        VBox centerBox = new VBox(20, title, grid, btnBox);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setMaxWidth(600);

        mainLayout.setCenter(centerBox);
        scene = new Scene(mainLayout, 700, 600);
    }

	public Scene getScene() {
		return scene;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public TextField getNameField() {
		return nameField;
	}

	public TextField getEmailField() {
		return emailField;
	}

	public TextField getPhoneField() {
		return phoneField;
	}

	public PasswordField getPassField() {
		return passField;
	}

	public TextArea getAddressArea() {
		return addressArea;
	}

	public ComboBox<String> getGenderBox() {
		return genderBox;
	}

	public TextField getEmergencyField() {
		return emergencyField;
	}

	public ComboBox<String> getVehicleTypeBox() {
		return vehicleTypeBox;
	}

	public TextField getPlateField() {
		return plateField;
	}

	public Button getSaveBtn() {
		return saveBtn;
	}

	public Button getBackBtn() {
		return backBtn;
	}
}