package main.java.gui;

import java.util.List;

import main.java.entity.Item;
import main.java.entity.Review;

public class CLIPresentation implements Presentation {

	@Override
	public String presentItems(List<Item> items) {
		StringBuilder sb = new StringBuilder(); 
		for ( Item item : items ) {
			sb.append("Item Name: ") ; 
			sb.append(item.getName()); 
			sb.append("\n");
			
			sb.append("Sale Price: ");
			sb.append(item.getSalePrice());
			sb.append("\n");
			
			Review review = item.getReview();
			if (  review != null) {
				sb.append("Review: [ "); 
				sb.append(review.getAverageOverallRating());
				sb.append(" / "); 
				sb.append(review.getOverallRatingRange());
				sb.append(" ], ");
				sb.append(review.getTotalReviewCount());
				sb.append(" reviews ");
				sb.append("\n");
			}
			sb.append("\n");
			sb.append("==========================\n");
			sb.append("\n");
		}
		return sb.toString();
	}

}
