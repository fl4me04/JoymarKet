package controller;

import database.CourierHandler;
import javafx.stage.Stage;
import model.Courier;
import model.User;
import util.AlertHelper;
import view.CourierFormPage;
import view.CourierListPage;

public class CourierFormController {

    private CourierFormPage view;
    private Stage stage;
    private CourierHandler handler;

    public CourierFormController(CourierFormPage view, Stage stage) {
        this.view = view;
        this.stage = stage;
        this.handler = new CourierHandler();

        if (view.getCourierToEdit() == null) {
            view.getIdField().setText(handler.generateNewId());
        }

        initializeTriggers();
    }

    private void initializeTriggers() {
        view.getSaveBtn().setOnAction(e -> handleSave());
        
        view.getBackBtn().setOnAction(e -> navigateBack());
    }

    // Saving Courier
    private void handleSave() {
        String id = view.getIdField().getText();
        String name = view.getNameField().getText();
        String email = view.getEmailField().getText();
        String pass = view.getPassField().getText();
        String phone = view.getPhoneField().getText();
        String addr = view.getAddressArea().getText();
        String gender = view.getGenderBox().getValue();
        String vehicle = view.getVehicleTypeBox().getValue();
        String plate = view.getPlateField().getText();

        if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || gender == null) {
            AlertHelper.showError("Error", "Please fill all required fields.");
            return;
        }
        
        if (vehicle == null || plate.isEmpty()) {
            AlertHelper.showError("Error", "Please fill vehicle details.");
            return;
        }

        Courier c = new Courier(id, name, email, pass, phone, addr, gender, vehicle, plate);

        boolean success;
        if (view.getCourierToEdit() == null) {
            success = handler.createCourier(c);
        } else {
            success = handler.updateCourier(c);
        }

        if (success) {
            AlertHelper.showInfo("Success", "Courier data saved successfully!");
            navigateBack();
        } else {
            AlertHelper.showError("Failed", "Database error.");
        }
    }

    private void navigateBack() {
        CourierListPage listPage = new CourierListPage(view.getCurrentUser());
        new CourierListController(listPage, stage);
        stage.setScene(listPage.getScene());
    }
}