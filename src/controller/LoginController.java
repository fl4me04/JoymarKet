package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;
import view.LoginPage;
import view.MenuPage;
import view.RegisterPage;

public class LoginController {

	private LoginPage view;
	private Stage stage;
	private UserHandler userHandler;
	
	public LoginController(LoginPage view, Stage stage) {
		this.view = view;
		this.stage = stage;
		// Triggers for Controller
		initializeTriggers();
	}

	private void initializeTriggers() {
		// Event Handler
		view.getLoginBtn().setOnAction(e -> {
			login();
		});
		
		view.getRegisterLink().setOnAction(e -> {
			navigateToRegister();
		});
	}
	
	private void login() {
		String email = view.getEmailTf().getText();
		String password = view.getPasswordTf().getText();
		
		if(email.isEmpty() || password.isEmpty()) {
			showAlert(AlertType.ERROR, "Error!!!", "Please fill in all fields!");
			return;
		}
		
		userHandler = new UserHandler();
		User user = userHandler.login(email, password);
		
		if(user != null) {
			showAlert(AlertType.INFORMATION, "Success", "Login Successful!");
			try {
	            MenuPage menuPage = new MenuPage(user);
	            new MenuController(menuPage, stage);
	            
	            stage.setScene(menuPage.getScene());    
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
        } else {
            showAlert(AlertType.ERROR, "Login Failed", "Invalid Email or Password");
        }
	}
	
	private void navigateToRegister() {
	    try {
	        RegisterPage regPage = new RegisterPage();
	        new RegisterController(regPage, this.stage); 
	        stage.setScene(regPage.getScene());
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
