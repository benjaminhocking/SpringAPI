-- This script creates a user table and inserts a test user.
-- Create the table if it does not exist
CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT PRIMARY KEY,
                                     name VARCHAR(255),
    age INT,
    email VARCHAR(255)
    );

-- Insert a test user
INSERT INTO users (id, name, age, email) VALUES (1, 'John Doe', 30, 'john.doe@example.com');