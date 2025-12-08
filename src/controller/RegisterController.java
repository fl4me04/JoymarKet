package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import view.LoginPage;
import view.RegisterPage;

public class RegisterController {

	private RegisterPage view;
	private Stage stage;
	private UserHandler userHandler;
	
	public RegisterController(RegisterPage view, Stage stage) {
		this.view = view;
		this.stage = stage;
		this.userHandler = new UserHandler();
		initializeTriggers();
	}

	private void initializeTriggers() {
		view.getRegisBtn().setOnAction(e -> {
			register();
		});
		
		view.getLoginLink().setOnAction(e -> {
			navigateToLogin();
		});
	}
	
	private void register() {
		String name = view.getUsernameTf().getText();
        String email = view.getEmailTf().getText();
        String pass = view.getPasswordTf().getText();
        String confPass = view.getConfirmTf().getText();
        String phone = view.getPhoneTf().getText();
        String address = view.getAddressTa().getText();
        
        // Gender Selected
        String gender = "";
        if(view.getMaleRb().isSelected()) gender = "Male";
        else if(view.getFemaleRb().isSelected()) gender = "Female";

        // 1. Full Name: Cannot be empty
        if(name.isEmpty()) {
            showAlert(AlertType.ERROR, "Validation Error", "Full Name cannot be empty!");
            return;
        }

        // 2. Email: Must be filled, ends with @gmail.com
        if(email.isEmpty() || !email.endsWith("@gmail.com")) {
            showAlert(AlertType.ERROR, "Validation Error", "Email must end with @gmail.com!");
            return;
        }

        // 3. Password: Must be at least 6 characters
        if(pass.length() < 6) {
            showAlert(AlertType.ERROR, "Validation Error", "Password must be at least 6 characters!");
            return;
        }

        // 4. Confirm Password: Must match Password
        if(!pass.equals(confPass)) {
            showAlert(AlertType.ERROR, "Validation Error", "Confirm Password must match Password!");
            return;
        }

        // 5. Validate Phone Number without REGEX
        if(phone.length() < 10 || phone.length() > 13) {
            showAlert(AlertType.ERROR, "Validation Error", "Phone number must be between 10-13 digits!");
            return;
        }
        
        boolean isNumeric = true;
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                isNumeric = false;
                break;
            }
        }
        
        if(!isNumeric) {
            showAlert(AlertType.ERROR, "Validation Error", "Phone number must be numeric (digits only)!");
            return;
        }

        // 6. Address: Must be filled
        if(address.isEmpty()) {
            showAlert(AlertType.ERROR, "Validation Error", "Address cannot be empty!");
            return;
        }

        // 7. Gender: Must be chosen
        if(gender.isEmpty()) {
            showAlert(AlertType.ERROR, "Validation Error", "Please select a gender!");
            return;
        }

        // 8. Check Uniqueness Email and Insert to Database
        boolean isEmailAvailable = userHandler.checkEmailAvailability(email);
        
        if (!isEmailAvailable) {
            showAlert(AlertType.ERROR, "Register Failed", "Email is already registered!");
            return;
        }

        // Insert Data via Handler
        boolean success = userHandler.addNewUser(name, email, pass, phone, address, gender);
        
        if (success) {
            showAlert(AlertType.INFORMATION, "Success", "Registration Successful!");
            navigateToLogin();
        } else {
            showAlert(AlertType.ERROR, "Database Error", "Registration failed due to database error.");
        }
	}
	
	private void navigateToLogin() {
	    try {
	        LoginPage loginPage = new LoginPage();
	        new LoginController(loginPage, stage);
	        
	        stage.setScene(loginPage.getScene());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
