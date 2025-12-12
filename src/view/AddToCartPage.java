package view;

import java.util.ArrayList;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox; // Import HBox untuk menampung tombol
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Product;

public class AddToCartPage {

    private Scene scene;
    private BorderPane bp;
    private GridPane gp;

    private Text title;
    private Button backBtn;
    private Button submitBtn; // <-- Tambahan properti baru

    private ArrayList<Product> products;
    private ArrayList<Button> addBtns = new ArrayList<>();
    private ArrayList<Spinner<Integer>> qtyInputs = new ArrayList<>();

    private Scene menuScene; // untuk tombol Back

    public AddToCartPage(ArrayList<Product> products) {
        this.products = products;
        initialize();
        arrange();
    }

    private void initialize() {
        bp = new BorderPane();
        gp = new GridPane();
        title = new Text("Add Product to Cart");

        backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        backBtn.setMinWidth(100);
        
        // Inisialisasi tombol Submit Order
        submitBtn = new Button("Submit Order"); // <-- Inisialisasi tombol baru
        submitBtn.setStyle("-fx-background-color: darkblue; -fx-text-fill: white; -fx-font-weight: bold;");
        submitBtn.setMinWidth(150);

        scene = new Scene(bp, 700, 600);
    }

    private void arrange() {
        // --- Bagian Atas/Tengah (Grid Produk) ---
        title.setFont(Font.font("", FontWeight.BOLD, 24));
        GridPane.setHalignment(title, HPos.CENTER);
        GridPane.setMargin(title, new Insets(0,0,20,0));

        gp.add(title, 0, 0, 4, 1);

        int row = 1;
        for (Product p : products) {
            Label nameLbl = new Label(p.getName());
            Label priceLbl = new Label("Rp " + p.getPrice());

            Spinner<Integer> qty = new Spinner<>();
            qty.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
            qty.setPrefWidth(70);

            Button addBtn = new Button("Add");
            addBtn.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
            addBtn.setPrefWidth(100);

            qtyInputs.add(qty);
            addBtns.add(addBtn);

            gp.add(nameLbl, 0, row);
            gp.add(priceLbl, 1, row);
            gp.add(qty, 2, row);
            gp.add(addBtn, 3, row);

            row++;
        }

        gp.setAlignment(Pos.CENTER);
        gp.setHgap(20);
        gp.setVgap(15);
        gp.setPadding(new Insets(20));

        bp.setCenter(gp);

      
        
        HBox bottomHBox = new HBox(20);
        bottomHBox.setAlignment(Pos.CENTER);
        bottomHBox.getChildren().addAll(backBtn, submitBtn); 
        
        bp.setBottom(bottomHBox); 
        BorderPane.setMargin(bottomHBox, new Insets(15));
    }

   
    public Scene getScene() { return scene; }
    public ArrayList<Button> getAddBtns() { return addBtns; }
    public ArrayList<Spinner<Integer>> getQtyInputs() { return qtyInputs; }
    public ArrayList<Product> getProducts() { return products; }

    public Button getBackBtn() { return backBtn; }
    public Button getSubmitBtn() { return submitBtn; } 

    public void setMenuScene(Scene menuScene) { this.menuScene = menuScene; }
    public Scene getMenuScene() { return menuScene; }
}