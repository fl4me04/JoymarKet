package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

import java.util.ArrayList;

public class UpdateCartPage {

    private Scene scene;
    private BorderPane bp;
    private VBox mainLayout;
    private GridPane itemGrid;
    private Text title;
    private Button backBtn;
    private Button saveChangesBtn;

    private Cart cart;
    private ArrayList<Spinner<Integer>> qtyInputs = new ArrayList<>();
    private ArrayList<Button> deleteBtns = new ArrayList<>();

    public UpdateCartPage(Cart cart) {
        this.cart = cart;
        initialize();
        arrange();
    }

    private void initialize() {
        bp = new BorderPane();
        mainLayout = new VBox(20);
        itemGrid = new GridPane();
        title = new Text("Update Cart Items");

        backBtn = new Button("Back to Menu");
        backBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        backBtn.setMinWidth(120);

        saveChangesBtn = new Button("Save Changes");
        saveChangesBtn.setStyle("-fx-background-color: darkgreen; -fx-text-fill: white; -fx-font-weight: bold;");
        saveChangesBtn.setMinWidth(150);

        scene = new Scene(bp, 700, 600);
    }

    private void arrange() {
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Setup Header Grid (Item Name, Price, Quantity, Action)
        itemGrid.setHgap(30);
        itemGrid.setVgap(15);
        itemGrid.setPadding(new Insets(20));
        itemGrid.setAlignment(Pos.TOP_CENTER);

        // Headers
        itemGrid.add(new Label("Product Name"), 0, 0);
        itemGrid.add(new Label("Price"), 1, 0);
        itemGrid.add(new Label("Quantity"), 2, 0);
        itemGrid.add(new Label("Action"), 3, 0);
        
        // Style Headers
        for (int i = 0; i < 4; i++) {
            ((Label) itemGrid.getChildren().get(i)).setFont(Font.font("Arial", FontWeight.BOLD, 14));
        }

        // --- Isi Item Keranjang ---
        int row = 1;
        for (CartItem item : cart.getItems()) {
            Label nameLbl = new Label(item.getProduct().getName());
            Label priceLbl = new Label("Rp " + item.getTotalPrice());

            // Spinner untuk mengubah kuantitas
            Spinner<Integer> qty = new Spinner<>();
            qty.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, item.getQuantity()));
            qty.setPrefWidth(70);
            qtyInputs.add(qty);

            Button deleteBtn = new Button("Remove");
            deleteBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");
            deleteBtns.add(deleteBtn);

            itemGrid.add(nameLbl, 0, row);
            itemGrid.add(priceLbl, 1, row);
            itemGrid.add(qty, 2, row);
            itemGrid.add(deleteBtn, 3, row);

            row++;
        }
        
        if (cart.getItems().isEmpty()) {
            Label emptyLbl = new Label("Your cart is empty.");
            itemGrid.add(emptyLbl, 0, 1, 4, 1);
        }

        mainLayout.getChildren().addAll(title, itemGrid);
        mainLayout.setAlignment(Pos.CENTER);

        HBox bottomHBox = new HBox(20);
        bottomHBox.setAlignment(Pos.CENTER);
        // Tampilkan tombol Save Changes hanya jika keranjang tidak kosong
        if (!cart.getItems().isEmpty()) {
            bottomHBox.getChildren().addAll(backBtn, saveChangesBtn);
        } else {
            bottomHBox.getChildren().add(backBtn);
        }

        bp.setCenter(mainLayout);
        bp.setBottom(bottomHBox);
        BorderPane.setMargin(bottomHBox, new Insets(15));
    }

    public void refreshItems() {
        // Method untuk me-refresh tampilan jika terjadi perubahan di keranjang
        // Perlu implementasi yang lebih kompleks (seperti membersihkan VBox dan menggambar ulang)
        // Untuk saat ini, kita akan mengandalkan pembaruan scene.
    }

    public Scene getScene() { return scene; }
    public Button getBackBtn() { return backBtn; }
    public Button getSaveChangesBtn() { return saveChangesBtn; }
    public ArrayList<Spinner<Integer>> getQtyInputs() { return qtyInputs; }
    public ArrayList<Button> getDeleteBtns() { return deleteBtns; }
    public Cart getCart() { return cart; }

    // Properti ini diperlukan di Controller utama
    private Scene menuScene;
    public void setMenuScene(Scene menuScene) { this.menuScene = menuScene; }
    public Scene getMenuScene() { return menuScene; }
}