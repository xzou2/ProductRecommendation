package test.java.core;

import java.util.List;

import main.java.common.Constants;
import main.java.core.Mediator;
import main.java.core.SearchMediator;
import main.java.entity.Item;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SearchMediatorTest  extends MediatorTest {

	private Mediator searcher;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		searcher = new SearchMediator(service);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		searcher = null;
	}

	@Test
	public void testNoFoundItems() {
		inputs.put(Constants.REQUEST_SEARCH, "xxxxcccc");
		List<Object> items = searcher.invokeService(inputs);
		Assert.assertTrue(items.size() == 0);
	}
	
	@Test
	public void testFoundItems() {
		inputs.put(Constants.REQUEST_SEARCH, "ipod");
		List<Object> items = searcher.invokeService(inputs);
		Assert.assertTrue(items.size() > 0);
		for ( Object obj : items ) {
			Item item  = (Item) obj ; 
			Assert.assertNotNull(item.getID());
			Assert.assertNotNull(item.getName());
			Assert.assertNotNull(item.getSalePrice());
		}
		
	}

}
