package main.java.entity;


public class Item {


	private long ID;
	
	private String name ; 
	
	private double salePrice ; 
	
	private Review review;

	public Item(long iD, String name , double salePrice) {
		super();
		ID = iD;
		this.name = name;
		this.salePrice = salePrice;
	}



	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public long getID() {
		return ID;
	}



	public void setID(long iD) {
		ID = iD;
	}



	public double getSalePrice() {
		return salePrice;
	}



	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}



	public Review getReview() {
		return review;
	}


	public void setReview(Review review) {
		this.review = review;
	}

	
	
}
