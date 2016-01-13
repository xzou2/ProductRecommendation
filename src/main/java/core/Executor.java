package main.java.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.common.Constants;
import main.java.core.interfaces.Action;
import main.java.core.interfaces.ItemSort;
import main.java.entity.Item;
import main.java.entity.Review;

/**
 * A class connects the user inputs and the back-end services, including 
 * search, product recommendation and review services
 * 
 * Created on : 07/01/2016
 * @author    : Xiaocheng Zou
 *
 */
public class Executor implements Action {
	
	private static final Logger log = Logger.getLogger(Executor.class.getName());

	/**
	 * The first 10 (as documented in the requirement) recommended items that needs the review retrieval 
	 */
	private final static int REVIEW_LIMIT = 10; 
	
	/**
	 * A search service mediator
	 */
	private Mediator searchMediator; 
	
	/**
	 * a product recommendation service mediator
	 */
	private Mediator prodRecommendMediator;
	
	/**
	 * a review service mediator
	 */
	private Mediator reviewMediator;
	
	/**
	 * item sorting service
	 */
	private ItemSort itemSort; 
	
	public Executor(Mediator searchMediator, Mediator prodRecommendMediator,
			Mediator reviewMediator, ItemSort itemSort) {
		super();
		this.searchMediator = searchMediator;
		this.prodRecommendMediator = prodRecommendMediator;
		this.reviewMediator = reviewMediator;
		this.itemSort = itemSort;
	}

	@Override
	public List<Item> getRecommendations(Map<String, String> inputs) {
		
		//do the search using users' input keywords
		List<Object> searchFoundItems = searchMediator.invokeService(inputs);
		if ( searchFoundItems == null) return null;
		if ( searchFoundItems.size() == 0) return new ArrayList<Item>();
		//we only need the first item as documented in the requirement
		Item firstItem = (Item) searchFoundItems.get(0);
		
		log.log(Level.INFO, "We find the first item [ " + firstItem.getName() + " ] match your keywords");
		log.log(Level.INFO, "We now find recommended items for this item");
		
		Map<String, String> recommendReq = generateRecommendOrReviewRequest(firstItem);
		// perform the product recommendation for the first item
		List<Object> recommendedItems = prodRecommendMediator.invokeService(recommendReq);
		if ( recommendedItems == null) return null;
		
		log.log(Level.INFO, "Found [ " + recommendedItems.size() + " ] recommended items.");
		
		List<Item>  items = new ArrayList<Item> ();
		//retrieve review for each item
		for ( int i = 0 ; i < Math.min(REVIEW_LIMIT, recommendedItems.size()); ++ i) {
			Item item = (Item) recommendedItems.get(i); 
			Map<String, String> reviewReq = generateRecommendOrReviewRequest(item);
			List<Object> review = reviewMediator.invokeService(reviewReq);
			if ( review == null || review.size() == 0 ) continue; 
			item.setReview((Review)review.get(0));
			items.add(item);
		}
		
		log.log(Level.INFO, "Retrieved [" + Math.min(REVIEW_LIMIT, recommendedItems.size()) + " ] reviews.");

		//sort returned items in the sorted order
		return itemSort.sortItemsByReview(items);
	}
	
	/**
	 * Generate additional parameters in the key-value pair for the product recommendation or review service
	 * @param item
	 * 		an item 
	 * @return
	 * 		parameters 
	 */
	protected Map<String, String> generateRecommendOrReviewRequest( Item item ) {
		Map<String, String> request = new HashMap<String, String>();
		request.put(Constants.REQUEST_API_KEY, Constants.SERVICE_KEY);
		request.put(Constants.REQUEST_ITEM, item.getID()+"");
		return request; 
	}

}
