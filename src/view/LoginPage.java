package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class LoginPage {

	Scene scene;
	BorderPane bp;
	GridPane gp;
	Label emailLabel, passwordLabel;
	TextField emailTf;
	PasswordField passwordTf;
	Button loginBtn;
	
	public LoginPage() {
		initialize();
	}
	
	private void initialize() {
		bp = new BorderPane();
		gp = new GridPane();
		emailTf = new TextField();
		passwordTf = new PasswordField();
		emailLabel = new Label("Email : ");
		passwordLabel = new Label("Password : ");
		loginBtn = new Button("Login");
		scene = new Scene(bp, 300, 300);
	}
	
}
