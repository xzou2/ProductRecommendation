package main.java;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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



public class Application {

	public static final String SPACE = "%20";
	
	private static String joinKeywords(String[] args) {
		StringBuilder sb = new StringBuilder(); 
		String prefix = ""; 
		for ( int i = 1; i < args.length; ++ i) {
			sb.append(prefix);
			sb.append(args[i]);
			prefix = SPACE;
		}
		return sb.toString();
	}
	
	public static Map<String, String> genInputs(String[] args) {
		String keywords = joinKeywords(args);
		Map<String, String> inputs = new HashMap<String, String>(); 
		inputs.put(Constants.REQUEST_SEARCH, keywords);
		return inputs;
	}
	
	public static void run(String[] args) {
		Map<String, String> inputs = genInputs(args);
		RemoteService remoteService = new HttpService();
		Mediator searchMediator = new SearchMediator(remoteService);
		Mediator prodRecommendMediator = new ProdRecommendMediator(remoteService);
		Mediator reviewMediator = new ReviewMediator(remoteService) ;
		ItemSort sorter = new SimpleItemSort();
		Action controller = new Controller(searchMediator,prodRecommendMediator,reviewMediator,sorter);
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
		Presentation presentation = new CLIPresentation();
		String output = presentation.presentItems(items);
		System.out.println("We have following recommended items.");
		System.out.println(output);
	}

	
	public static void main(String[] args) {
		if ( args.length < 2) {
			System.out.println("Please input at least one search keyword");
			System.out.println("Input format: main key1 [key2] [key3] ...");
			return ;
		}
		run(args);
	}

	
	

	

}
