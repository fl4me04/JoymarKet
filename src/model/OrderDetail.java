package model;

public class OrderDetail {

	private String idOrder;
	private String idProduct;
	private int qty;
	
	private Product product;

	public OrderDetail(String idOrder, String idProduct, int qty) {
		super();
		this.idOrder = idOrder;
		this.idProduct = idProduct;
		this.qty = qty;
	}

	public String getIdOrder() {
		return idOrder;
	}

	public String getIdProduct() {
		return idProduct;
	}

	public int getQty() {
		return qty;
	}

	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
}
