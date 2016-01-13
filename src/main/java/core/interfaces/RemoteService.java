package main.java.core.interfaces;


/**
 * We abstract the Walmart's Open API services as remote services
 * 
 * Created on : 07/01/2016
 * @author    : Xiaocheng Zou
 *
 */
public interface RemoteService {
	
	/**
	 * send a request the walmart service and return the response text  
	 * @param url
	 * 		a request URL 
	 * @return
	 * 		a string of responded text. if the returned value is null, it means the request is not success.
	 */
	public String requestService(String url) ; 
}
