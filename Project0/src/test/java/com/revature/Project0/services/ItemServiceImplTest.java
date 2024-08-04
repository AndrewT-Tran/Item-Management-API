package com.revature.Project0.services;

import com.revature.Project0.models.Item;
import com.revature.Project0.repositories.ItemRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ItemServiceImplTest {

    @Mock
    private ItemRepo itemRepo;

    @InjectMocks
    private ItemServiceImpl itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddItem() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Test Item");

        when(itemRepo.save(any(Item.class))).thenReturn(item);

        Item savedItem = itemService.addItem(item);

        assertNotNull(savedItem);
        assertEquals(1L, savedItem.getId());
        assertEquals("Test Item", savedItem.getName());
    }

    @Test
    void testDeleteItem() {
        doNothing().when(itemRepo).deleteById(1L);

        itemService.deleteItem(1L);

        verify(itemRepo, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllItems() {
        Item item1 = new Item();
        item1.setId(1L);
        item1.setName("Item 1");

        Item item2 = new Item();
        item2.setId(2L);
        item2.setName("Item 2");

        List<Item> items = Arrays.asList(item1, item2);

        when(itemRepo.findAll()).thenReturn(items);

        List<Item> allItems = itemService.getAllItems();

        assertNotNull(allItems);
        assertEquals(2, allItems.size());
        assertEquals(1L, allItems.get(0).getId());
        assertEquals("Item 1", allItems.get(0).getName());
    }

    @Test
    void testGetItemById() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Test Item");

        when(itemRepo.findById(1L)).thenReturn(Optional.of(item));

        Item foundItem = itemService.getItemById(1L);

        assertNotNull(foundItem);
        assertEquals(1L, foundItem.getId());
        assertEquals("Test Item", foundItem.getName());
    }

    @Test
    void testGetItemsByUserId() {
        Item item1 = new Item();
        item1.setId(1L);
        item1.setName("Item 1");

        Item item2 = new Item();
        item2.setId(2L);
        item2.setName("Item 2");

        List<Item> items = Arrays.asList(item1, item2);

        when(itemRepo.findByUserId(1L)).thenReturn(items);

        List<Item> userItems = itemService.getItemsByUserId(1L);

        assertNotNull(userItems);
        assertEquals(2, userItems.size());
        assertEquals(1L, userItems.get(0).getId());
        assertEquals("Item 1", userItems.get(0).getName());
    }

    @Test
    void testUpdateItem() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Updated Item");

        when(itemRepo.existsById(1L)).thenReturn(true);
        when(itemRepo.save(any(Item.class))).thenReturn(item);

        Item updatedItem = itemService.updateItem(item);

        assertNotNull(updatedItem);
        assertEquals(1L, updatedItem.getId());
        assertEquals("Updated Item", updatedItem.getName());
    }
}
