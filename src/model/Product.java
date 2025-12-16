package model;

public class Product {
    private String id;
    private String name;
    private double price;
    private int stock;
    private String category;
    
	public Product(String id, String name, double price, int stock, String category) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.category = category;
	}

	public String getIdProduct() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public int getStock() {
		return stock;
	}

	public String getCategory() {
		return category;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}
