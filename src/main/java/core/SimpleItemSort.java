package main.java.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import main.java.core.interfaces.ItemSort;
import main.java.entity.Item;
import main.java.entity.Review;

public class SimpleItemSort implements ItemSort {

	@Override
	public List<Item> sortItemsByReview(List<Item> items) {
		Collections.sort(items, new Comparator<Item>() {

			@Override
			public int compare(Item o1, Item o2) {
				Review r1 = o1.getReview(), r2 = o2.getReview(); 
				if ( null == r1 && r1 == r2) return 0; 
				if ( null == r1 ) return 1; 
				if ( null == r2 ) return -1; 
				if ( r1.getAverageOverallRating() != r2.getAverageOverallRating()) {
					return r1.getAverageOverallRating() - r2.getAverageOverallRating() >0 ? -1 : 1;
				}
				if (r1.getTotalReviewCount() != r2.getTotalReviewCount()) {
					return -1 * (r1.getTotalReviewCount() - r2.getTotalReviewCount());
				}
				return 0;
			}
			
		});
		return items;
	}

}
