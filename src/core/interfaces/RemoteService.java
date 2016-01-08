package core.interfaces;

public interface RemoteService {
	
	/**
	 * @param url
	 * @return  null: the request is not success.
	 */
	public String requestService(String url) ; 
}
