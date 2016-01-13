package main.java.core.interfaces;

import java.util.List;
import java.util.Map;

import main.java.entity.Item;


/**
 * We abstract the main service of this application as the item recommendation service
 * 
 * Created on : 07/01/2016
 * @author    : Xiaocheng Zou
 *
 */
public interface Action {

	/**
	 * find a list of recommended items (up to 10 items) of the first item matches user's search keyword
	 * @param inputs
	 * 			user's inputs (search keywords) for invoking walmart's services 
	 * @return
	 * 			a list of recommended items
	 */
	public List<Item> getRecommendations(Map<String, String> inputs);
}
