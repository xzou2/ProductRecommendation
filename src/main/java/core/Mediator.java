package main.java.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.core.interfaces.RemoteService;
import main.java.entity.Item;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * A mediator class abstracts the invocation of search, product recommendation and review services
 * to three basic following steps.
 * First, prepare the request URL
 * Second, send the http request
 * Third, parse the response text in JSON format  
 * Created on : 07/01/2016
 * @author    : Xiaocheng Zou
 *
 */
public abstract class Mediator  {

	/**
	 * a remote service which provides the interface to the walmart open API service
	 */
	protected RemoteService service; 
	
	/**
	 * a JSON parser
	 */
	protected JSONParser jsonParser; 
	
	public Mediator ( RemoteService service) {
		this.service = service;
		jsonParser = new JSONParser();
	}
	
	protected abstract Logger getLogger(); 
	
	/**
	 * Given a set of parameters, in key-value pair format, form the request URL
	 * @param params
	 * 			a set of request parameters		
	 * @return
	 * 			a request URL
	 */
	protected abstract String prepareURL(Map<String, String> params);
	
	/**
	 * parse a string of text to a list of JSON objects
	 * @param response
	 * 			a response text
	 * @return
	 * 			a list of JSON objects
	 */
	protected abstract List<Object> parseResponse (String response);

	
	/**
	 * assemble parameters to a string of request URL. 
	 * different from prepareURL function, this function only assembles the URL after the URL base 
	 * @param params
	 * 			key-value pair parameters 
	 * @return
	 * 			a string of request URL
	 */
	protected String assembleReqOptions(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		String prefix = "?";
		for (String key : params.keySet()) {
			sb.append(prefix);
			sb.append(key) ; 
			sb.append("="); 
			sb.append(params.get(key)); 
			prefix = "&";
		}
		return sb.toString();
	}
	
	
	/**
	 * fulfill the abstracted three steps in the invocation of remote services.
	 * 
	 * @param inputs
	 * 		 key-value inputs
	 * @return
	 * 		a list of JSON objects. 
	 */
	public List<Object> invokeService(Map<String, String> inputs) {
		//1. prepare the request URL
		String url = prepareURL(inputs);
		//2. invoke the remote service 
		String response  = service.requestService(url);
		if ( response == null) return null; // if something wrong, return null
		//3. parse the response
		return parseResponse(response);
	} 
	
	/**
	 * parse the JSONArray object to a list of items
	 * @param itemsInJSON
	 * 		a JSONArray object
	 * @return
	 * 		a list of item
	 */
	protected List<Object> parseItem(JSONArray itemsInJSON) {
		List<Object> items = new ArrayList<Object>(); 
		for ( int i = 0; i < itemsInJSON.size(); ++ i ) {
			JSONObject obj = (JSONObject) itemsInJSON.get(i); 
			Item item = new Item(
					(long) obj.get("itemId"), 
					(String) obj.get("name"),
					obj.get("salePrice") == null ? 0.0 : (double) obj.get("salePrice") 
					);
			items.add(item);
		}
		getLogger().log(Level.FINE, 
				"Obtain [" + items.size() + "] items from the response parsing" );
		return items; 
	}
	
}
