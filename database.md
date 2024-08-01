
# 1. Create a Database and User

1. Open the PostgreSQL command line interface (psql) or use a database management tool like pgAdmin.

2. Create a new database and user. Replace `your_database`, `postgres`, and `password` with your desired database name, username, and password.

```sql
CREATE DATABASE project0;
CREATE USER postgres WITH ENCRYPTED PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE project0 TO postgres;
```

# 2. Configure the Spring Boot Application

Update the `application.properties` file in your Spring Boot application to connect to the PostgreSQL database.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/project0
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

# 3. Create Tables and Seed Data

Create the necessary tables and seed the database with initial data if needed. You can do this by creating a SQL script and executing it in the PostgreSQL database.

#### Create `users` and `items` Tables

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

#### Insert Sample Data

```sql

-- Insert sample user
INSERT INTO users (username, password, email)
VALUES
   ('admin', 'password', 'admin@me.com'),
    ('john_doe', 'password123', 'john.doe@example.com'),
    ('jane_smith', 'securepassword', 'jane.smith@example.com'),
    ('alice_jones', 'mypassword', 'alice.jones@example.com');

-- Insert sample items
INSERT INTO items (name, description, status, user_id)
VALUES
    ('Laptop', 'A high-end gaming laptop', true, 1),  -- User with id 1 (admin)
    ('Smartphone', 'Latest model with great features', true, 2),  -- User with id 2 (jane_smith)
    ('Headphones', 'Noise-cancelling over-ear headphones', false, 1),  -- User with id 1 (admin)
    ('Monitor', '27-inch 4K monitor', true, 3);  -- User with id 3 (alice_jones)


```

# 4. Verify the Database Connection

Run your Spring Boot application and verify that it connects to the PostgreSQL database and creates the necessary tables. You can use a tool like pgAdmin to inspect the database and ensure that the tables and data are correctly set up.

# 5. Test the Application

Use Postman or any other HTTP client to test the endpoints of your application.

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

By following these steps, you will have a PostgreSQL database set up and connected to your Spring Boot application.
