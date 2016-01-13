package main.java.entity;


/**
 * An entity class defines the reviews of an item/product
 * Similar to the item entity, an item's review also has many attributes in real practice.  
 * We only define a few key attributes here for sorting and for display purpose.
 * 
 * Created on : 07/01/2016
 * @author    : Xiaocheng Zou
 *
 */
public class Review {

	/**
	 * review's overall rating
	 */
	private double averageOverallRating;

	/**
	 * total review counts
	 */
	private int totalReviewCount;

	/**
	 * review's rating scale
	 */
	private double overallRatingRange;

	public Review(double averageOverallRating, double overallRatingRange,
			int totalReviewCount) {
		this.totalReviewCount = totalReviewCount;
		this.overallRatingRange = overallRatingRange;
		this.averageOverallRating = averageOverallRating;
	}

	public int getTotalReviewCount() {
		return totalReviewCount;
	}

	public void setTotalReviewCount(int totalReviewCount) {
		this.totalReviewCount = totalReviewCount;
	}

	public double getAverageOverallRating() {
		return averageOverallRating;
	}

	public void setAverageOverallRating(double averageOverallRating) {
		this.averageOverallRating = averageOverallRating;
	}

	public double getOverallRatingRange() {
		return overallRatingRange;
	}

	public void setOverallRatingRange(double overallRatingRange) {
		this.overallRatingRange = overallRatingRange;
	}

	
}
