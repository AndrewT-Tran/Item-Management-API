# Project 0: Item Management Application

## Project Overview

This project is a Spring Boot application with a PostgreSQL database backend. It provides APIs for user registration, login, and item management. Users can register, log in, create items, view items, update items, and delete items. The application uses JPA for ORM and Hibernate for the persistence layer.

### Technologies Used
- **Spring Boot 3.3.2**
  - Spring Boot Starter Web
  - Spring Boot Starter Data JPA
  - Spring Boot Starter Actuator
- **Java 17**
- **PostgreSQL**
- **Hibernate**
- **Mockito Core** for Unit Testing
- **Spring Boot Starter Test**
- **Spring Boot DevTools** for Development
- **H2 Database** for Testing
- **Lombok** for reducing boilerplate code

## Table of Contents
- [Database Setup](#database-setup)
- [Spring Boot Configuration](#spring-boot-configuration)
- [Database Schema](#database-schema)
  - [Create Tables](#create-tables)
  - [Seed Data](#seed-data)
- [API Endpoints](#api-endpoints)
  - [User Operations](#user-operations)
  - [Item Operations](#item-operations)
- [Running the Application](#running-the-application)
- [Testing Endpoints](#testing-endpoints)

## Database Setup

### 1. Create Database and User

```sql
CREATE DATABASE projectDB;
CREATE USER postgres WITH ENCRYPTED PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE project0 TO postgres;
```

## Spring Boot Configuration

Update the `application.properties` file in your Spring Boot application to connect to the PostgreSQL database.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/projectDB
spring.jpa.properties.hibernate.default_schema=project0
spring.datasource.username=${DB_username}
spring.datasource.password=${DB_password}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

## Database Schema

### Create Tables

Create the necessary tables for the application.

```sql
-- Makes sure we are using the right schema
SET search_path TO project0;

-- Create users table
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

### Seed Data

Seed the database with initial data.

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

## API Endpoints

### User Operations

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

### Item Operations

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

## Running the Application

### Build and Package the Application

```sh
mvn clean package -Dspring-boot.run.arguments="--DB_username=postgres --DB_password=password"
```

### Set Environment Variables

```sh
export DB_username="postgres"
export DB_password="password"
```

### Run the Application

```sh
java -jar target/Project0-0.0.1-SNAPSHOT.jar
```

## Testing Endpoints

### Register a User
- **Endpoint:** `POST http://localhost:8080/users/register`
- **Request Example (cURL):**
    ```sh
    curl -X POST http://localhost:8080/users/register \
    -H "Content-Type: application/json" \
    -d '{
      "email": "test@example.com",
      "username": "testuser",
      "password": "password"
    }'
    ```

### Login a User
- **Endpoint:** `POST http://localhost:8080/users/login`
- **Request Example (cURL):**
    ```sh
    curl -X POST http://localhost:8080/users/login \
    -H "Content-Type: application/json" \
    -d '{
      "username": "testuser",
      "password": "password"
    }'
    ```

### Create an Item
- **Endpoint:** `POST http://localhost:8080/items`
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

### View All Items
- **Endpoint:** `GET http://localhost:8080/items`
- **URL:** [http://localhost:8080/items](http://localhost:8080/items)

### View Item by ID
- **Endpoint:** `GET http://localhost:8080/items/{id}`

### Update Item
- **Endpoint:** `PUT http://localhost:8080/items/{id}`
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

### Delete Item
- **Endpoint:** `DELETE http://localhost:8080/items/{id}`

### View Items by User
- **Endpoint:** `GET http://localhost:8080/items/user/{userId}`

---

