package main.java.gui;

import java.util.List;

import main.java.entity.Item;

/**
 * An interface defines how the returned items to be presented.
 * In case we want to show the return items in a different way, such as web, 
 * we only need to a different class to implement this interface. 
 * Created on : 07/01/2016
 * @author Xiaocheng Zou 
 *
 */
public interface Presentation {

	/**
	 * Presents a given item list 
	 * @param items 
	 * 		a list of items
	 * @return
	 * 		a string represents the presentation of the given item list 
	 */
	public String presentItems (List<Item> items);
}
