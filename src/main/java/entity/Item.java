package main.java.entity;

/**
 * An entity class defines the product/item.
 * In the real application, a product/item has many attributes. 
 * In this case, we only define a few key attributes in a product/item for the sake of demonstration.
 * 
 * Created on : 07/01/2016
 * @author    : Xiaocheng Zou
 *
 */
public class Item {

	/**
	 * item's unique identity
	 */
	private long ID;
	
	/**
	 * item's name
	 */
	private String name; 
	
	/**
	 * item's sale price
	 */
	private double salePrice; 
	
	/**
	 * item's review
	 */
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
