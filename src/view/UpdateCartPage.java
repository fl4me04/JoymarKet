package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Cart;
import model.CartItem;
import model.Product;

import java.util.ArrayList;

public class UpdateCartPage {

    private Scene scene;
    private BorderPane bp;
    private VBox mainLayout;
    private GridPane productGrid;
    private GridPane itemGrid;
    private Text title;
    private Button backBtn;
    private Button saveChangesBtn;
    private ScrollPane scrollPane;

    private Cart cart;
    private ArrayList<Product> productList;
    private ArrayList<Spinner<Integer>> addQtyInputs = new ArrayList<>();
    private ArrayList<Button> addBtns = new ArrayList<>();
    private ArrayList<Spinner<Integer>> updateQtyInputs = new ArrayList<>();
    private ArrayList<Button> deleteBtns = new ArrayList<>();

    public UpdateCartPage(Cart cart, ArrayList<Product> productList) {
        this.cart = cart;
        this.productList = productList;
        initialize();
        arrange();
    }

    private void initialize() {
        bp = new BorderPane();
        mainLayout = new VBox(20);
        
        productGrid = new GridPane(); // Grid untuk produk yang bisa ditambahkan
        itemGrid = new GridPane(); // Grid untuk item yang sudah di keranjang
        
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        
        title = new Text("Update Cart Items and Add Products");

        backBtn = new Button("Back to Menu");
        backBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        backBtn.setMinWidth(120);

        saveChangesBtn = new Button("Save Changes");
        saveChangesBtn.setStyle("-fx-background-color: darkgreen; -fx-text-fill: white; -fx-font-weight: bold;");
        saveChangesBtn.setMinWidth(150);

        scene = new Scene(bp, 900, 700);
    }

    private void arrange() {
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        // --- Bagian 1: Daftar Produk yang Tersedia untuk Ditambah (Product Grid) ---
        productGrid.setHgap(30);
        productGrid.setVgap(15);
        productGrid.setPadding(new Insets(10));
        
        productGrid.add(new Label("Available Products"), 0, 0, 4, 1);
        ((Label) productGrid.getChildren().get(0)).setFont(Font.font("Arial", FontWeight.BOLD, 16));

        productGrid.add(new Label("Name"), 0, 1);
        productGrid.add(new Label("Price"), 1, 1);
        productGrid.add(new Label("Quantity"), 2, 1);
        productGrid.add(new Label("Action"), 3, 1);

        int productRow = 2;
        for (Product product : productList) {
            productGrid.add(new Label(product.getName()), 0, productRow);
            productGrid.add(new Label("Rp " + product.getPrice()), 1, productRow);

            Spinner<Integer> qty = new Spinner<>();
            qty.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
            qty.setPrefWidth(70);
            addQtyInputs.add(qty);

            Button addBtn = new Button("Add");
            addBtn.setStyle("-fx-background-color: navy; -fx-text-fill: white;");
            addBtns.add(addBtn);

            productGrid.add(qty, 2, productRow);
            productGrid.add(addBtn, 3, productRow);
            productRow++;
        }
        
        // --- Bagian 2: Daftar Item yang Sudah Ada di Keranjang (Item Grid) ---
        itemGrid.setHgap(30);
        itemGrid.setVgap(15);
        itemGrid.setPadding(new Insets(20));
        itemGrid.setAlignment(Pos.TOP_CENTER);
        
        itemGrid.add(new Label("Current Cart Items"), 0, 0, 4, 1);
        ((Label) itemGrid.getChildren().get(0)).setFont(Font.font("Arial", FontWeight.BOLD, 16));

        itemGrid.add(new Label("Product Name"), 0, 1);
        itemGrid.add(new Label("Price"), 1, 1);
        itemGrid.add(new Label("Quantity"), 2, 1);
        itemGrid.add(new Label("Action"), 3, 1);
        
        int cartRow = 2;
        for (CartItem item : cart.getItems()) {
            Label nameLbl = new Label(item.getProduct().getName());
            Label priceLbl = new Label("Rp " + item.getTotalPrice());

            Spinner<Integer> qty = new Spinner<>();
            qty.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, item.getQuantity()));
            qty.setPrefWidth(70);
            updateQtyInputs.add(qty);

            Button deleteBtn = new Button("Remove");
            deleteBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");
            deleteBtns.add(deleteBtn);

            itemGrid.add(nameLbl, 0, cartRow);
            itemGrid.add(priceLbl, 1, cartRow);
            itemGrid.add(qty, 2, cartRow);
            itemGrid.add(deleteBtn, 3, cartRow);

            cartRow++;
        }
        
        if (cart.getItems().isEmpty()) {
            Label emptyLbl = new Label("Your cart is empty.");
            itemGrid.add(emptyLbl, 0, cartRow, 4, 1);
        }
        
        VBox cartVBox = new VBox(15, itemGrid);
        
        mainLayout.getChildren().addAll(title, productGrid, new Separator(), cartVBox); 
        mainLayout.setAlignment(Pos.CENTER);
        
        scrollPane.setContent(mainLayout);

        HBox bottomHBox = new HBox(20);
        bottomHBox.setAlignment(Pos.CENTER);
        bottomHBox.getChildren().addAll(backBtn, saveChangesBtn);

        bp.setCenter(scrollPane);
        bp.setBottom(bottomHBox);
        BorderPane.setMargin(bottomHBox, new Insets(15));
    }

   
    public void refreshItems() {
        
        itemGrid.getChildren().clear(); 
        updateQtyInputs.clear();
        deleteBtns.clear();
        arrange(); 
    }

    public Scene getScene() { return scene; }
    public Button getBackBtn() { return backBtn; }
    public Button getSaveChangesBtn() { return saveChangesBtn; }
    
    
    public ArrayList<Product> getProducts() { return productList; }
    public ArrayList<Spinner<Integer>> getAddQtyInputs() { return addQtyInputs; }
    public ArrayList<Button> getAddBtns() { return addBtns; }
    
    
    public ArrayList<Spinner<Integer>> getUpdateQtyInputs() { return updateQtyInputs; }
    public ArrayList<Button> getDeleteBtns() { return deleteBtns; }
    public Cart getCart() { return cart; }

    private Scene menuScene;
    public void setMenuScene(Scene menuScene) { this.menuScene = menuScene; }
    public Scene getMenuScene() { return menuScene; }
}