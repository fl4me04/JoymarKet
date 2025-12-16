package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.CartItem;
import model.User;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableCell;

public class CheckoutPage {

    private Scene scene;
    private BorderPane mainLayout;
    private User currentUser;

    private TableView<CartItem> itemsTable;
    private TextField promoField;
    private Button applyPromoBtn;
    private Label subTotalLabel;
    private Label discountLabel;
    private Label grandTotalLabel;
    private Label balanceLabel;
    private Button placeOrderBtn;
    private Button backBtn;

    public CheckoutPage(User user) {
        this.currentUser = user;
        initialize();
    }

    @SuppressWarnings("unchecked")
	private void initialize() {
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));

        Label titleLabel = new Label("Checkout Confirmation");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        itemsTable = new TableView<>();
        
        TableColumn<CartItem, String> prodCol = new TableColumn<>("Product");
        prodCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getProduct().getName()));
        
        TableColumn<CartItem, Integer> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        TableColumn<CartItem, Double> priceCol = new TableColumn<>("Total");
        priceCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getTotalPrice()));
        
        // Change the format into Rupiah
        NumberFormat idrFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        priceCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(idrFormat.format(item));
            }
        });

        itemsTable.getColumns().addAll(prodCol, qtyCol, priceCol);
        itemsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox summaryBox = new VBox(15);
        summaryBox.setPrefWidth(300);
        summaryBox.setPadding(new Insets(20));
        summaryBox.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label summaryTitle = new Label("Order Summary");
        summaryTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        subTotalLabel = new Label("Subtotal: Rp 0");
        
        HBox promoBox = new HBox(5);
        promoField = new TextField();
        promoField.setPromptText("Promo Code");
        applyPromoBtn = new Button("Apply");
        promoBox.getChildren().addAll(promoField, applyPromoBtn);
        
        discountLabel = new Label("Discount: -Rp 0");
        discountLabel.setStyle("-fx-text-fill: green;");

        Separator sep = new Separator();

        grandTotalLabel = new Label("Total: Rp 0");
        grandTotalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");

        balanceLabel = new Label("Your Balance: Rp 0");
        balanceLabel.setStyle("-fx-text-fill: #6c757d;");

        summaryBox.getChildren().addAll(
            summaryTitle, subTotalLabel, 
            new Label("Have a promo?"), promoBox, discountLabel,
            sep, 
            grandTotalLabel, balanceLabel
        );

        HBox actionBox = new HBox(15);
        actionBox.setAlignment(Pos.CENTER_RIGHT);
        
        HBox contentContainer = new HBox(20);
        contentContainer.getChildren().addAll(itemsTable, summaryBox);
        
        HBox.setHgrow(itemsTable, Priority.ALWAYS);
        HBox.setHgrow(summaryBox, Priority.NEVER);

        summaryBox.setMaxHeight(Double.MAX_VALUE);
        
        titleLabel.setPadding(new Insets(0,0,20,0));
        
        backBtn = new Button("Back to Cart");
        backBtn.setStyle("-fx-padding: 8 10;");
        placeOrderBtn = new Button("Place Order");
        placeOrderBtn.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 10;");
        
        actionBox.getChildren().addAll(backBtn, placeOrderBtn);

        mainLayout.setTop(titleLabel);
        mainLayout.setCenter(contentContainer);
        mainLayout.setRight(null);
        mainLayout.setBottom(actionBox);
        
        BorderPane.setMargin(itemsTable, new Insets(20, 20, 20, 0));
        BorderPane.setMargin(actionBox, new Insets(20, 0, 0, 0));

        scene = new Scene(mainLayout, 900, 600);
    }

	public Scene getScene() {
		return scene;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public TableView<CartItem> getItemsTable() {
		return itemsTable;
	}

	public TextField getPromoField() {
		return promoField;
	}

	public Button getApplyPromoBtn() {
		return applyPromoBtn;
	}

	public Label getSubTotalLabel() {
		return subTotalLabel;
	}

	public Label getDiscountLabel() {
		return discountLabel;
	}

	public Label getGrandTotalLabel() {
		return grandTotalLabel;
	}

	public Label getBalanceLabel() {
		return balanceLabel;
	}

	public Button getPlaceOrderBtn() {
		return placeOrderBtn;
	}

	public Button getBackBtn() {
		return backBtn;
	}
}