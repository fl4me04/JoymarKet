package controller;

import javafx.stage.Stage;
import model.User;
import util.AlertHelper;
import view.LoginPage;
import view.MenuPage;
import view.RegisterPage;

public class LoginController {

	private LoginPage view;
	private Stage stage;
	private UserController userController;
	
	public LoginController(LoginPage view, Stage stage) {
		this.view = view;
		this.stage = stage;
		this.userController = new UserController();
		// Triggers for Controller
		initializeTriggers();
	}

	private void initializeTriggers() {
		// Event Handler
		view.getLoginBtn().setOnAction(e -> {
			String emailInput = view.getEmailTf().getText();
			String passwordInput = view.getPasswordTf().getText();
			
			if(emailInput.isEmpty() || passwordInput.isEmpty()) {
				AlertHelper.showError("Input Error!", "Please fill in all fields!");
				return;
			}
			
			User user = userController.login(emailInput, passwordInput);
			
			if(user != null) {
				AlertHelper.showInfo("Login Success", "Login Successful!");
				
				try {
					MenuPage menuPage = new MenuPage(user);
					new MenuController(menuPage, stage);
					stage.setScene(menuPage.getScene());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				AlertHelper.showError("Login Failed", "Invalid email or password!");
			}
		});
		
		view.getRegisterLink().setOnAction(e -> {
			navigateToRegister();
		});
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
}
