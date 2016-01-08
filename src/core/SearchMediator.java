package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import common.Constants;

public class SearchMediator extends Mediator {
	private static final Logger logger = Logger.getLogger(SearchMediator.class.getName());
	
	private static final String SEARCH_SERVICE= "search";
	
	@Override
	protected List<Object> parseResponse(String response) {
		List<Object> items  = new ArrayList<Object>();
		try {
			Object obj = jsonParser.parse(response);
			JSONObject JSONObj = (JSONObject) obj; 
			JSONArray prodsInJSON = (JSONArray) JSONObj.get("items"); 
			items = parseItem(prodsInJSON);
		} catch (ParseException e) {
			logger.log(Level.WARNING,
					"Encounter exceptions when parse the response from search API service at position [" + e.getPosition() + "]");
			return null; 
		}
		
		return items;
	}

	@Override
	protected String assembleURL(Map<String, String> inputs) {
		StringBuilder sb = new StringBuilder(Constants.SERVICE_BASE);
		sb.append(SEARCH_SERVICE); 
		sb.append(assembleReqOptions(inputs));
		return sb.toString();
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

}
