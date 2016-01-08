package core.interfaces;

import java.util.List;
import java.util.Map;

import entity.Item;

/**
 * @author xczou
 *
 */
public interface Action {

	/**
	 * @param inputs
	 * @return
	 */
	public List<Item> getRecommendations(Map<String, String> inputs);
}
