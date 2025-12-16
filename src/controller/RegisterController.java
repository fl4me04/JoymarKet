package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import util.AlertHelper;
import view.LoginPage;
import view.RegisterPage;

public class RegisterController {

	private RegisterPage view;
	private Stage stage;
	private UserController userController;
	
	public RegisterController(RegisterPage view, Stage stage) {
		this.view = view;
		this.stage = stage;
		this.userController = new UserController();
		initializeTriggers();
	}

	private void initializeTriggers() {
		view.getRegisBtn().setOnAction(e -> {
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
	        
	        String result = userController.register(name, email, pass, confPass, phone, address, gender);
	        
	        if(result.equals("SUCCESS")) {
	        	AlertHelper.showInfo("Registration Success", "Registration Success! Please Login");
	        	navigateToLogin();
	        } else {
	        	AlertHelper.showError("Registration Failed", result);
	        }
		});
		
		view.getLoginLink().setOnAction(e -> {
			navigateToLogin();
		});
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
}
