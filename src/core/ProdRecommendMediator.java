package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import common.Constants;

public class ProdRecommendMediator extends Mediator {
	private static final Logger log = Logger.getLogger(ProdRecommendMediator.class.getName());
	
	private static final String RECOMMEND_SERVICE = "nbp";
	
	@Override
	protected Logger getLogger() {
		return log;
	}

	@Override
	protected List<Object> parseResponse(String response) {
		List<Object> items  = new ArrayList<Object>();
		try {
			Object obj = jsonParser.parse(response);
			items = parseItem((JSONArray)obj);
		} catch (ParseException e) {
			log.log(Level.WARNING,
					"Encounter exceptions when parse the response from product recommendation API service at position [" + e.getPosition() + "]");
			return null; 
		}
		
		return items;
	}

	@Override
	protected String assembleURL(Map<String, String> inputs) {
		StringBuilder sb = new StringBuilder(Constants.SERVICE_BASE);
		sb.append(RECOMMEND_SERVICE); 
		sb.append(assembleReqOptions(inputs));
		return sb.toString();
	}

}
