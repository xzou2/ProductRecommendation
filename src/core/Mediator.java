package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import core.interfaces.RemoteService;
import entity.Item;


public abstract class Mediator  {

	protected RemoteService service; 
	
	protected JSONParser jsonParser ; 
	
	protected abstract Logger getLogger(); 
	
	protected abstract List<Object> parseResponse (String response);
	
	protected abstract String assembleURL(Map<String, String> inputs);

	protected String assembleReqOptions(Map<String, String> inputs) {
		StringBuilder sb = new StringBuilder();
		String prefix = "?";
		for (String key : inputs.keySet()) {
			sb.append(prefix);
			sb.append(key) ; 
			sb.append("="); 
			sb.append(inputs.get(key)); 
			prefix = "&";
		}
		return sb.toString();
	}
	
	
	public List<Object> sendRequest(Map<String, String> inputs) {
		String url = assembleURL(inputs);
		String response  = service.requestService(url); 
		if ( response == null) return null; 
		return parseResponse(response);
	} 
	
	protected List<Object> parseItem(JSONArray itemsInJSON) {
		List<Object> items = new ArrayList<Object>(); 
		for ( int i = 0; i < itemsInJSON.size(); ++ i ) {
			JSONObject obj = (JSONObject) itemsInJSON.get(i); 
			Item item = new Item(
					(String) obj.get("itemId"), 
					(String) obj.get("name"),
					(String) obj.get("salePrice")
					);
			items.add(item);
		}
		getLogger().log(Level.INFO, 
				"Obtain [" + items.size() + "] items from the response parsing" );
		return items; 
	}
	
}
