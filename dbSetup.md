
# 1. Create a Database and User

```sql
CREATE DATABASE projectDB;
CREATE USER postgres WITH ENCRYPTED PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE project0 TO postgres;
```

# 2. Configure the Spring Boot Application

Update the `application.properties` file in your Spring Boot application to connect to the PostgreSQL database.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/projectDB?project0
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

# 3. Create Tables and Seed Data

Create the necessary tables and seed the database with initial data.

### Create `users` and `items` Tables

```sql
-- Makes Sure we using the right schema
SET search_path TO project0;
-- creates users table
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- Create items table
CREATE TABLE IF NOT EXISTS items (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    status BOOLEAN NOT NULL,
    user_id INT REFERENCES users(id) ON DELETE CASCADE
);
```

### SEED database with initial demo data

```sql
-- Insert sample users
INSERT INTO users (username, password, email)
VALUES
    ('admin', 'password', 'admin@me.com'),
    ('PGriffin', 'familyguy', 'Peter.Griffin@example.com'),
    ('JSnow', 'Winter', 'Winter@iscomming.com'),
    ('TKamado', 'Tanjaro', 'Tanjaro@FireBreathing.com');

-- Insert sample items
INSERT INTO items (name, description, status, user_id)
VALUES
    ('Laptop', 'A high-end gaming laptop', true, 1),
    ('Smartphone', 'Latest model with great features', true, 2),
    ('Headphones', 'Noise-cancelling over-ear headphones', false, 1),
    ('Monitor', '27-inch 4K monitor', true, 3);

  ```

# 4 Test Endpoints

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


