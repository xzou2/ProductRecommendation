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

public class Controller implements Action {
	
	private static final Logger log = Logger.getLogger(Controller.class.getName());

	private final static int REVIEW_LIMIT = 10; 
	
	private Mediator searchMediator; 
	
	private Mediator prodRecommendMediator;
	
	private Mediator reviewMediator ;
	
	private ItemSort itemSort ; 
	
	public Controller(Mediator searchMediator, Mediator prodRecommendMediator,
			Mediator reviewMediator, ItemSort itemSort) {
		super();
		this.searchMediator = searchMediator;
		this.prodRecommendMediator = prodRecommendMediator;
		this.reviewMediator = reviewMediator;
		this.itemSort = itemSort;
	}

	@Override
	public List<Item> getRecommendations(Map<String, String> inputs) {
		
		
		List<Object> searchFoundItems = searchMediator.sendRequest(inputs);
		if ( searchFoundItems == null) return null;
		if ( searchFoundItems.size() == 0) return new ArrayList<Item>(); 
		Item firstItem = (Item) searchFoundItems.get(0);
		
		log.log(Level.INFO, "Finish the search, we are about to find the recommended items for item [ " + firstItem.getName() + " ].");
		
		Map<String, String> recommendReq = generateRecommendOrReviewRequest(firstItem);
		List<Object> recommendedItems = prodRecommendMediator.sendRequest(recommendReq);
		if ( recommendedItems == null) return null;
		
		log.log(Level.INFO, "Found [ " + recommendedItems.size() + " ] recommended items.");
		
		List<Item>  items = new ArrayList<Item> ();
		for ( int i = 0 ; i < Math.min(REVIEW_LIMIT, recommendedItems.size()); ++ i) {
			Item item = (Item) recommendedItems.get(i); 
			Map<String, String> reviewReq = generateRecommendOrReviewRequest(item);
			List<Object> review = reviewMediator.sendRequest(reviewReq);
			if ( review == null || review.size() == 0 ) continue; 
			item.setReview((Review)review.get(0));
			items.add(item);
		}
		
		log.log(Level.INFO, "Retrieved [" + Math.min(REVIEW_LIMIT, recommendedItems.size()) + " ] reviews.");
		
		return itemSort.sortItemsByReview(items);
	}
	
	protected Map<String, String> generateRecommendOrReviewRequest( Item item ) {
		Map<String, String> request = new HashMap<String, String>();
		request.put(Constants.REQUEST_API_KEY, Constants.SERVICE_KEY);
		request.put(Constants.REQUEST_ITEM, item.getID()+"");
		return request; 
	}

}
