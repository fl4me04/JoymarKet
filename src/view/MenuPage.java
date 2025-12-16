package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Admin;
import model.Courier;
import model.Customer;
import model.User;

public class MenuPage {
	
	Scene scene;
	BorderPane bp;
	VBox menuContainer;
	Label welcomeLabel;
	Button logoutBtn, topupBtn, addToCartBtn, checkoutBtn, editProfileBtn, updateCartBtn, viewOrderBtn, viewAllOrdersBtn, viewAllCouriersBtn, editStockBtn, assignOrderBtn, updateStatusBtn;
	
	// Detecting which user is logged in
	private User user;
	
	public MenuPage(User user) {
		this.user = user;
		initialize();
		setupComponents();
	}

	private void initialize() {
		bp = new BorderPane();
        menuContainer = new VBox(20);
        scene = new Scene(bp, 600, 600);
	}

	private void setupComponents() {
        // Welcome Label
        welcomeLabel = new Label("Welcome, " + user.getFullName());
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
        logoutBtn.setMinWidth(100);

        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.getChildren().add(welcomeLabel);
        
        // Menu depends on the Role of User
        if (user instanceof Customer) {
            topupBtn = new Button("1. Top Up Balance");
            addToCartBtn = new Button("2. Add Product to Cart");
            updateCartBtn = new Button("3. Update Cart Items");
            checkoutBtn = new Button("4. Checkout Cart");
            viewOrderBtn = new Button("5. View Order History");
            editProfileBtn = new Button("6. Edit Profile");
            
            styleButton(topupBtn);
            styleButton(addToCartBtn);
            styleButton(updateCartBtn);
            styleButton(checkoutBtn);
            styleButton(viewOrderBtn);
            styleButton(editProfileBtn);

            menuContainer.getChildren().addAll(topupBtn, addToCartBtn, updateCartBtn, checkoutBtn, viewOrderBtn, editProfileBtn);
        } else if (user instanceof Admin) {
        	viewAllOrdersBtn = new Button("1. View All Orders");
        	viewAllCouriersBtn = new Button("2. View All Couriers");
            editStockBtn = new Button("3. Edit Product Stock");
            assignOrderBtn = new Button("4. Assign Order to Courier");
            editProfileBtn = new Button("5. Edit Profile");
            
            styleButton(viewAllOrdersBtn);
            styleButton(viewAllCouriersBtn);
            styleButton(editStockBtn);
            styleButton(assignOrderBtn);
            styleButton(editProfileBtn);
            
            menuContainer.getChildren().addAll(viewAllOrdersBtn, viewAllCouriersBtn, editStockBtn, assignOrderBtn, editProfileBtn);
        } else if (user instanceof Courier) {
             updateStatusBtn = new Button("1. Update Delivery Status");
             editProfileBtn = new Button("2. Edit Profile");
             styleButton(updateStatusBtn);
             styleButton(editProfileBtn);
             menuContainer.getChildren().addAll(updateStatusBtn, editProfileBtn);
         }

        menuContainer.getChildren().add(logoutBtn);
        
        bp.setCenter(menuContainer);
    }

    // Function for styling the button
    private void styleButton(Button btn) {
        btn.setMinWidth(250);
        btn.setMinHeight(30);
        btn.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
    }

    public Scene getScene() {
        return scene;
    }
    
    public Button getLogoutBtn() {
		return logoutBtn;
	}

	public Button getTopupBtn() {
		return topupBtn;
	}

	public Button getAddToCartBtn() {
		return addToCartBtn;
	}

	public Button getCheckoutBtn() {
		return checkoutBtn;
	}

	public Button getEditProfileBtn() {
		return editProfileBtn;
	}

	public Button getUpdateCartBtn() {
		return updateCartBtn;
	}

	public Button getEditStockBtn() {
		return editStockBtn;
	}

	public Button getAssignOrderBtn() {
		return assignOrderBtn;
	}

	public Button getUpdateStatusBtn() {
		return updateStatusBtn;
	}

	public Button getViewOrderBtn() {
		return viewOrderBtn;
	}

	public Button getViewAllOrdersBtn() {
		return viewAllOrdersBtn;
	}

	public Button getViewAllCouriersBtn() {
		return viewAllCouriersBtn;
	}

	public User getUser() { return user; }
}
