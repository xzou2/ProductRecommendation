package main.java.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.core.interfaces.RemoteService;
/**
 * A class implements the RemoteService by the Http protocol.
 * The response of the service is a JSON string.
 * Note that this class does not throws out exceptions if there are any exceptions occurred during the http request. 
 * It simply returns a null to the caller, indicating there is something wrong underneath. 
 * The detailed causes are kept in the logs  
 * Created on : 07/01/2016
 * @author    : Xiaocheng Zou
 *
 */
public class HttpService implements RemoteService {

	private static final Logger log = Logger.getLogger(HttpService.class.getName());
	
	/*a list of variables defines different kinds of log messages*/
	public static final String BAD_URL_LOG = "URL is in the bad format.";
	
	public static final String UNOPENED_CONNECTION_LOG = "Http connect can not be opened.";
	
	public static final String NO_RESPONSE_CODE_LOG = "Can not get Http response code.";
	
	public static final String NOT_OKAY_RESPONSE_CODE_LOG = "Got not-okay http response code.";
	
	public static final String NO_ESTABLISHED_INPUT_STREAM_LOG = "Can not establish input stream from remote services.";
	
	public static final String CAN_NOT_READ_STREAM_LOG = "Can not read stream from remote services.";
	
	public static final String CAN_NOT_CLOSE_STREAM_LOG = "Can not close input buffer reader.";
	
	public static final String SUCCESS_LOG = "Http request succeeds";
	
	@Override
	public String requestService(String url)  {
		
		log.log(Level.FINE, "Send a http request to URL " + url );

		//create an URL boject
		URL urlObj;
		try {
			urlObj = new URL (url);
		} catch (MalformedURLException e) {
			log.log(Level.WARNING, BAD_URL_LOG);
			return null ;
		}
		
		// open the http connection
		HttpURLConnection httpConn;
		try {
			httpConn = (HttpURLConnection) urlObj.openConnection();
		} catch (IOException e) {
			log.log(Level.SEVERE, UNOPENED_CONNECTION_LOG);
			return null; 
		}
		
		// get the response code
		int responseCode;
		try {
			responseCode = httpConn.getResponseCode();
		} catch (IOException e) {
			log.log(Level.SEVERE, NO_RESPONSE_CODE_LOG);
			return null;
		}
		
		// check if the response code is okay or not
		if ( responseCode != HttpURLConnection.HTTP_OK) {
			log.log(Level.SEVERE, NOT_OKAY_RESPONSE_CODE_LOG);
			return null;
		}
		
		// prepare for the response receiving
		BufferedReader in;
		try {
			in = new BufferedReader(
			        new InputStreamReader(httpConn.getInputStream()));
		} catch (IOException e) {
			log.log(Level.SEVERE, NO_ESTABLISHED_INPUT_STREAM_LOG );
			return null; 
		}
		// read the response text
		String inputLine;
		StringBuffer response = new StringBuffer();
		try {
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
		} catch (IOException e) {
			log.log(Level.SEVERE, CAN_NOT_READ_STREAM_LOG);
			return null; 
			
		}finally{
			// close the read stream
			try {
				in.close();
			} catch (IOException e) {
				log.log(Level.SEVERE, CAN_NOT_CLOSE_STREAM_LOG);
			}
		}
		
		log.log(Level.FINE, SUCCESS_LOG );
		return response.toString();
		
	}

}
