package view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Product;
import model.User;
import java.text.NumberFormat;
import java.util.Locale;

public class EditStockPage {

    private Scene scene;
    private BorderPane mainLayout;
    private User currentUser;

    // Components
    private TableView<Product> table;
    private Button addBtn, editBtn, deleteBtn, backBtn;

    public EditStockPage(User user) {
        this.currentUser = user;
        initialize();
    }

    @SuppressWarnings("unchecked")
	private void initialize() {
    	// Initialzie UI
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));

        Label title = new Label("Product Management");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        table = new TableView<>();
        
        // Making table for product
        TableColumn<Product, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getIdProduct()));
        
        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getName()));
        
        TableColumn<Product, String> priceCol = new TableColumn<>("Price");
        NumberFormat idr = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        priceCol.setCellValueFactory(d -> new SimpleStringProperty(idr.format(d.getValue().getPrice())));
        
        TableColumn<Product, Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getStock()));
        stockCol.setStyle("-fx-font-weight: bold;");
        
        TableColumn<Product, String> catCol = new TableColumn<>("Category");
        catCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCategory()));

        table.getColumns().addAll(idCol, nameCol, priceCol, stockCol, catCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        backBtn = new Button("Back to Menu");
        
        addBtn = new Button("Add Product");
        editBtn = new Button("Edit Details");
        deleteBtn = new Button("Delete Product");
        deleteBtn.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");

        HBox actionBox = new HBox(10, backBtn, new Region(), addBtn, editBtn, deleteBtn);
        HBox.setHgrow(actionBox.getChildren().get(1), Priority.ALWAYS); // Spacer
        actionBox.setPadding(new Insets(20, 0, 0, 0));

        mainLayout.setTop(title);
        mainLayout.setCenter(table);
        mainLayout.setBottom(actionBox);
        
        BorderPane.setMargin(title, new Insets(0, 0, 20, 0));
        BorderPane.setAlignment(title, Pos.CENTER);

        scene = new Scene(mainLayout, 800, 600);
    }

	public Scene getScene() {
		return scene;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public TableView<Product> getTable() {
		return table;
	}

	public Button getAddBtn() {
		return addBtn;
	}

	public Button getEditBtn() {
		return editBtn;
	}

	public Button getDeleteBtn() {
		return deleteBtn;
	}

	public Button getBackBtn() {
		return backBtn;
	}
}