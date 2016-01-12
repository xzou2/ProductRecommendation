package test.java.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.common.Constants;
import main.java.core.HttpService;
import main.java.core.Mediator;
import main.java.core.ProdRecommendMediator;
import main.java.core.interfaces.RemoteService;
import main.java.entity.Item;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProdRecommendMediatorTest extends MediatorTest {

	private Mediator prodRecommender ; 
	
	@Before
	public void setUp() throws Exception {
		super.setUp(); 
		prodRecommender = new ProdRecommendMediator(service);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		prodRecommender = null; 
	}

	@Test
	public void testInvalidItemID() {
		Map<String, String> inputs = new HashMap<String, String>();
		inputs.put(Constants.REQUEST_API_KEY, Constants.SERVICE_KEY);
		// for a invalid itemId, we have response code 400 (bad request)
		inputs.put(Constants.REQUEST_ITEM, "xxxdd");  
		List<Object> items = prodRecommender.sendRequest(inputs);
		Assert.assertNull(items);
	}
	
	@Test
	public void testEmptyRecommendedItems() {
		Map<String, String> inputs = new HashMap<String, String>();
		inputs.put(Constants.REQUEST_API_KEY, Constants.SERVICE_KEY);
		inputs.put(Constants.REQUEST_ITEM, "99283293");
		List<Object> items = prodRecommender.sendRequest(inputs);
		Assert.assertTrue(items.size() == 0);
	}
	
	@Test 
	public void testRecommendedItems() {
		Map<String, String> inputs = new HashMap<String, String>();
		inputs.put(Constants.REQUEST_API_KEY, Constants.SERVICE_KEY);
		inputs.put(Constants.REQUEST_ITEM, "36904791");
		List<Object> items = prodRecommender.sendRequest(inputs);
		Assert.assertTrue(items.size() > 0);
		for ( Object obj : items ) {
			Item item  = (Item) obj ; 
			Assert.assertNotNull(item.getID());
			Assert.assertNotNull(item.getName());
			Assert.assertNotNull(item.getSalePrice());
		}
	}
}
