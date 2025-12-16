package model;

public class Delivery {

	private String idOrder;
	private String idCourier;
	private String status;
	
	public Delivery(String idOrder, String idCourier, String status) {
		super();
		this.idOrder = idOrder;
		this.idCourier = idCourier;
		this.status = status;
	}

	public String getIdOrder() {
		return idOrder;
	}

	public String getIdCourier() {
		return idCourier;
	}

	public String getStatus() {
		return status;
	}
}
