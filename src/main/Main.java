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
		// TODO Auto-generated method stub
		LoginPage loginPage = new LoginPage();
        new LoginController(loginPage, primaryStage);
        primaryStage.setTitle("JoymarKet");
        primaryStage.setScene(loginPage.getScene());
        primaryStage.show();
	}

}