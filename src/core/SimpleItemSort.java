package core;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import core.interfaces.ItemSort;
import entity.Item;

public class SimpleItemSort implements ItemSort {

	@Override
	public List<Item> sortItemsByReview(List<Item> items) {
		Collections.sort(items, new Comparator<Item>() {

			@Override
			public int compare(Item o1, Item o2) {
				// TODO Auto-generated method stub
				return 0;
			}
			
		});
		return items;
	}

}
