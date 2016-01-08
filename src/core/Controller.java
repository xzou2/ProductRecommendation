package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Constants;

import core.interfaces.Action;
import core.interfaces.ItemSort;
import entity.Item;
import entity.Review;

public class Controller implements Action {

	private Mediator searchMediator; 
	
	private Mediator prodRecommendMediator;
	
	private Mediator reviewMediator ;
	
	private ItemSort itemSort ; 
	
	@Override
	public List<Item> getRecommendations(Map<String, String> inputs) {
		
		List<Object> searchFoundItems = searchMediator.sendRequest(inputs);
		if ( searchFoundItems == null) return null;
		if ( searchFoundItems.size() == 0) return new ArrayList<Item>(); 
		Item firstItem = (Item) searchFoundItems.get(0);
		
		Map<String, String> recommendReq = generateRecommendOrReviewRequest(firstItem);
		List<Object> recommendedItems = prodRecommendMediator.sendRequest(recommendReq);
		if ( recommendedItems == null) return null;
		List<Item>  items = new ArrayList<Item> (); 
		for (Object recommendedItem : recommendedItems) {
			Item item = (Item) recommendedItem; 
			Map<String, String> reviewReq = generateRecommendOrReviewRequest(item);
			List<Object> review = reviewMediator.sendRequest(reviewReq);
			if ( review == null || review.size() == 0 ) continue; 
			item.setReview((Review)review.get(0));
			items.add(item);
		}
		return itemSort.sortItemsByReview(items);
	}
	
	protected Map<String, String> generateRecommendOrReviewRequest( Item item ) {
		Map<String, String> request = new HashMap<String, String>();
		request.put(Constants.REQUEST_KEY, Constants.SERVICE_KEY);
		request.put(Constants.REQUEST_ITEM, item.getID());
		return request; 
	}

}
