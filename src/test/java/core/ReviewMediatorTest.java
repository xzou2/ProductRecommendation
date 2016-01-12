package test.java.core;

import java.util.List;

import main.java.common.Constants;
import main.java.core.Mediator;
import main.java.core.ReviewMediator;
import main.java.entity.Review;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReviewMediatorTest extends MediatorTest {

	

	private Mediator reviewMediator ; 
	
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		reviewMediator = new ReviewMediator(service);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		reviewMediator = null; 
	}

	@Test
	public void testEmptyReview() {
		inputs.put(Constants.REQUEST_ITEM, "99123456");
		List<Object> items = reviewMediator.sendRequest(inputs);
		Assert.assertTrue(items.size() == 0);
	}
	
	@Test 
	public void testInvalidReview() {
		inputs.put(Constants.REQUEST_ITEM, "99xxxikk");
		List<Object>  items = reviewMediator.sendRequest(inputs);
		Assert.assertNull(items);
	}
	
	@Test
	public void testReviewRetrieval() {
		inputs.put(Constants.REQUEST_ITEM, "33093101");
		List<Object> items = reviewMediator.sendRequest(inputs);
		Assert.assertTrue(items.size() == 1);
		Review review = (Review) items.get(0);
		Assert.assertTrue(review.getAverageOverallRating() == 4.79 );
		Assert.assertTrue(review.getOverallRatingRange() == 5 );
		Assert.assertTrue(review.getTotalReviewCount() == 209 );
	}

}
