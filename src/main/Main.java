package main;

import java.util.ArrayList;

import controller.UpdateCartController; 
import database.ProductManager;
import database.TransactionManager;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Cart;
import model.Customer;
import model.Product;
import view.UpdateCartPage; 

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

        ProductManager pm = new ProductManager();
        ArrayList<Product> productList = pm.getAllProducts();
        
        if (productList.isEmpty()) {
            System.err.println("WARNING: Product list is empty. Check your database Products table.");
        }
        
        // --- 1. Customer Dummy (User ID 2) ---
        Customer dummyUser = new Customer("2", "Test User 2", "test2@mail.com", "456", "0812345", "Bandung", "Female", 0);
        
        TransactionManager tm = new TransactionManager();
        
        // --- 2. MUAT DRAFT DARI DATABASE UNTUK USER ID 2 ---
        Cart userCart = tm.loadDraftCart(dummyUser.getId());
        
        // Logika pengujian awal: Jika Draft kosong, buat draft dummy dan simpan ke DB.
        if (userCart.getItems().isEmpty() && productList.size() >= 4) {
             System.out.println("DEBUG: DB Draft for User ID 2 is empty. Creating initial dummy draft...");
             userCart.addToCart(productList.get(0), 10); 
             userCart.addToCart(productList.get(1), 5); 
             tm.updateDraftTransaction(userCart, dummyUser.getId());
             userCart = tm.loadDraftCart(dummyUser.getId());
        }
        
        // --- 3. Instansiasi UpdateCartPage (View) ---
        // PENTING: Mengirim Cart yang sudah dimuat dan SELURUH productList
        UpdateCartPage updateView = new UpdateCartPage(userCart, productList); 
        updateView.setMenuScene(null); 
        
        // --- 4. Instansiasi UpdateCartController ---
        new UpdateCartController(userCart, updateView, primaryStage, dummyUser); 

        // --- 5. Tampilkan Scene Update Cart ---
        primaryStage.setTitle("JoymarKet - Update Cart Test (User ID 2, Gabungan)");
        primaryStage.setScene(updateView.getScene());
        primaryStage.show();
	    
	    System.out.println("Displaying Update Cart Page. User ID: 2. Items loaded: " + userCart.getItems().size());
	}
}