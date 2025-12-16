package controller;

import database.CustomerHandler;
import javafx.stage.Stage;
import model.Customer;
import model.User;
import util.AlertHelper;
import view.MenuPage;
import view.TopUpPage;

public class TopUpController {

    private TopUpPage view;
    private Stage stage;
    private CustomerHandler customerHandler;

    public TopUpController(TopUpPage view, Stage stage) {
        this.view = view;
        this.stage = stage;
        this.customerHandler = new CustomerHandler();
        
        initializeTriggers();
    }

    private void initializeTriggers() {
        view.getTopUpBtn().setOnAction(e -> {
            handleTopUp();
        });

        view.getBackBtn().setOnAction(e -> {
            navigateToMenu();
        });
    }

    private void handleTopUp() {
        Double amount = view.getAmountSpinner().getValue();
        User user = view.getCurrentUser();

        if (amount == null || amount <= 0) {
            AlertHelper.showError("Invalid Amount", "Please enter a valid amount greater than 0.");
            return;
        }

        if (!(user instanceof Customer)) {
            AlertHelper.showError("Error", "Only customers can top up.");
            return;
        }

        boolean success = customerHandler.topUpBalance(user.getIdUser(), amount);

        if (success) {
            Customer c = (Customer) user;
            c.setBalance(c.getBalance() + amount);
            
            view.updateBalanceLabel();
            
            AlertHelper.showInfo("Top-Up Successful", "Your balance has been updated!");
        } else {
            AlertHelper.showError("Failed", "Top Up failed. Please try again.");
        }
    }

    private void navigateToMenu() {
        try {
            MenuPage menu = new MenuPage(view.getCurrentUser());
            new MenuController(menu, stage);
            stage.setScene(menu.getScene());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}