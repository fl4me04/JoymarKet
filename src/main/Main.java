package main;

import java.util.ArrayList;

import controller.CartController;
import controller.LoginController;
import controller.UpdateCartController; // <-- DITAMBAHKAN
import javafx.application.Application;
import javafx.stage.Stage;
import model.Cart;
import model.Customer;
import model.Product;
import view.AddToCartPage;
import view.LoginPage;
import view.MenuPage;
import view.UpdateCartPage; // <-- DITAMBAHKAN

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	
	public void start(Stage primaryStage) throws Exception {

	    // --- 1. Buat daftar produk dummy ---
	    ArrayList<Product> productList = new ArrayList<>();
	    productList.add(new Product("P001", "Apple", 5000));
	    productList.add(new Product("P002", "Banana", 3000));
	    productList.add(new Product("P003", "Milk", 12000));

	    // --- 2. Buat cart dummy ---
	    Cart userCart = new Cart();
	    userCart.addToCart(productList.get(0), 5); // 5 Apple
	    userCart.addToCart(productList.get(1), 8); // 8 Banana
	    userCart.addToCart(productList.get(2), 2); // 2 Milk

	    // --- 3. Buat UpdateCartPage ---
	    UpdateCartPage updateView = new UpdateCartPage(userCart);
	    updateView.setMenuScene(null); // tidak ada MenuPage untuk test ini

	    // --- 4. Buat controller ---
	    new UpdateCartController(userCart, updateView, primaryStage);

	    // --- 5. Tampilkan scene ---
	    primaryStage.setTitle("JoymarKet - Update Cart Test");
	    primaryStage.setScene(updateView.getScene());
	    primaryStage.show();

	    System.out.println("Displaying Update Cart Page directly with dummy data.");
	}
	
//	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
//		LoginPage loginPage = new LoginPage();
//        new LoginController(loginPage, primaryStage);
//        primaryStage.setTitle("JoymarKet");
//        primaryStage.setScene(loginPage.getScene());
//        primaryStage.show();
		
//		Customer dummyUser = new Customer(
//	            "C001",
//	            "Test User",
//	            "test@mail.com",
//	            "123",
//	            "08123",
//	            "Jakarta",
//	            "Male",
//	            0 // balance
//	    );
//
//	    // --- Langsung arahkan ke MenuPage ---
//	    MenuPage menu = new MenuPage(dummyUser);
//
//	    primaryStage.setTitle("JoymarKet");
//	    primaryStage.setScene(menu.getScene());
//	    primaryStage.show();
		
//		 ArrayList<Product> productList = new ArrayList<>();
//	        productList.add(new Product("P001", "Apple", 5000));
//	        productList.add(new Product("P002", "Banana", 3000));
//	        productList.add(new Product("P003", "Milk", 12000));
//	        
//	        AddToCartPage addPage = new AddToCartPage(productList);
//	        
//	        Cart cart = new Cart();
//
//	        new CartController(cart, addPage, primaryStage);
//
//	        primaryStage.setTitle("JoymarKet - Add to Cart Test");
//	        primaryStage.setScene(addPage.getScene());
//	        primaryStage.show();
        
		
        
		// Product List Dummy (untuk membuat item cart)
//		ArrayList<Product> productList = new ArrayList<>();
//	    productList.add(new Product("P001", "Apple", 5000));
//	    productList.add(new Product("P002", "Banana", 3000));
//	    productList.add(new Product("P003", "Milk", 12000));
//		
//		// Cart Dummy (Diisi dengan beberapa item untuk pengujian update/remove)
//		Cart userCart = new Cart();
//		userCart.addToCart(productList.get(0), 5); // 5 Apple
//		userCart.addToCart(productList.get(1), 8); // 8 Banana
//		userCart.addToCart(productList.get(2), 2); // 2 Milk
//	    
//		// 2. SETUP UPDATE CART PAGE (LANGSUNG)
//		
//        UpdateCartPage updateView = new UpdateCartPage(userCart);
//
//        // Set Scene Menu ke null karena kita tidak memiliki MenuPage, 
//        // ini mencegah tombol 'Back' (atau Save Changes/Remove yang pindah scene) crash.
//        updateView.setMenuScene(null); 
//        
//     // 3. SETUP CONTROLLER
//        UpdateCartController updateController = new UpdateCartController(userCart, updateView, primaryStage); 
            
        // 4. Tampilkan Scene Update Cart
//        primaryStage.setTitle("JoymarKet - Update Cart Test");
//	    primaryStage.setScene(updateView.getScene());
//	    primaryStage.show();
//	    
//	    System.out.println("Displaying Update Cart Page directly with dummy data.");
//	   }
}
