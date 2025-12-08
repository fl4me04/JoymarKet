package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LoginPage {

	Scene scene;
	BorderPane bp;
	GridPane gp;
	Label emailLabel, passwordLabel;
	TextField emailTf;
	PasswordField passwordTf;
	Button loginBtn;
	Text title;
	Hyperlink registerLink;
	
	public LoginPage() {
		initialize();
		arrangePos();
	}
	
	private void initialize() {
		bp = new BorderPane();
		gp = new GridPane();
		title = new Text("Login Page");
		emailTf = new TextField();
		passwordTf = new PasswordField();
		emailLabel = new Label("Email : ");
		passwordLabel = new Label("Password : ");
		loginBtn = new Button("Login");
		registerLink = new Hyperlink("Don't have an account? Register here");
		scene = new Scene(bp, 600, 600);
	}
	
	private void arrangePos() {
		// Set Title Style
		title.setFont(Font.font("", FontWeight.BOLD, 20));
		emailTf.setPromptText("Enter your email");
		passwordTf.setPromptText("Enter your password");
		
		// Set Button Style
		loginBtn.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
        loginBtn.setMinWidth(100);
        
        // Set Register Link Style
        registerLink.setStyle("-fx-border-color: transparent;");
        
        // Add Components to Grid
		gp.add(title, 0, 0, 2, 1);
		gp.add(emailLabel, 0, 1);
		gp.add(passwordLabel, 0, 2);
		gp.add(emailTf, 1, 1);
		gp.add(passwordTf, 1, 2);
		gp.add(loginBtn, 0, 3, 2, 1);
		gp.add(registerLink, 0, 4, 2, 1);
		
		// Making title and login in the center with margin
		GridPane.setHalignment(title, HPos.CENTER);
		GridPane.setMargin(title, new Insets(0, 0, 20, 0));
		GridPane.setHalignment(loginBtn, HPos.CENTER);
		GridPane.setMargin(loginBtn, new Insets(20, 0, 0, 0));
		GridPane.setHalignment(registerLink, HPos.CENTER);
		GridPane.setMargin(registerLink, new Insets(10, 0, 0, 0));
		
		// Grid Settings
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setPadding(new Insets(25, 25, 25, 25));
		bp.setCenter(gp);
	}
	
	public Scene getScene() {
		return scene;
	}

	public TextField getEmailTf() {
		return emailTf;
	}

	public PasswordField getPasswordTf() {
		return passwordTf;
	}

	public Button getLoginBtn() {
		return loginBtn;
	}

	public Hyperlink getRegisterLink() {
		return registerLink;
	}
}
