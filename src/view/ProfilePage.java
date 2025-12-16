package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import model.User;

public class ProfilePage {

    private Scene scene;
    private BorderPane bp;
    private GridPane gp;
    
    private User currentUser;

    private Text title;
    private Label idLbl, nameLbl, emailLbl, passLbl, confPassLbl, phoneLbl, addressLbl, genderLbl;
    private TextField idTf, nameTf, emailTf, phoneTf;
    private PasswordField passTf, confPassTf;
    private TextArea addressTa;
    private RadioButton maleRb, femaleRb;
    private ToggleGroup genderGroup;
    
    private Button updateBtn, backBtn;

    public ProfilePage(User user) {
        this.currentUser = user;
        initialize();
        arrangePos();
        fillData();
    }

    private void initialize() {
        bp = new BorderPane();
        gp = new GridPane();
        title = new Text("Edit Profile");

        idLbl = new Label("Customer ID : ");
        nameLbl = new Label("Full Name : ");
        emailLbl = new Label("Email : ");
        passLbl = new Label("New Password : ");
        confPassLbl = new Label("Confirm Password : ");
        phoneLbl = new Label("Phone Number : ");
        addressLbl = new Label("Address : ");
        genderLbl = new Label("Gender : ");

        idTf = new TextField();
        idTf.setDisable(true);
        
        nameTf = new TextField();
        emailTf = new TextField();
        passTf = new PasswordField();
        confPassTf = new PasswordField();
        phoneTf = new TextField();
        
        addressTa = new TextArea();
        addressTa.setPrefRowCount(3);
        addressTa.setPrefWidth(200);

        maleRb = new RadioButton("Male");
        femaleRb = new RadioButton("Female");
        genderGroup = new ToggleGroup();
        maleRb.setToggleGroup(genderGroup);
        femaleRb.setToggleGroup(genderGroup);

        updateBtn = new Button("Update Profile");
        backBtn = new Button("Back to Menu");
        
        scene = new Scene(bp, 600, 600);
    }

    private void arrangePos() {
        title.setFont(Font.font("", FontWeight.BOLD, 24));
        updateBtn.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
        updateBtn.setMinWidth(150);
        
        backBtn.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-font-weight: bold;");

        gp.add(title, 0, 0, 2, 1);
        
        gp.add(idLbl, 0, 1);
        gp.add(idTf, 1, 1);

        gp.add(nameLbl, 0, 2);
        gp.add(nameTf, 1, 2);

        gp.add(emailLbl, 0, 3);
        gp.add(emailTf, 1, 3);

        gp.add(passLbl, 0, 4);
        gp.add(passTf, 1, 4);

        gp.add(confPassLbl, 0, 5);
        gp.add(confPassTf, 1, 5);

        gp.add(phoneLbl, 0, 6);
        gp.add(phoneTf, 1, 6);

        gp.add(addressLbl, 0, 7);
        gp.add(addressTa, 1, 7);

        gp.add(genderLbl, 0, 8);
        HBox genderBox = new HBox(10);
        genderBox.getChildren().addAll(maleRb, femaleRb);
        gp.add(genderBox, 1, 8);

        gp.add(updateBtn, 0, 9, 2, 1);
        gp.add(backBtn, 0, 10, 2, 1);

        // Alignment
        GridPane.setHalignment(title, HPos.CENTER);
        GridPane.setMargin(title, new Insets(0, 0, 20, 0));
        GridPane.setHalignment(updateBtn, HPos.CENTER);
        GridPane.setMargin(updateBtn, new Insets(20, 0, 10, 0));
        GridPane.setHalignment(backBtn, HPos.CENTER);

        gp.setAlignment(Pos.CENTER);
        gp.setHgap(15);
        gp.setVgap(15);
        gp.setPadding(new Insets(30));
        bp.setCenter(gp);
    }
    
    private void fillData() {
        idTf.setText(currentUser.getIdUser());
        nameTf.setText(currentUser.getFullName());
        emailTf.setText(currentUser.getEmail());
        phoneTf.setText(currentUser.getPhone());
        addressTa.setText(currentUser.getAddress());
        
        String gender = currentUser.getGender();
        if (gender != null) {
            if (gender.equalsIgnoreCase("Male")) maleRb.setSelected(true);
            else if (gender.equalsIgnoreCase("Female")) femaleRb.setSelected(true);
        }
    }

	public Scene getScene() {
		return scene;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public TextField getIdTf() {
		return idTf;
	}

	public TextField getNameTf() {
		return nameTf;
	}

	public TextField getEmailTf() {
		return emailTf;
	}

	public TextField getPhoneTf() {
		return phoneTf;
	}

	public PasswordField getPassTf() {
		return passTf;
	}

	public PasswordField getConfPassTf() {
		return confPassTf;
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

	public Button getUpdateBtn() {
		return updateBtn;
	}

	public Button getBackBtn() {
		return backBtn;
	}
}