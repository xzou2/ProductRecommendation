package test.java.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import main.java.common.Constants;
import main.java.core.HttpService;
import main.java.core.ProdRecommendMediator;
import main.java.core.ReviewMediator;
import main.java.core.SearchMediator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HttpServiceTest {

	private static Logger log = Logger.getLogger(HttpService.class.getName());

	private static OutputStream logCapturingStream;

	private static StreamHandler customLogHandler;
	
	private HttpService httpService; 

	private void attachLogCapturer() {
		logCapturingStream = new ByteArrayOutputStream();
		Handler[] handlers = log.getParent().getHandlers();
		customLogHandler = new StreamHandler(logCapturingStream, handlers[0].getFormatter());
		log.addHandler(customLogHandler);
		
	    for(Handler handler : log.getHandlers()) {
	      handler.setLevel(Level.FINE);
	    }
	    log.setLevel(Level.FINE);
	}

	private String getTestCapturedLog() throws IOException {
	  customLogHandler.flush();
	  return logCapturingStream.toString();
	}
	
	@Before
	public void setUp() throws Exception {
		httpService = new HttpService();
		attachLogCapturer();
	}

	@After
	public void tearDown() throws Exception {
		httpService = null; 
	}

	private void testUnsuccessfulRequestService(String url, String expectedLogInfo) {
		String response = httpService.requestService(url); 
		String log;
		try {
			log = getTestCapturedLog();
			Assert.assertNull(response);
			Assert.assertNotNull(log);
			Assert.assertTrue(log.contains(expectedLogInfo));
		} catch (IOException e) {
			Assert.fail("Can not get log msg");
		}
	} 
	
	private void testSuccessfulRequestService(String url) {
		String response = httpService.requestService(url); 
		String log;
		try {
			log = getTestCapturedLog();
			Assert.assertNotNull(response);
			Assert.assertNotNull(log);
			Assert.assertTrue(log.contains(HttpService.SUCCESS_LOG));
		} catch (IOException e) {
			Assert.fail("Can not get log msg");
		}
	}
	
	@Test
	public void testBadURLSerive() {
		testUnsuccessfulRequestService("badUrl", HttpService.BAD_URL_LOG);
		StringBuilder sb = new StringBuilder(Constants.SERVICE_BASE);
		sb.append(SearchMediator.SEARCH_SERVICE);
		testUnsuccessfulRequestService(sb.toString(), HttpService.NOT_OKAY_RESPONSE_CODE_LOG);
		
	}
	
	@Test
	public void testProdRecommendationService() {
		StringBuilder sb = new StringBuilder(Constants.SERVICE_BASE);
		sb.append(ProdRecommendMediator.RECOMMEND_SERVICE);
		sb.append("?"); 
		sb.append(Constants.REQUEST_API_KEY);
		sb.append("=");
		sb.append(Constants.SERVICE_KEY);
		sb.append("&");
		sb.append(Constants.REQUEST_ITEM);
		sb.append("=");
		sb.append("36904791");
		testSuccessfulRequestService(sb.toString());
	}
	
	@Test
	public void testReviewService() {
		StringBuilder sb = new StringBuilder(Constants.SERVICE_BASE);
		sb.append(ReviewMediator.REVIEW_SERVICE);
		sb.append("/33093101?"); 
		sb.append(Constants.REQUEST_API_KEY);
		sb.append("=");
		sb.append(Constants.SERVICE_KEY);
		sb.append("&");
		testSuccessfulRequestService(sb.toString());
	}
	
	@Test
	public void testSearchService() {
		StringBuilder sb = new StringBuilder(Constants.SERVICE_BASE);
		sb.append(SearchMediator.SEARCH_SERVICE);
		sb.append("?"); 
		sb.append(Constants.REQUEST_API_KEY);
		sb.append("=");
		sb.append(Constants.SERVICE_KEY);
		sb.append("&");
		sb.append(Constants.REQUEST_SEARCH);
		sb.append("=");
		sb.append("ipod");
		testSuccessfulRequestService(sb.toString());
	}

}
