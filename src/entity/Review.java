package entity;

public class Review {

	private float averageOverallRating;

	private int totalReviewCount;

	private float overallRatingRange;

	public Review(float averageOverallRating, float overallRatingRange,
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

	public float getOverallRatingRange() {
		return overallRatingRange;
	}

	public void setOverallRatingRange(float overallRatingRange) {
		this.overallRatingRange = overallRatingRange;
	}

	public float getAverageOverallRating() {
		return averageOverallRating;
	}

	public void setAverageOverallRating(float averageOverallRating) {
		this.averageOverallRating = averageOverallRating;
	}

}
