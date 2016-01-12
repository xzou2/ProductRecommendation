package main.java.entity;

public class Review {

	private double averageOverallRating;

	private int totalReviewCount;

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
