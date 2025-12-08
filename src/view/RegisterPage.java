package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class RegisterPage {

	Scene scene;
	BorderPane bp;
	GridPane gp;
	Label usernameLabel, emailLabel, passwordLabel, confirmLabel, phoneLabel, addressLabel, genderLabel;
	TextField usernameTf, emailTf, phoneTf;
	PasswordField passwordTf, confirmTf;
	Text title;
	TextArea addressTa;
	RadioButton maleRb, femaleRb;
	ToggleGroup genderGroup;
	Button regisBtn;
	Hyperlink loginLink;
	
	public RegisterPage() {
		initialize();
		arrangePos();
	}

	private void initialize() {
		bp = new BorderPane();
		gp = new GridPane();
		title = new Text("Register Page");
		usernameLabel = new Label("Full Name : ");
		emailLabel = new Label("Email : ");
		passwordLabel = new Label("Password : ");
		confirmLabel = new Label("Confirm Password : ");
		phoneLabel = new Label("Phone Number : ");
		addressLabel = new Label("Address : ");
		genderLabel = new Label("Gender : ");
		
		usernameTf = new TextField();
		emailTf = new TextField();
		phoneTf = new TextField();
		passwordTf = new PasswordField();
		confirmTf = new PasswordField();
		addressTa = new TextArea();
		addressTa.setPrefRowCount(3);
		addressTa.setPrefWidth(200);
		
		maleRb = new RadioButton("Male");
		femaleRb = new RadioButton("Female");
		genderGroup = new ToggleGroup();
		maleRb.setToggleGroup(genderGroup);
		femaleRb.setToggleGroup(genderGroup);
		
		regisBtn = new Button("Register");
		loginLink = new Hyperlink("Already have an account? Login here");
		
		scene = new Scene(bp, 600, 600);
	}

	private void arrangePos() {
		// Set Style
		title.setFont(Font.font("", FontWeight.BOLD, 20));
        regisBtn.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-font-weight: bold;");
        regisBtn.setMinWidth(120);
        loginLink.setStyle("-fx-border-color: transparent;");
        
        // Set Placeholder
        usernameTf.setPromptText("Enter your full name");
        emailTf.setPromptText("Enter your email. Ex: user@gmail.com");
        phoneTf.setPromptText("Enter your phone number [10-13 digits]");
        passwordTf.setPromptText("Enter your password [>= 6 characters]");
        confirmTf.setPromptText("Re-enter your password");
        addressTa.setPromptText("Enter your address. Ex: Jln. Charlie A No. 1");
        
        // Set Grid Position
        gp.add(title, 0, 0, 2, 1);
        gp.add(usernameLabel, 0, 1);
        gp.add(usernameTf, 1, 1);
        gp.add(emailLabel, 0, 2);
        gp.add(emailTf, 1, 2);
        gp.add(passwordLabel, 0, 3);
        gp.add(passwordTf, 1, 3);
        gp.add(confirmLabel, 0, 4);
        gp.add(confirmTf, 1, 4);
        gp.add(phoneLabel, 0, 5);
        gp.add(phoneTf, 1, 5);
        gp.add(addressLabel, 0, 6);
        gp.add(addressTa, 1, 6);
        
        gp.add(genderLabel, 0, 7);
        HBox genderBox = new HBox(10);
        genderBox.getChildren().addAll(maleRb, femaleRb);
        gp.add(genderBox, 1, 7);
        
        gp.add(regisBtn, 0, 8, 2, 1);
        gp.add(loginLink, 0, 9, 2, 1);

        // Set title, regis button, and login link into center
        GridPane.setHalignment(title, HPos.CENTER);
        GridPane.setMargin(title, new Insets(0, 0, 20, 0));
        GridPane.setHalignment(regisBtn, HPos.CENTER);
        GridPane.setMargin(regisBtn, new Insets(20, 0, 0, 0));
        GridPane.setHalignment(loginLink, HPos.CENTER);

        // Grid Settings
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(15);
        gp.setVgap(15);
        gp.setPadding(new Insets(30));
        
        bp.setCenter(gp);
	}
	
	public Scene getScene() {
		return scene;
	}

	public TextField getUsernameTf() {
		return usernameTf;
	}

	public TextField getEmailTf() {
		return emailTf;
	}

	public TextField getPhoneTf() {
		return phoneTf;
	}

	public PasswordField getPasswordTf() {
		return passwordTf;
	}

	public PasswordField getConfirmTf() {
		return confirmTf;
	}

	public TextArea getAddressTa() {
		return addressTa;
	}

	public RadioButton getMaleRb() {
		return maleRb;
	}

	public RadioButton getFemaleRb() {
		return femaleRb;
	}

	public Button getRegisBtn() {
		return regisBtn;
	}

	public Hyperlink getLoginLink() {
		return loginLink;
	}
}
