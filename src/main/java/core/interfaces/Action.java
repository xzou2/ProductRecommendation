package main.java.core.interfaces;

import java.util.List;
import java.util.Map;

import main.java.entity.Item;


public interface Action {

	/**
	 * @param inputs
	 * @return
	 */
	public List<Item> getRecommendations(Map<String, String> inputs);
}
