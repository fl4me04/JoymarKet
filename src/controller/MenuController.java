package controller;

import javafx.stage.Stage;
import model.User;
import view.AllOrdersPage;
import view.AssignCourierPage;
import view.AssignedDeliveriesPage;
import view.CheckoutPage;
import view.CourierListPage;
import view.EditStockPage;
import view.LoginPage;
import view.MenuPage;
import view.OrderHistoryPage;
import view.ProductListPage;
import view.ProfilePage;
import view.TopUpPage;
import view.UpdateCartPage;

public class MenuController {
    
    private MenuPage view;
    private Stage stage;

    public MenuController(MenuPage view, Stage stage) {
        this.view = view;
        this.stage = stage;
        initializeTriggers();
    }

    private void initializeTriggers() {
        view.getLogoutBtn().setOnAction(e -> {
            navigateToLogin();
        });

        if (view.getTopupBtn() != null) {
            view.getTopupBtn().setOnAction(e -> {
                navigateToTopUp();
            });
        }
        
        if (view.getAddToCartBtn() != null) {
        	view.getAddToCartBtn().setOnAction(e -> {
        		navigateToAddToCart();
        	});
        }
        
        if (view.getUpdateCartBtn() != null) {
        	view.getUpdateCartBtn().setOnAction(e -> {
        		navigateToUpdateCart();
        	});
        }
        
        if (view.getCheckoutBtn() != null) {
			view.getCheckoutBtn().setOnAction(e -> {
				navigateToCheckout();
			});
		}
        
        if (view.getViewOrderBtn() != null) {
			view.getViewOrderBtn().setOnAction(e -> {
				navigateToOrderHistory();
			});
		}
        
        if (view.getViewAllOrdersBtn() != null) {
			view.getViewAllOrdersBtn().setOnAction(e -> {
				navigateToViewOrders();
			});
		}
        
        if (view.getViewAllCouriersBtn() != null) {
			view.getViewAllCouriersBtn().setOnAction(e -> {
				navigateToViewCouriers();
			});
		}
        
        if (view.getEditStockBtn() != null) {
            view.getEditStockBtn().setOnAction(e -> {
                navigateToEditStock();
            });
        }
        
        if (view.getAssignOrderBtn() != null) {
			view.getAssignOrderBtn().setOnAction(e -> {
				navigateToAssignOrder();
			});
		}
        
        if (view.getUpdateStatusBtn() != null) {
			view.getUpdateStatusBtn().setOnAction(e -> {
				navigateToUpdateStatus();
			});
		}
        
        if (view.getEditProfileBtn() != null) {	
			view.getEditProfileBtn().setOnAction(e -> {
				navigateToProfile();
			});
		}
    }

    private void navigateToViewCouriers() {
		try {
			User currentUser = view.getUser();
			
			CourierListPage page = new CourierListPage(currentUser);
			new CourierListController(page, stage);
			stage.setScene(page.getScene());
			stage.setTitle("Admin - View All Couriers");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void navigateToViewOrders() {
		try {
			User currentUser = view.getUser();
			
			AllOrdersPage page = new AllOrdersPage(currentUser);
	        new AllOrdersController(page, stage);
	        stage.setScene(page.getScene());
	        stage.setTitle("Admin - View All Orders");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void navigateToUpdateStatus() {
		try {
			User currentUser = view.getUser();
			
			AssignedDeliveriesPage page = new AssignedDeliveriesPage(currentUser);
	        new AssignedDeliveriesController(page, stage);
	        stage.setScene(page.getScene());
	        stage.setTitle("Courier - My Deliveries");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void navigateToAssignOrder() {
		try {
			User currentUser = view.getUser();
			
			AssignCourierPage page = new AssignCourierPage(currentUser);
	        new AssignCourierController(page, stage);
	        stage.setScene(page.getScene());
	        stage.setTitle("Admin - Assign Courier");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void navigateToEditStock() {
		try {
			User currentUser = view.getUser();
			
			EditStockPage page = new EditStockPage(currentUser);
	        new EditStockController(page, stage);
	        stage.setScene(page.getScene());
	        stage.setTitle("Admin - Manage Stock");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void navigateToTopUp() {
		try {
			User currentUser = view.getUser();
			
			TopUpPage page = new TopUpPage(currentUser);
	        new TopUpController(page, stage);
	        stage.setScene(page.getScene());
	        stage.setTitle("Top Up Balance");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void navigateToOrderHistory() {
		try {
			User currentUser = view.getUser();
			
			OrderHistoryPage historyPage = new OrderHistoryPage(currentUser);
			new OrderHistoryController(historyPage, stage);
			stage.setScene(historyPage.getScene());
			stage.setTitle("Order History");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void navigateToCheckout() {
		try {
			User currentUser = view.getUser();
			
			CheckoutPage checkoutPage = new CheckoutPage(currentUser);
		    new CheckoutController(checkoutPage, stage);
		    stage.setScene(checkoutPage.getScene());
		    stage.setTitle("Checkout");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void navigateToUpdateCart() {
		try {
			User currentUser = view.getUser();
			
	        UpdateCartPage page = new UpdateCartPage(currentUser);
	        new UpdateCartController(page, stage);
	        stage.setScene(page.getScene());
	        stage.setTitle("My Cart");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void navigateToAddToCart() {
		try {
			User currentUser = view.getUser();
			
			ProductListPage page = new ProductListPage(currentUser);
    	    new ProductListController(page, stage);
    	    
    	    stage.setScene(page.getScene());
    	    stage.setTitle("Add To Cart");
		} catch (Exception e) {
			e.printStackTrace();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}