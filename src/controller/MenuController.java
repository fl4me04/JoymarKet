package controller;

import javafx.stage.Stage;
import model.User;
import view.LoginPage;
import view.MenuPage;
import view.ProfilePage;

public class MenuController {
    
    private MenuPage view;
    private Stage stage;

    public MenuController(MenuPage view, Stage stage) {
        this.view = view;
        this.stage = stage;
        initializeTriggers();
    }

    private void initializeTriggers() {
        // 1. Handle Logout
        view.getLogoutBtn().setOnAction(e -> {
            navigateToLogin();
        });

        // 2. Handle Top Up Button
        if (view.getTopupBtn() != null) {
            view.getTopupBtn().setOnAction(e -> {
                System.out.println("Navigasi ke halaman Top Up...");
                // new TopUpController(...)
            });
        }
        
        // 3. Handle Tombol Admin
        if (view.getEditStockBtn() != null) {
            view.getEditStockBtn().setOnAction(e -> {
                System.out.println("Navigasi ke halaman Edit Stock...");
            });
        }
        
        if (view.getEditProfileBtn() != null) {	
			view.getEditProfileBtn().setOnAction(e -> {
				navigateToProfile();
			});
		}
    }

    private void navigateToProfile() {
    	try {
            User currentUser = view.getUser(); 
            
            ProfilePage editPage = new ProfilePage(currentUser);
            new ProfileController(editPage, stage);
            
            stage.setScene(editPage.getScene());
            stage.setTitle("Edit Profile");
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

	private void navigateToLogin() {
        try {
            LoginPage loginPage = new LoginPage();
            new LoginController(loginPage, stage);
            
            stage.setScene(loginPage.getScene());
            stage.setTitle("Login Page");
            System.out.println("Berhasil Logout");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}