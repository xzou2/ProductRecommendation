package main.java.core.interfaces;

import java.util.List;

import main.java.entity.Item;


/**
 * An interface defines the item sorting
 * 
 * Created on : 07/01/2016
 * @author    : Xiaocheng Zou
 *
 */
public interface ItemSort {

	/**
	 * Given by a list of items, return a list of sorted items 
	 * @param items
	 * 		a list of items
	 * @return
	 * 		a list of sorted items according to sentiments of item's reviews 
	 */
	public List<Item> sortItemsByReview(List<Item> items);
}
