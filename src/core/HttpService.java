package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import core.interfaces.RemoteService;

public class HttpService implements RemoteService {

	private static final Logger log = Logger.getLogger(HttpService.class.getName());
	
	@Override
	public String requestService(String url)  {
		
		log.log(Level.INFO, "Send a http request to URL [" + url + "].");
		
		URL urlObj;
		try {
			urlObj = new URL (url);
		} catch (MalformedURLException e) {
			log.log(Level.WARNING, "URL [" + url 
		               + "] is in bad format.");
			return null ;
		}
		
		HttpURLConnection httpConn;
		try {
			httpConn = (HttpURLConnection) urlObj.openConnection();
		} catch (IOException e) {
			log.log(Level.SEVERE, "Http connect can not be established.");
			return null; 
		}
		
		int responseCode;
		try {
			responseCode = httpConn.getResponseCode();
		} catch (IOException e) {
			log.log(Level.SEVERE, "Http response is lost.");
			return null;
		}
		
		if ( responseCode != HttpURLConnection.HTTP_OK) {
			log.log(Level.SEVERE, "Encounter difficulties in requesting the service at url [" + url 
					               + "], and get response code [" + responseCode + "].");
			return null;
		}
		
		BufferedReader in;
		try {
			in = new BufferedReader(
			        new InputStreamReader(httpConn.getInputStream()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "Can not establish input stream from remote services.");
			return null; 
		}
		String inputLine;
		StringBuffer response = new StringBuffer();
		try {
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
		} catch (IOException e) {
			log.log(Level.SEVERE, "Can not read stream from remote services.");
			return null; 
			
		}finally{
			
			try {
				in.close();
			} catch (IOException e) {
				log.log(Level.SEVERE, "Can not close input buffer reader.");
			}
			
		}
		
		log.log(Level.INFO, "Http request succeeds");
		return response.toString();
		
	}

}
