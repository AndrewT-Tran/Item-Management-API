package com.revature.Project0.controllers;

import com.revature.Project0.models.Item;
import com.revature.Project0.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// The @RestController Indicates that this class is REST controller
// and the methods should RETURN DATA directly rather than views

@RequestMapping("/items") // sets the base url path for all methods inm this controller

@CrossOrigin // allows cross-origin requests, which is we need if the API is accessed from a
// diff domain or port
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    // Adds a new item
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        // the method addItem(@RequestBody Item item) expects a JSON representaiton of
        // the item.
        Item newItem = itemService.addItem(item);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
        // Returns the newly created Item with an HTTP status of 201 Created.
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        // Method: getAllItems()
        List<Item> items = itemService.getAllItems();
        // Response: Returns a list of all Item entities with an HTTP status of 200 OK.
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        // the @PathVariable is what we get from url and is id - the ID of the Item to
        // retrieve.
        // Method: getItemById(@PathVariable Long id)
        Item item = itemService.getItemById(id);
        if (item != null) {
            return new ResponseEntity<>(item, HttpStatus.OK);
            // Returns the Item if found,
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // or an HTTP status of 404 Not Found if the Item does not exist.
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item item) {
        item.setId(id);
        // will update the item ID matches teh DB it will taake teh requestbody which is
        // the JSON of the item details and update it
        Item updatedItem = itemService.updateItem(item);
        if (updatedItem != null) {
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
            // Returns the updated Item if the update is successful,
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // Returns an HTTP status of 404 Not Found if the Item does not exist.
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Item>> getItemsByUserId(@PathVariable Long userId) {
        List<Item> items = itemService.getItemsByUserId(userId);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
