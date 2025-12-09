package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;
import view.MenuPage;
import view.ProfilePage;

public class ProfileController {

	private ProfilePage view;
	private Stage stage;
	private UserHandler userHandler;

	public ProfileController(ProfilePage view, Stage stage) {
        this.view = view;
        this.stage = stage;
        this.userHandler = new UserHandler();
        initializeTriggers();
    }

    private void initializeTriggers() {
        view.getUpdateBtn().setOnAction(e -> updateProfile());
        
        view.getBackBtn().setOnAction(e -> {
            navigateToMenu(view.getCurrentUser());
        });
    }

    private void updateProfile() {
        // Taking data input
        String id = view.getIdTf().getText();
        String name = view.getNameTf().getText();
        String email = view.getEmailTf().getText();
        String phone = view.getPhoneTf().getText();
        String address = view.getAddressTa().getText();
        String inputPass = view.getPassTf().getText();
        String inputConfPass = view.getConfPassTf().getText();
        String finalPassword = ""; 

        String gender = "";
        if(view.getMaleRb().isSelected()) gender = "Male";
        else if(view.getFemaleRb().isSelected()) gender = "Female";

        // 1. Validate Name
        if(name.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Full Name cannot be empty!");
            return;
        }

        // 2. Validate Email
        if(email.isEmpty() || !email.endsWith("@gmail.com") || email.indexOf('@') < 0) {
            showAlert(AlertType.ERROR, "Error", "Email must be valid (@gmail.com)!");
            return;
        }

        // 3. If Password PasswordField is empty, take from old Password
        if (inputPass.isEmpty()) {
            finalPassword = view.getCurrentUser().getPassword(); 
        } else {
            if(inputPass.length() < 6) {
                showAlert(AlertType.ERROR, "Error", "New Password must be at least 6 characters!");
                return;
            }
            if(!inputPass.equals(inputConfPass)) {
                showAlert(AlertType.ERROR, "Error", "Confirm Password must match!");
                return;
            }
            finalPassword = inputPass;
        }

        // 4. Validate Phone Number
        if(phone.length() < 10 || phone.length() > 13) {
            showAlert(AlertType.ERROR, "Error", "Phone number must be 10-13 digits!");
            return;
        }
        boolean isNumeric = true;
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                isNumeric = false; break;
            }
        }
        if(!isNumeric) {
            showAlert(AlertType.ERROR, "Error", "Phone number must be numeric!");
            return;
        }

        // 5. Validate Address and Gender
        if(address.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Address cannot be empty!");
            return;
        }
        if(gender.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Please select a gender!");
            return;
        }

        // 6. Check Email Uniqueness
        boolean isAvailable = userHandler.checkEmailAvailabilityForUpdate(email, id);
        if(!isAvailable) {
            showAlert(AlertType.ERROR, "Error", "Email is already taken!");
            return;
        }

        // 7. Update Database
        boolean success = userHandler.editProfile(id, name, email, finalPassword, phone, address, gender);
        
        if(success) {
            showAlert(AlertType.INFORMATION, "Success", "Profile Updated!");
            User updatedUser = userHandler.getUser(id); 
            navigateToMenu(updatedUser);           
        } else {
            showAlert(AlertType.ERROR, "Error", "Failed to update profile.");
        }
    }

    private void navigateToMenu(User user) {
        try {
            MenuPage menuPage = new MenuPage(user);
            new MenuController(menuPage, stage);
            stage.setScene(menuPage.getScene());
            stage.setTitle("Main Menu");
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
