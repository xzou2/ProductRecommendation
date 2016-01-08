package entity;


public class Item {


	private String ID;
	
	private String name ; 
	
	private String salePrice ; 
	
	private Review review;

	public Item(String iD, String name, String salePrice) {
		super();
		ID = iD;
		this.name = name;
		this.salePrice = salePrice;
	}


	public void setID(String iD) {
		this.ID = iD;
	}

	public String getID() { 
		return ID;
	} 
	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSalePrice() {
		return salePrice;
	}


	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}


	public Review getReview() {
		return review;
	}


	public void setReview(Review review) {
		this.review = review;
	}

	
	
}
