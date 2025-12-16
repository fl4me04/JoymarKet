package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Product;
import model.User;

public class ProductFormPage {

    private Scene scene;
    private BorderPane mainLayout;
    private User currentUser;

    private TextField idField;
    private TextField nameField;
    private TextField priceField;
    private Spinner<Integer> stockSpinner;
    private ComboBox<String> categoryBox;
    private Button saveBtn;
    private Button cancelBtn;

    private Product productToEdit;

    public ProductFormPage(User user, Product productToEdit) {
        this.currentUser = user;
        this.productToEdit = productToEdit;
        initialize();
    }

    private void initialize() {
    	// Initialize UI
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));

        Label title = new Label(productToEdit == null ? "Add New Product" : "Edit Product");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);

        idField = new TextField();
        idField.setDisable(true); 
        
        nameField = new TextField();
        nameField.setPromptText("Product Name");
        
        priceField = new TextField();
        priceField.setPromptText("Price (Numbers only)");
        
        stockSpinner = new Spinner<>(0, 10000, 0);
        stockSpinner.setEditable(true);

        categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Foods", "Beverages", "Snacks", "Household", "Electronics", "Health", "Stationary");
        categoryBox.setPromptText("Select Category");

        grid.addRow(0, new Label("Product ID:"), idField);
        grid.addRow(1, new Label("Name:"), nameField);
        grid.addRow(2, new Label("Price:"), priceField);
        grid.addRow(3, new Label("Stock:"), stockSpinner);
        grid.addRow(4, new Label("Category:"), categoryBox);

        saveBtn = new Button("Save Product");
        saveBtn.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");
        cancelBtn = new Button("Cancel");

        HBox btnBox = new HBox(10, cancelBtn, saveBtn);
        btnBox.setAlignment(Pos.CENTER_RIGHT);

        VBox centerBox = new VBox(20, title, grid, btnBox);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setMaxWidth(400);

        mainLayout.setCenter(centerBox);
        scene = new Scene(mainLayout, 600, 600);

        // Set text edited product
        if (productToEdit != null) {
            idField.setText(productToEdit.getIdProduct());
            nameField.setText(productToEdit.getName());
            priceField.setText(String.valueOf((int)productToEdit.getPrice()));
            stockSpinner.getValueFactory().setValue(productToEdit.getStock());
            categoryBox.setValue(productToEdit.getCategory());
        }
    }

	public Scene getScene() {
		return scene;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public TextField getIdField() {
		return idField;
	}

	public TextField getNameField() {
		return nameField;
	}

	public TextField getPriceField() {
		return priceField;
	}

	public Spinner<Integer> getStockSpinner() {
		return stockSpinner;
	}

	public ComboBox<String> getCategoryBox() {
		return categoryBox;
	}

	public Button getSaveBtn() {
		return saveBtn;
	}

	public Button getCancelBtn() {
		return cancelBtn;
	}

	public Product getProductToEdit() {
		return productToEdit;
	}
}