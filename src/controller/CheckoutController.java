package controller;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import database.CartItemHandler;
import database.OrderHandler;
import database.PromoHandler;
import javafx.stage.Stage;
import model.CartItem;
import model.Customer;
import model.Promo;
import model.User;
import util.AlertHelper;
import view.CheckoutPage;
import view.MenuPage;
import view.UpdateCartPage;

public class CheckoutController {

    private CheckoutPage view;
    private Stage stage;
    
    // Handlers
    private CartItemHandler cartHandler;
    private PromoHandler promoHandler;
    private OrderHandler orderHandler;

    private ArrayList<CartItem> cartItems;
    private Promo activePromo = null;
    private double subtotal = 0;
    private double discountAmount = 0;
    private double grandTotal = 0;

    // Change the format into Rupiah
    private NumberFormat idr = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public CheckoutController(CheckoutPage view, Stage stage) {
        this.view = view;
        this.stage = stage;
        
        this.cartHandler = new CartItemHandler();
        this.promoHandler = new PromoHandler();
        this.orderHandler = new OrderHandler();
        
        loadData();
        initializeTriggers();
    }

    private void loadData() {
        User user = view.getCurrentUser();
        
        cartItems = cartHandler.getCartItems(user.getIdUser());
        view.getItemsTable().getItems().setAll(cartItems);
        calculateTotals();
        
        if (user instanceof Customer) {
            Customer c = (Customer) user;
            view.getBalanceLabel().setText("Your Balance: " + idr.format(c.getBalance()));
        }
    }

    private void calculateTotals() {
        subtotal = 0;
        for (CartItem item : cartItems) {
            subtotal += item.getTotalPrice();
        }

        discountAmount = 0;
        if (activePromo != null) {
            discountAmount = subtotal * activePromo.getDiscountPercentage();
        }

        grandTotal = subtotal - discountAmount;
        if (grandTotal < 0) grandTotal = 0;

        view.getSubTotalLabel().setText("Subtotal: " + idr.format(subtotal));
        view.getDiscountLabel().setText("Discount: -" + idr.format(discountAmount));
        view.getGrandTotalLabel().setText("Total: " + idr.format(grandTotal));
    }

    private void initializeTriggers() {
        view.getApplyPromoBtn().setOnAction(e -> {
            String code = view.getPromoField().getText().trim();
            
            if (code.isEmpty()) {
                activePromo = null;
                calculateTotals();
                AlertHelper.showError("Promo", "Please enter a code.");
                return;
            }

            Promo promo = promoHandler.getPromo(code);

            if (promo != null) {
                activePromo = promo;
                calculateTotals();
                AlertHelper.showInfo("Promo Applied", 
                    "Code: " + promo.getCode() + "\n" + promo.getHeadline());
            } else {
                activePromo = null;
                calculateTotals();
                AlertHelper.showError("Invalid Code", "Promo code not found!");
            }
        });

        view.getPlaceOrderBtn().setOnAction(e -> {
            handleCheckout();
        });

        view.getBackBtn().setOnAction(e -> {
            navigateBackToCart();
        });
    }

    private void handleCheckout() {
        User user = view.getCurrentUser();

        if (cartItems.isEmpty()) {
            AlertHelper.showError("Empty Cart", "You have no items to checkout.");
            return;
        }

        String result = orderHandler.checkout(user, cartItems, activePromo, grandTotal);

        if (result.equals("SUCCESS")) {
            AlertHelper.showInfo("Success", "Order placed successfully!");
            navigateToMenu();
        } else {
            AlertHelper.showError("Transaction Failed", result);
        }
    }

    private void navigateBackToCart() {
        try {
            UpdateCartPage cartPage = new UpdateCartPage(view.getCurrentUser());
            new UpdateCartController(cartPage, stage);
            stage.setScene(cartPage.getScene());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateToMenu() {
        try {
            MenuPage menuPage = new MenuPage(view.getCurrentUser());
            new MenuController(menuPage, stage);
            stage.setScene(menuPage.getScene());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}