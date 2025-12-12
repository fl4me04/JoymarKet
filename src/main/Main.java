package main;

import java.util.ArrayList;

import controller.CartController;
import controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Cart;
import model.Customer;
import model.Product;
import view.AddToCartPage;
import view.LoginPage;
import view.MenuPage;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
//		LoginPage loginPage = new LoginPage();
//        new LoginController(loginPage, primaryStage);
//        primaryStage.setTitle("JoymarKet");
//        primaryStage.setScene(loginPage.getScene());
//        primaryStage.show();
		
//		Customer dummyUser = new Customer(
//	            "C001",
//	            "Test User",
//	            "test@mail.com",
//	            "123",
//	            "08123",
//	            "Jakarta",
//	            "Male",
//	            0 // balance
//	    );
//
//	    // --- Langsung arahkan ke MenuPage ---
//	    MenuPage menu = new MenuPage(dummyUser);
//
//	    primaryStage.setTitle("JoymarKet");
//	    primaryStage.setScene(menu.getScene());
//	    primaryStage.show();
		
		 ArrayList<Product> productList = new ArrayList<>();
	        productList.add(new Product("P001", "Apple", 5000));
	        productList.add(new Product("P002", "Banana", 3000));
	        productList.add(new Product("P003", "Milk", 12000));

	        // 2️⃣ Buat AddToCartPage
	        AddToCartPage addPage = new AddToCartPage(productList);

	        // 3️⃣ Buat Cart
	        Cart cart = new Cart();

	        // 4️⃣ Pasang Controller
	        new CartController(cart, addPage, primaryStage);

	        // 5️⃣ Set scene langsung ke AddToCartPage
	        primaryStage.setTitle("JoymarKet - Add to Cart Test");
	        primaryStage.setScene(addPage.getScene());
	        primaryStage.show();
	   }
}

