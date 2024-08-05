package com.revature.Project0.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.Project0.models.Item;
import com.revature.Project0.repositories.ItemRepo;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepo itemRepo;

    public ItemServiceImpl(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }

    @Override
    public Item addItem(Item item) {
        return itemRepo.save(item);
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepo.findAll();
    }

    @Override
    public Item getItemById(Long id) {
        return itemRepo.findById(id).orElse(null);
    }

    @Override
    public Item updateItem(Item item) {
        if (itemRepo.existsById(item.getId())) {
            return itemRepo.save(item);
        }
        return null;
    }

    @Override
    public void deleteItem(Long id) {
        itemRepo.deleteById(id);
    }

    @Override
    public List<Item> getItemsByUserId(Long userId) {
        return itemRepo.findByUserId(userId);
    }

    @Override
    public List<Item> getItemsForUser(Long userId, boolean isAdmin) {
        if (isAdmin) {
            return getAllItems();
        } else {
            return getItemsByUserId(userId);
        }
    }
}
