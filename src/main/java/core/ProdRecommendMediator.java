package main.java.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.common.Constants;
import main.java.core.interfaces.RemoteService;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class ProdRecommendMediator extends Mediator {

	public ProdRecommendMediator(RemoteService service) {
		super(service);
	}

	private static final Logger log = Logger.getLogger(ProdRecommendMediator.class.getName());
	
	public static final String RECOMMEND_SERVICE = "nbp";
	
	@Override
	protected Logger getLogger() {
		return log;
	}

	@Override
	protected List<Object> parseResponse(String response) {
		List<Object> items  = new ArrayList<Object>();
		try {
			Object obj = jsonParser.parse(response);
			JSONArray arrObj = (JSONArray)obj ; 
			items = parseItem(arrObj);
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
