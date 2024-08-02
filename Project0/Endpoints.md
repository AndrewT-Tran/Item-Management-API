- **Register a User:** `POST http://localhost:8080/users/register`

#### *curl POST request*

```sh
curl -X POST http://localhost:8080/users/register \
-H "Content-Type: application/json" \
-d '{
  "email": "test@example.com",
  "username": "testuser",
  "password": "password"
}'
```

#### *JSON body for POST*

```json
{
  "email": "test@example.com",
  "username": "testuser",
  "password": "password"
}
```

- **Login a User:** `POST http://localhost:8080/users/login`

#### *curl POST request*

```sh
curl -X POST http://localhost:8080/users/login \
-H "Content-Type: application/json" \
-d '{
  "username": "testuser",
  "password": "password"
}'
```

#### *JSON body for POST*

```json
{
  "username": "testuser",
  "password": "password"
}
```

- **Create an Item:** `POST http://localhost:8080/items`

#### *JSON body for POST*

```json

{
    "name": "PlayStation 6",
    "description": "This is a big hit",
    "status": true,
    "user": {
        "id": 1
    }
}
```

#### *curl POST request*

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

- **View All Items:** `GET http://localhost:8080/items`

  - [http://localhost:8080/items](http://localhost:8080/items)

- **View Item by ID:** `GET http://localhost:8080/items/{id}`
- **Update Item:** `PUT http://localhost:8080/items/{id}`

#### *curl PUT request*

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

#### *JSON body for PUT request*

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

- **Delete Item:** `DELETE http://localhost:8080/items/{id}`
- **View Items by User:** `GET http://localhost:8080/items/user/{userId}`