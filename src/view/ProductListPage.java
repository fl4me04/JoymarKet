package view;

import java.text.NumberFormat;
import java.util.Locale;

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
import model.Product;
import model.User;

public class ProductListPage {

    private Scene scene;
    private BorderPane mainLayout;
    
    private TableView<Product> productTable;
    private Spinner<Integer> quantitySpinner;
    private Button addToCartBtn;
    private Button backBtn;
    private Label selectedProductLabel;
    
    private User currentUser;

    public ProductListPage(User user) {
        this.currentUser = user;
        initialize();
    }

    @SuppressWarnings("unchecked")
	private void initialize() {
    	NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    	
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));

        Label titleLabel = new Label("Product Catalog");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        productTable = new TableView<>();
        
        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setMinWidth(200);

        TableColumn<Product, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Product, Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Product, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        priceCol.setCellFactory(column -> new TableCell<Product, Double>() {
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

        productTable.getColumns().addAll(nameCol, categoryCol, priceCol, stockCol);
        
        VBox formContainer = new VBox(10);
        formContainer.setPadding(new Insets(20, 0, 0, 0));
        formContainer.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px 0 0 0;");

        Label formTitle = new Label("Add to Cart");
        formTitle.setStyle("-fx-font-weight: bold;");

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        selectedProductLabel = new Label("No product selected");
        selectedProductLabel.setStyle("-fx-font-style: italic;");
        
        Label qtyLabel = new Label("Quantity:");
        quantitySpinner = new Spinner<>(1, 100, 1);
        quantitySpinner.setEditable(true);

        addToCartBtn = new Button("Add to Cart");
        backBtn = new Button("Back to Menu");

        formGrid.add(new Label("Selected:"), 0, 0);
        formGrid.add(selectedProductLabel, 1, 0);
        formGrid.add(qtyLabel, 0, 1);
        formGrid.add(quantitySpinner, 1, 1);
        formGrid.add(addToCartBtn, 1, 2);

        formContainer.getChildren().addAll(formTitle, formGrid, backBtn);

        HBox topBar = new HBox(20, titleLabel);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(0,0,20,0));

        mainLayout.setTop(topBar);
        mainLayout.setCenter(productTable);
        mainLayout.setBottom(formContainer);

        scene = new Scene(mainLayout, 800, 600);
    }

	public Scene getScene() {
		return scene;
	}

	public TableView<Product> getProductTable() {
		return productTable;
	}

	public Spinner<Integer> getQuantitySpinner() {
		return quantitySpinner;
	}

	public Button getAddToCartBtn() {
		return addToCartBtn;
	}

	public Button getBackBtn() {
		return backBtn;
	}

	public Label getSelectedProductLabel() {
		return selectedProductLabel;
	}

	public User getCurrentUser() {
		return currentUser;
	}
}