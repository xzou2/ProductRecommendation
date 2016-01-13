package main.java.gui;

import java.util.List;

import main.java.entity.Item;
import main.java.entity.Review;

/**
 * A specific class (command-line interactive presentation) implements the presentation interface
 *   
 * Created on : 07/01/2016
 * @author Xiaocheng Zou 
 *
 */
public class CLIPresentation implements Presentation {
	
	/**
	 * Separation line
	 */
	private static final String SEP_LINE = "==========================\n";
	
	@Override
	public String presentItems(List<Item> items) {
		StringBuilder sb = new StringBuilder();
		sb.append(SEP_LINE);
		for ( Item item : items ) {
			// display an item's name in a format, such as, "Item Name: xxx"
			sb.append("Item Name: ") ;    
			sb.append(item.getName()); 
			sb.append("\n");
			
			// display an item's sale price in a format, such as, "Sale Prices: xxx"
			sb.append("Sale Price: ");  
			sb.append(item.getSalePrice());
			sb.append("\n");
			
			Review review = item.getReview();
			if (  review != null) {    
				// display an item's review if applicable, the format is like "Review: [3.5/5.0] 500 reviews"
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
			sb.append(SEP_LINE);
			sb.append("\n");
		}
		return sb.toString();
	}

}
