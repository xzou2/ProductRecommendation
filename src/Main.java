import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Constants;

import core.Controller;
import core.interfaces.Action;
import entity.Item;
import gui.CLIPresentation;
import gui.Presentation;



public class Main {

	private static String joinKeywords(String[] args) {
		StringBuilder sb = new StringBuilder(); 
		String prefix = ""; 
		for ( int i = 1; i < args.length; ++ i) {
			sb.append(prefix);
			sb.append(args[i]);
			prefix = " ";
		}
		return sb.toString();
	}
	
	public static Map<String, String> genInputs(String[] args) {
		String keywords = joinKeywords(args);
		Map<String, String> inputs = new HashMap<String, String>(); 
		inputs.put(Constants.REQUEST_SEARCH, keywords);
		return inputs;
	}
	
	public static void main(String[] args) {
		if ( args.length < 2) {
			System.out.println("Please input at least one search keyword");
			System.out.println("Input format: main key1 [key2] [key3] ...");
			return ;
		}
		Map<String, String> inputs = genInputs(args);
		Action controller = new Controller();
		List<Item> items = controller.getRecommendations(inputs);
		Presentation presentation = new CLIPresentation();
		String output = presentation.presentItems(items);
		System.out.println(output);
	}

	

	

}
