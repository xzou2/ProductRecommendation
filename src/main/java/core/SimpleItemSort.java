package main.java.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import main.java.core.interfaces.ItemSort;
import main.java.entity.Item;
import main.java.entity.Review;


/**
 * A sorter sorts items according to the items' reviews
 * An item with high average overall rating is put before another item with low average overall rating
 * If two items with same average overall rating, items with more review counts are before items with few review counts
 * In case items with null review, we put them at the end of the sorting list
 * Created on : 07/01/2016
 * @author    : Xiaocheng Zou
 *
 */
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
