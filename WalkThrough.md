### 1. Set Up the Project Structure

Ensure your project has the following structure:

- `src/main/java/com/revature/Project0`
  - `controllers`
  - `models`
  - `repositories`
  - `services`
  - `config`
  - `Project0Application.java`

### 2. Define the Models

#### User.java

```java
package com.revature.Project0.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items;
}
```

#### Item.java

```java
package com.revature.Project0.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
```

### 3. Define the Repositories

#### UserRepo.java

```java
package com.revature.Project0.repositories;

import com.revature.Project0.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
```

#### ItemRepo.java

```java
package com.revature.Project0.repositories;

import com.revature.Project0.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {
    List<Item> findByUserId(Long userId);
}
```

### 4. Define the Services

#### UserService.java

```java
package com.revature.Project0.services;

import com.revature.Project0.models.User;

public interface UserService {
    User register(User user);
    User login(String username, String password);
}
```

#### UserServiceImpl.java

```java
package com.revature.Project0.services;

import com.revature.Project0.models.User;
import com.revature.Project0.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User register(User user) {
        return userRepo.save(user);
    }

    @Override
    public User login(String username, String password) {
        User user = userRepo.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
```

#### ItemService.java

```java
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
```

#### ItemServiceImpl.java

```java
package com.revature.Project0.services;

import com.revature.Project0.models.Item;
import com.revature.Project0.repositories.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepo itemRepo;

    @Autowired
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
}
```

### 5. Define the Controllers

#### UserController.java

```java
package com.revature.Project0.controllers;

import com.revature.Project0.models.User;
import com.revature.Project0.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User newUser = userService.register(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User loggedInUser = userService.login(user.getUsername(), user.getPassword());
        if (loggedInUser != null) {
            return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
```

#### ItemController.java

```java
package com.revature.Project0.controllers;

import com.revature.Project0.models.Item;
import com.revature.Project0.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@CrossOrigin
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        Item newItem = itemService.addItem(item);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return new ResponseEntity<>(itemService.getAllItems(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        return new ResponseEntity<>(itemService.getItemById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item item) {
        item.setId(id);
        Item updatedItem = itemService.updateItem(item);
        if (updatedItem != null) {
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Item>> getItemsByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(itemService.getItemsByUserId(userId), HttpStatus.OK);
    }
}
```

### 6. Application Class

Ensure your main application class is correctly configured.

#### Project0Application.java

```java
package com.revature.Project0;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Project0Application {
    public static void main(String[] args) {
        SpringApplication.run(Project0Application.class, args);
    }
}
```

### 7. Update `application.properties`

Ensure your `application.properties` file is correctly set up for your database.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/testtable1
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### 8. Run the Application

Start your Spring Boot application and test the endpoints using a tool like Postman.

- **Register a User:** `POST http://localhost:8080/users/register`
- **Login a User:** `POST http://localhost:8080/users/login`
- **Create an Item:** `POST http://localhost:8080/items`
- **View All Items:** `GET http://localhost:808

0/items`
- **View Item by ID:** `GET http://localhost:8080/items/{id}`
- **Update Item:** `PUT http://localhost:8080/items/{id}`
- **Delete Item:** `DELETE http://localhost:8080/items/{id}`
- **View Items by User:** `GET http://localhost:8080/items/user/{userId}`

This setup ensures a simple login and registration system that shows tasks created by the user, without Spring Security interfering.