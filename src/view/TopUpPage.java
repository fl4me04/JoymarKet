package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.Customer;
import model.User;

import java.text.NumberFormat;
import java.util.Locale;

public class TopUpPage {

    private Scene scene;
    private BorderPane mainLayout;
    private User currentUser;

    private Label currentBalanceLabel;
    private Spinner<Double> amountSpinner;
    private Button topUpBtn;
    private Button backBtn;

    public TopUpPage(User user) {
        this.currentUser = user;
        initialize();
    }

    private void initialize() {
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));

        Label titleLabel = new Label("Top Up Balance");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        VBox formBox = new VBox(15);
        formBox.setAlignment(Pos.CENTER);
        formBox.setMaxWidth(400);
        formBox.setStyle("-fx-border-color: transparent; -fx-padding: 30;");

        currentBalanceLabel = new Label("Current Balance: Rp 0");
        currentBalanceLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #555;");

        Label amountLabel = new Label("Top Up Amount:");
        amountSpinner = new Spinner<>(0.0, 100000000.0, 50000.0, 10000.0); 
        amountSpinner.setEditable(true);
        amountSpinner.setPrefWidth(300);

        topUpBtn = new Button("Top Up Now");
        topUpBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-weight: bold; -fx-pref-width: 300;");

        formBox.getChildren().addAll(currentBalanceLabel, amountLabel, amountSpinner, topUpBtn);

        backBtn = new Button("Back to Menu");

        VBox contentContainer = new VBox(10);
        contentContainer.setAlignment(Pos.CENTER);
        
        contentContainer.getChildren().addAll(titleLabel, formBox);
        
        mainLayout.setTop(null);
        mainLayout.setCenter(contentContainer);
        mainLayout.setBottom(backBtn);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        BorderPane.setMargin(backBtn, new Insets(20, 0, 0, 0));

        scene = new Scene(mainLayout, 600, 600);
        
        updateBalanceLabel();
    }

    // Helper to update balance
    public void updateBalanceLabel() {
        if (currentUser instanceof Customer) {
            double bal = ((Customer) currentUser).getBalance();
            NumberFormat idr = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            currentBalanceLabel.setText("Current Balance: " + idr.format(bal));
        }
    }

	public Scene getScene() {
		return scene;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public Spinner<Double> getAmountSpinner() {
		return amountSpinner;
	}

	public Button getTopUpBtn() {
		return topUpBtn;
	}

	public Button getBackBtn() {
		return backBtn;
	}
}