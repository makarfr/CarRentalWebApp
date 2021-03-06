package common;

public enum Actions {
	LOGIN_VIEW("login", ""), 
	CLIENTS_VIEW("clientView", "admin"), 
	CARS_VIEW("carView", ""), ADD_CAR_VIEW("addCarView", "admin"),
	CONTRACTS_VIEW("orderView", "dealer"),
	DEALER_VIEW("dealerView", "admin"),
	ORDER_POST("orderPost", "client"),
	REGISTER("addClientView", "");
	
	private String path;
	private String view;
	
	Actions(String view, String path) {
		this.path = path;
		this.view = view;
		
	}
	public String getFullUrl() {
		return String.format("/%s/%s%s", path, view, "?faces-redirect=true");
	}
	
	public String getViewUrl() {
		return String.format("%s%s", view, "?faces-redirect=true");
	}
}
