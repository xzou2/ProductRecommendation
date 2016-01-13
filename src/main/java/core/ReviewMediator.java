package main.java.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.common.Constants;
import main.java.core.interfaces.RemoteService;
import main.java.entity.Review;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * A review service mediator
 * Created on : 07/01/2016
 * @author    : Xiaocheng Zou
 *
 */
public class ReviewMediator extends Mediator {

	public ReviewMediator(RemoteService service) {
		super(service);
	}

	private static final Logger log = Logger.getLogger(Review.class.getName());
	
	public static final String REVIEW_SERVICE= "reviews";

	
	@Override
	protected Logger getLogger() {
		return log;
	}

	@Override
	protected List<Object> parseResponse(String response) {
		List<Object> review = new ArrayList<Object>();
		try {
			Object obj = jsonParser.parse(response);
			JSONObject JSONObj = (JSONObject) obj; 
			long itemId = (long) JSONObj.get("itemId");
			if ( itemId == 0) return review;
			JSONObject  reviewStat = (JSONObject) JSONObj.get("reviewStatistics");
			Review r = new Review ( 
					Double.parseDouble((String) reviewStat.get("averageOverallRating")),
					Double.parseDouble((String) reviewStat.get("overallRatingRange")),
					Integer.parseInt((String) reviewStat.get("totalReviewCount"))
					); 
			review.add(r);
		} catch (ParseException e) {
			log.log(Level.WARNING,
					"Encounter exceptions when parse the response from review service at position [" + e.getPosition() + "]");
			return null; 
		}
		return review;
	}

	/* 
	 * prepare the review URL in the following format:
	 * http://api.walmartlabs.com/v1/reviews/{itemId}?apiKey={apiKey}
	 */
	@Override
	protected String prepareURL(Map<String, String> inputs) {
		StringBuilder sb = new StringBuilder(Constants.SERVICE_BASE);
		sb.append(REVIEW_SERVICE); 
		sb.append("/"); 
		sb.append(inputs.get(Constants.REQUEST_ITEM));
		sb.append(assembleReqOptions(inputs));
		return sb.toString();
	}

}
