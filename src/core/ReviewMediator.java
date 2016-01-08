package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import common.Constants;

import entity.Review;

public class ReviewMediator extends Mediator {

	private static final Logger log = Logger.getLogger(Review.class.getName());
	
	private static final String REVIEW_SERVICE= "review";

	
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
			JSONObject  reviewStat = (JSONObject) JSONObj.get("reviewStatistics");
			Review r = new Review ( 
					Float.parseFloat((String) reviewStat.get("averageOverallRating")),
					Float.parseFloat((String) reviewStat.get("overallRatingRange")),
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

	@Override
	protected String assembleURL(Map<String, String> inputs) {
		StringBuilder sb = new StringBuilder(Constants.SERVICE_BASE);
		sb.append(REVIEW_SERVICE); 
		sb.append("/"); 
		sb.append(inputs.get(Constants.REQUEST_ITEM));
		sb.append(assembleReqOptions(inputs));
		return sb.toString();
	}

}
