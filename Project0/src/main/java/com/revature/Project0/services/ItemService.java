package com.revature.Project0.services;

import com.revature.Project0.models.Item;

import java.util.List;

public interface ItemService {
    Item addItem(Item item);

    List<Item> getAllItems();

    Item getItemById(Long id);

    Item updateItem(Item item);

    void deleteItem(Long id);

    List<Item> getItemsByUserId(Long userId);
}
