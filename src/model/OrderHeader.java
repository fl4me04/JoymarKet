package model;

import java.util.Date;

public class OrderHeader {

	private String idOrder;
	private String idCustomer;
	private String idPromo;
	private String status;
	private Date orderedAt;
	private double totalAmount;
	
	public OrderHeader(String idOrder, String idCustomer, String idPromo, String status, Date orderedAt,
			double totalAmount) {
		super();
		this.idOrder = idOrder;
		this.idCustomer = idCustomer;
		this.idPromo = idPromo;
		this.status = status;
		this.orderedAt = orderedAt;
		this.totalAmount = totalAmount;
	}

	public String getIdOrder() {
		return idOrder;
	}

	public String getIdCustomer() {
		return idCustomer;
	}

	public String getIdPromo() {
		return idPromo;
	}

	public String getStatus() {
		return status;
	}

	public Date getOrderedAt() {
		return orderedAt;
	}

	public double getTotalAmount() {
		return totalAmount;
	}
}
