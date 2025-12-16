package model;

public class CartItem {
    private String idCustomer;
    private String idProduct;
    private int count;
    
    private Product product;

	public CartItem(String idCustomer, String idProduct, int count) {
		super();
		this.idCustomer = idCustomer;
		this.idProduct = idProduct;
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getIdCustomer() {
		return idCustomer;
	}

	public String getIdProduct() {
		return idProduct;
	}

	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public double getTotalPrice() {
        if (product != null) {
            return product.getPrice() * count;
        }
        return 0.0;
    }
}
