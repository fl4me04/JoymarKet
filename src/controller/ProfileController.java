package controller;

import javafx.stage.Stage;
import model.User;
import util.AlertHelper;
import view.MenuPage;
import view.ProfilePage;

public class ProfileController {

	private ProfilePage view;
	private Stage stage;
	private UserController userController;

	public ProfileController(ProfilePage view, Stage stage) {
        this.view = view;
        this.stage = stage;
        this.userController = new UserController();
        initializeTriggers();
    }

    private void initializeTriggers() {
        view.getUpdateBtn().setOnAction(e -> {
        	User currentUser = view.getCurrentUser();
        	
        	String id = view.getIdTf().getText();
            String name = view.getNameTf().getText();
            String email = view.getEmailTf().getText();
            String phone = view.getPhoneTf().getText();
            String address = view.getAddressTa().getText();
            String inputPass = view.getPassTf().getText();
            String inputConfPass = view.getConfPassTf().getText();

            String gender = "";
            if(view.getMaleRb().isSelected()) gender = "Male";
            else if(view.getFemaleRb().isSelected()) gender = "Female";
            
            String result = userController.updateProfile(currentUser, name, email, phone, address, inputPass, inputConfPass, gender);
            
            if(result.equals("SUCCESS")) {
            	AlertHelper.showInfo("Update Success", "Profile updated successfully!");
            	
            	currentUser.setFullName(name);
                currentUser.setEmail(email);
                currentUser.setPhone(phone);
                currentUser.setAddress(address);
                currentUser.setGender(gender);
                
                if(!inputPass.isEmpty()) currentUser.setPassword(inputPass);
            } else {
            	AlertHelper.showError("Update Failed", result);
            }
        });
        
        view.getBackBtn().setOnAction(e -> {
            navigateToMenu(view.getCurrentUser());
        });
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
}
