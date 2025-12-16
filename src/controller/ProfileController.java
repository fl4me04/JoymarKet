package controller;

import database.ProfileHandler;
import javafx.stage.Stage;
import model.Admin;
import model.Courier;
import model.User;
import util.AlertHelper;
import view.MenuPage;
import view.ProfilePage;

public class ProfileController {

    private ProfilePage view;
    private Stage stage;
    private ProfileHandler handler;

    public ProfileController(ProfilePage view, Stage stage) {
        this.view = view;
        this.stage = stage;
        this.handler = new ProfileHandler();
        
        initializeTriggers();
    }

    private void initializeTriggers() {
        view.getSaveBtn().setOnAction(e -> handleSave());
        
        view.getBackBtn().setOnAction(e -> {
            try {
                MenuPage menu = new MenuPage(view.getCurrentUser());
                new MenuController(menu, stage);
                stage.setScene(menu.getScene());
            } catch (Exception ex) { ex.printStackTrace(); }
        });
    }

    private void handleSave() {
        User user = view.getCurrentUser();
        
        String name = view.getNameField().getText();
        String email = view.getEmailField().getText();
        String pass = view.getPassField().getText();
        String phone = view.getPhoneField().getText();
        String addr = view.getAddressArea().getText();
        String gender = view.getGenderBox().getValue();

        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            AlertHelper.showError("Error", "Name, Email, and Password are required.");
            return;
        }

        user.setFullName(name);
        user.setEmail(email);
        user.setPassword(pass);
        user.setPhone(phone);
        user.setAddress(addr);
        user.setGender(gender);

        // Specific Profile Page for Admin and Courier
        if (user instanceof Admin) {
            String emergency = view.getEmergencyField().getText();
            ((Admin) user).setEmergencyContact(emergency);
        } 
        else if (user instanceof Courier) {
            String vType = view.getVehicleTypeBox().getValue();
            String vPlate = view.getPlateField().getText();
            
            if (vType == null || vPlate.isEmpty()) {
                AlertHelper.showError("Error", "Vehicle details are required for Couriers.");
                return;
            }
            
            ((Courier) user).setVehicleType(vType);
            ((Courier) user).setVehiclePlate(vPlate);
        }

        boolean success = handler.updateProfile(user);

        if (success) {
            AlertHelper.showInfo("Success", "Profile updated successfully!");
        } else {
            AlertHelper.showError("Failed", "Could not update profile in database.");
        }
    }
}