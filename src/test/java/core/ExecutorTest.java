package test.java.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.Application;
import main.java.common.Constants;
import main.java.core.Executor;
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExecutorTest {

	private Action executor ;
	
	protected Map<String, String> inputs ;
	
	@Before
	public void setUp() throws Exception {
		RemoteService remoteService = new HttpService();
		Mediator searchMediator = new SearchMediator(remoteService);
		Mediator prodRecommendMediator = new ProdRecommendMediator(remoteService);
		Mediator reviewMediator = new ReviewMediator(remoteService) ;
		ItemSort sorter = new SimpleItemSort();
		executor = new Executor(searchMediator,prodRecommendMediator,reviewMediator,sorter);
		
		inputs = new HashMap<String, String>();
		inputs.put(Constants.REQUEST_API_KEY, Constants.SERVICE_KEY);
	}

	@After
	public void tearDown() throws Exception {
		executor = null; 
		inputs = null;
	}

	@Test
	public void testGetRecommendations() {
		inputs.put(Constants.REQUEST_SEARCH, "convertible"+ Application.SPACE 
				                                + "car" + Application.SPACE + "seat");
		List<Item> recommendedItems = executor.getRecommendations(inputs);
		Assert.assertTrue(recommendedItems.size() > 0 );
	}
	
	@Test
	public void testNonSenseSearch() {
		inputs.put(Constants.REQUEST_SEARCH, "xxx" + Application.SPACE 
				                                + "yyy");
		List<Item> recommendedItems = executor.getRecommendations(inputs);
		Assert.assertTrue(recommendedItems.size()  == 0 );
	}

}
