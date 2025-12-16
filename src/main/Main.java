package main;

import controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginPage;


public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			LoginPage loginView = new LoginPage();
			new LoginController(loginView, primaryStage);
			
			primaryStage.setTitle("JoymarKet");
			primaryStage.setScene(loginView.getScene());
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}