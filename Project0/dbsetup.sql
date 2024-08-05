-- Drop existing tables if they exist
DROP TABLE IF EXISTS items;

DROP TABLE IF EXISTS users;

-- Create the users table
CREATE TABLE
    users (
        id SERIAL PRIMARY KEY,
        username VARCHAR(255) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL UNIQUE,
        role VARCHAR(255) NOT NULL DEFAULT 'USER'
    );

-- Create the items table
CREATE TABLE
    items (
        id SERIAL PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        description TEXT NOT NULL,
        status BOOLEAN NOT NULL,
        user_id BIGINT NOT NULL,
        FOREIGN KEY (user_id) REFERENCES users (id)
    );

-- Insert an admin user (example)
INSERT INTO
    users (username, password, email, role)
VALUES
    (
        'admin',
        '$2a$10$GWF7MmfXC122m.HLWwl.huX9UcZDhoqlqq/uoGDj7WIFvaDO6UIhy',
        'admin@example.com',
        'ADMIN'
    );

-- Insert a regular user (example)
INSERT INTO
    users (username, password, email, role)
VALUES
    (
        'user',
        '$2a$10$GWF7MmfXC122m.HLWwl.huX9UcZDhoqlqq/uoGDj7WIFvaDO6UIhy',
        'user@example.com',
        'USER'
    );

-- Insert example items (example)
INSERT INTO
    items (name, description, status, user_id)
VALUES
    ('Item 1', 'Description for item 1', true, 2),
    ('Item 2', 'Description for item 2', false, 2);

-- Drop existing tables if they exist
DROP TABLE IF EXISTS items;

DROP TABLE IF EXISTS users;

-- Create the users table
CREATE TABLE
    users (
        id SERIAL PRIMARY KEY,
        username VARCHAR(255) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL UNIQUE,
        role VARCHAR(255) NOT NULL DEFAULT 'USER'
    );

-- Create the items table
CREATE TABLE
    items (
        id SERIAL PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        description TEXT NOT NULL,
        status BOOLEAN NOT NULL,
        user_id BIGINT NOT NULL,
        FOREIGN KEY (user_id) REFERENCES users (id)
    );

-- Insert an admin user (example)
INSERT INTO
    users (username, password, email, role)
VALUES
    (
        'admin',
        '$2a$10$GWF7MmfXC122m.HLWwl.huX9UcZDhoqlqq/uoGDj7WIFvaDO6UIhy',
        'admin@example.com',
        'ADMIN'
    );

-- Insert a regular user (example)
INSERT INTO
    users (username, password, email, role)
VALUES
    (
        'user',
        '$2a$10$GWF7MmfXC122m.HLWwl.huX9UcZDhoqlqq/uoGDj7WIFvaDO6UIhy',
        'user@example.com',
        'USER'
    );

-- Insert example items (example)
INSERT INTO
    items (name, description, status, user_id)
VALUES
    ('Item 1', 'Description for item 1', true, 2),
    ('Item 2', 'Description for item 2', false, 2);