package test.java.core;

import java.util.HashMap;
import java.util.Map;

import main.java.common.Constants;
import main.java.core.HttpService;
import main.java.core.interfaces.RemoteService;

import org.junit.After;
import org.junit.Before;

public class MediatorTest {
	
	protected RemoteService service ; 
	
	protected Map<String, String> inputs ;
	
	
	@Before
	public void setUp() throws Exception {
		
		inputs = new HashMap<String, String>();
		inputs.put(Constants.REQUEST_API_KEY, Constants.SERVICE_KEY);
		service = new HttpService();
	}

	@After
	public void tearDown() throws Exception {
		service = null;
		inputs = null;
	}

}
