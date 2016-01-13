package main.java;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.LogManager;

import main.java.common.Constants;
import main.java.core.Controller;
import main.java.core.HttpService;
import main.java.core.Mediator;
import main.java.core.ProdRecommendMediator;
import main.java.core.ReviewMediator;
import main.java.core.SearchMediator;
import main.java.core.SimpleItemSort;
import main.java.core.interfaces.Action;
import main.java.core.interfaces.ItemSort;
import main.java.core.interfaces.RemoteService;
import main.java.entity.Item;
import main.java.gui.CLIPresentation;
import main.java.gui.Presentation;

/**
 * An entrance of our application. 
 * It first resolves user input keywords, and then call the corresponding interface to get the recommended items. 
 * At last, it calls an interface to present the returned items.
 *  
 * Created on : 07/01/2016
 * @author    : Xiaocheng Zou
 *
 */
public class Application {

	
	/**
	 * A blank space in the URL address 
	 */
	public static final String SPACE = "%20";
	
	/**
	 * Join an array of keywords with a given connector and form a string 
	 * @param args 
	 * 			an array of user's search keywords
	 * @param connector
	 * 			a connector connects two keyword
	 * @return
	 * 			a joined-keyword string
	 */
	private static String joinKeywords(String[] args, String connector) {
		StringBuilder sb = new StringBuilder(); 
		String prefix = ""; 
		for ( int i = 0; i < args.length; ++ i) {
			sb.append(prefix);
			sb.append(args[i]);
			prefix = connector;
		}
		return sb.toString();
	}
	
	/**
	 * Generate query parameters in key-value pairs for users' input keywords
	 * @param args users' keywords
	 * @return  a hashmap keeps query parameters
	 */
	public static Map<String, String> genQueryParams(String[] args) {
		String keywords = joinKeywords(args, SPACE);
		Map<String, String> inputs = new HashMap<String, String>(); 
		inputs.put(Constants.REQUEST_SEARCH, keywords);
		inputs.put(Constants.REQUEST_API_KEY, Constants.SERVICE_KEY);
		return inputs;
	}
	
	public static void run(String[] args) {
		//resolve loading log property file 
		String logFile = System.getProperty("java.util.logging.config.file");
        if(logFile == null){
            try {
				LogManager.getLogManager().readConfiguration(Application.class.getClassLoader().getResourceAsStream("logging.properties"));
			} catch (SecurityException e) {
				System.out.println("don't have Log permission.");
				return ;
			} catch (IOException e) {
				System.out.println("There are errors in reading the logging property file.");
				return ;
			}
        }              
		
		Map<String, String> inputs = genQueryParams(args);
		
		//create instances 
		RemoteService remoteService = new HttpService();
		Mediator searchMediator = new SearchMediator(remoteService);
		Mediator prodRecommendMediator = new ProdRecommendMediator(remoteService);
		Mediator reviewMediator = new ReviewMediator(remoteService) ;
		ItemSort sorter = new SimpleItemSort();
		Action controller = new Controller(searchMediator,prodRecommendMediator,reviewMediator,sorter);
		
		// call the item recommendation service
		List<Item> items = controller.getRecommendations(inputs);
		if ( items == null ) {
			System.out.println("Recommendation service can not be performed.");
			System.out.println("Please check the log for the details");
			return ; 
		}
		if ( items.size() == 0 ) {
			System.out.println("There are no recommended products.");
			System.out.println("Please fine your keywords");
			return ;
		}
		
		// present the results
		Presentation presentation = new CLIPresentation();
		String output = presentation.presentItems(items);
		
		String keys = joinKeywords(args, " ");
		System.out.println("You input keywords: " + keys);
		System.out.println("We have following recommended items.");
		System.out.println(output);
	}
	
	public static void main(String[] args) {
		if ( args.length < 1) {
			System.out.println("Please input at least one search keyword");
			System.out.println("Input format: main key1 [key2] [key3] ...");
			return ;
		}
		run(args);
	}
}
