-- Create users table
CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  image_blob LONGBLOB,
  image_file_type VARCHAR(16)
);

CREATE TABLE listings (
  uuid CHAR(36) NOT NULL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  price DOUBLE NOT NULL,
  description TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  category BIGINT,
  subcategory BIGINT,
  postal_code INT,
  active BOOLEAN DEFAULT TRUE,
  deleted BOOLEAN DEFAULT FALSE,
  sold BOOLEAN DEFAULT FALSE,
  owner_id BIGINT NOT NULL,
  CONSTRAINT fk_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);

-- Insert a user
INSERT INTO users (username, password) VALUES ('testuser', 'password');

-- Insert a listing
INSERT INTO listings (
    uuid, name, price, description, category, subcategory, postal_code, active, deleted, sold, owner_id
) VALUES (
 '123e4567-e89b-12d3-a456-426614174000', 'Test Listing', 100.0, 'Test listing description', 1, 1, 1234, TRUE, FALSE, FALSE, 1
);