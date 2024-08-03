# API Endpoints

## User Operations

#### Register a User
- **Endpoint:** `POST /users/register`
- **Request Example (cURL):**
    ```sh
    curl -X POST http://localhost:8080/users/register \
    -H "Content-Type: application/json" \
    -d '{
      "email": "me@yaboy.com",
      "username": "admin",
      "password": "password"
    }'
    ```
- **Request Body:**
    ```json
    {
      "email": "test@example.com",
      "username": "testuser",
      "password": "password"
    }
    ```

#### Login a User
- **Endpoint:** `POST /users/login`
- **Request Example (cURL):**
    ```sh
    curl -X POST http://localhost:8080/users/login \
    -H "Content-Type: application/json" \
    -d '{
      "username": "testuser",
      "password": "password"
    }'
    ```
- **Request Body:**
    ```json
    {
      "username": "testuser",
      "password": "password"
    }
    ```

## Item Operations

#### Create an Item
- **Endpoint:** `POST /items`
- **Request Example (cURL):**
    ```sh
    curl -X POST http://localhost:8080/items \
    -H "Content-Type: application/json" \
    -d '{
      "name": "Sample Item",
      "description": "This is a sample item",
      "status": true,
      "user": {"id": 1}
    }'
    ```
- **Request Body:**
    ```json
    {
      "name": "Sample Item",
      "description": "This is a sample item",
      "status": true,
      "user": {
        "id": 1
      }
    }
    ```

#### View All Items
- **Endpoint:** `GET /items`
- **URL:** [http://localhost:8080/items](http://localhost:8080/items)

#### View Item by ID
- **Endpoint:** `GET /items/{id}`

#### Update Item
- **Endpoint:** `PUT /items/{id}`
- **Request Example (cURL):**
    ```sh
    curl -X PUT http://localhost:8080/items/1 \
    -H "Content-Type: application/json" \
    -d '{
      "name": "Updated Item Name",
      "description": "Updated Description",
      "status": true,
      "user": {
        "id": 1
      }
    }'
    ```
- **Request Body:**
    ```json
    {
      "name": "Updated Item Name",
      "description": "Updated Description",
      "status": true,
      "user": {
        "id": 1
      }
    }
    ```

#### Delete Item
- **Endpoint:** `DELETE /items/{id}`

#### View Items by User
- **Endpoint:** `GET /items/user/{userId}`

---

Feel free to adjust as needed!
