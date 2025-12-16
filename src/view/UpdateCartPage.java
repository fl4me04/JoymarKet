package view;

import java.text.NumberFormat;
import java.util.Locale;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.CartItem;
import model.User;

public class UpdateCartPage {

    private Scene scene;
    private BorderPane mainLayout;
    private User currentUser;

    private TableView<CartItem> cartTable;
    private Spinner<Integer> quantitySpinner;
    private Button updateBtn;
    private Button deleteBtn;
    private Button checkoutBtn;
    private Button backBtn;
    private Label selectedItemLabel;
    private Label totalPriceLabel;

    public UpdateCartPage(User user) {
        this.currentUser = user;
        initialize();
    }

    @SuppressWarnings("unchecked")
	private void initialize() {
    	NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    	
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));

        Label titleLabel = new Label("My Shopping Cart");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        cartTable = new TableView<>();

        TableColumn<CartItem, String> nameCol = new TableColumn<>("Product");
        nameCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        nameCol.setMinWidth(150);

        TableColumn<CartItem, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(cellData -> 
            new SimpleObjectProperty<>(cellData.getValue().getProduct().getPrice()));
        
        priceCol.setCellFactory(column -> new TableCell<CartItem, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(item)); 
                }
            }
        });

        TableColumn<CartItem, Integer> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("count"));

        TableColumn<CartItem, Double> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(cellData -> 
            new SimpleObjectProperty<>(cellData.getValue().getTotalPrice()));
        
        totalCol.setCellFactory(column -> new TableCell<CartItem, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(item));
                }
            }
        });

        cartTable.getColumns().addAll(nameCol, priceCol, qtyCol, totalCol);

        VBox formContainer = new VBox(10);
        formContainer.setPadding(new Insets(20, 0, 0, 0));
        formContainer.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px 0 0 0;");

        Label formTitle = new Label("Edit Item");
        formTitle.setStyle("-fx-font-weight: bold;");

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        selectedItemLabel = new Label("Select an item to edit");
        selectedItemLabel.setStyle("-fx-font-style: italic;");

        Label qtyLabel = new Label("Quantity:");
        quantitySpinner = new Spinner<>(1, 100, 1);
        quantitySpinner.setEditable(true);

        updateBtn = new Button("Update Qty");
        deleteBtn = new Button("Remove Item");
        deleteBtn.setStyle("-fx-background-color: #ffcccc; -fx-text-fill: red;");
        
        totalPriceLabel = new Label("Grand Total: Rp 0");
        totalPriceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        checkoutBtn = new Button("Checkout");
        backBtn = new Button("Back to Menu");

        formGrid.add(new Label("Selected:"), 0, 0);
        formGrid.add(selectedItemLabel, 1, 0);
        formGrid.add(qtyLabel, 0, 1);
        formGrid.add(quantitySpinner, 1, 1);
        
        HBox topBar = new HBox(20, titleLabel);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(0,0,20,0));
        
        HBox actionBox = new HBox(10, updateBtn, deleteBtn);
        formGrid.add(actionBox, 1, 2);

        HBox bottomBar = new HBox(20, totalPriceLabel, checkoutBtn);
        bottomBar.setAlignment(Pos.CENTER_RIGHT);

        formContainer.getChildren().addAll(formTitle, formGrid, bottomBar, backBtn);

        mainLayout.setTop(topBar);
        mainLayout.setCenter(cartTable);
        mainLayout.setBottom(formContainer);

        scene = new Scene(mainLayout, 800, 600);
    }

	public Scene getScene() {
		return scene;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public TableView<CartItem> getCartTable() {
		return cartTable;
	}

	public Spinner<Integer> getQuantitySpinner() {
		return quantitySpinner;
	}

	public Button getUpdateBtn() {
		return updateBtn;
	}

	public Button getDeleteBtn() {
		return deleteBtn;
	}

	public Button getCheckoutBtn() {
		return checkoutBtn;
	}

	public Button getBackBtn() {
		return backBtn;
	}

	public Label getSelectedItemLabel() {
		return selectedItemLabel;
	}

	public Label getTotalPriceLabel() {
		return totalPriceLabel;
	}
}