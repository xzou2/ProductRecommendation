package test.java.core;

import java.util.ArrayList;
import java.util.List;

import main.java.core.SimpleItemSort;
import main.java.core.interfaces.ItemSort;
import main.java.entity.Item;
import main.java.entity.Review;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SimpleItemSortTest {
	
	private ItemSort sorter ; 
	
	private List<Item> items; 
	
	private List<Item> expectedItemOrder;

	@Before
	public void setUp() throws Exception {
		sorter = new SimpleItemSort();
		items = new ArrayList<Item>();
		expectedItemOrder = new ArrayList<Item>();
	}

	@After
	public void tearDown() throws Exception {
		sorter = null;
		items = null; 
		expectedItemOrder = null;
	}

	private void prepareItems() {
		Item item1 = new Item(1, "item1",10);
		Review review1 = new Review(3.0, 5.0, 100);
		item1.setReview(review1);
		items.add(item1);
		
		Item item2 = new Item(2, "item2",20);
		Review review2 = new Review(4.0, 5.0, 300);
		item2.setReview(review2);
		items.add(item2);
		
		Item item3 = new Item(3, "item3",30);
		Review review3 = new Review(4.0, 5.0, 100);
		item3.setReview(review3);
		items.add(item3);
		
		Item item4 = new Item(4, "item4",40);
		Review review4 = new Review(4.5, 5.0, 400);
		item4.setReview(review4);
		items.add(item4);
		
		Item item5 = new Item(5, "item5",50);
		Review review5 = new Review(4.5, 5.0, 400);
		item5.setReview(review5);
		items.add(item5);
		
		expectedItemOrder.add(item4);
		expectedItemOrder.add(item5);
		expectedItemOrder.add(item2);
		expectedItemOrder.add(item3);
		expectedItemOrder.add(item1);
	}
	
	@Test
	public void testSortItemsByReview() {
		prepareItems();
		List<Item> sortedItems = sorter.sortItemsByReview(items);
		Assert.assertArrayEquals( expectedItemOrder.toArray(), sortedItems.toArray());
	}

	@Test
	public void testSortItemsWithNonReview() {
		prepareItemsWithNonReview(); 
		List<Item> sortedItems = sorter.sortItemsByReview(items);
		Assert.assertArrayEquals( expectedItemOrder.toArray(), sortedItems.toArray());
	}

	private void prepareItemsWithNonReview() {
		Item item1 = new Item(1, "item1",10);
		Review review1 = new Review(3.0, 5.0, 100);
		item1.setReview(review1);
		items.add(item1);
		
		Item item2 = new Item(2, "item2",20);
		Review review2 = new Review(4.0, 5.0, 300);
		item2.setReview(review2);
		items.add(item2);

		Item item4 = new Item(4, "item4",40);
		items.add(item4);
		
		Item item5 = new Item(5, "item5",50);
		Review review5 = new Review(4.5, 5.0, 400);
		item5.setReview(review5);
		items.add(item5);
		
		expectedItemOrder.add(item5);
		expectedItemOrder.add(item2);
		expectedItemOrder.add(item1);
		expectedItemOrder.add(item4);
	}

}
