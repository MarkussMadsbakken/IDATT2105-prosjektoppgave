-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_admin BOOLEAN,
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
  longitude DOUBLE,
  latitude DOUBLE,
  active BOOLEAN DEFAULT TRUE,
  deleted BOOLEAN DEFAULT FALSE,
  sold BOOLEAN DEFAULT FALSE,
  owner_id BIGINT NOT NULL,
  buyer_id BIGINT,
  CONSTRAINT fk_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE bookmarks (
     user_id BIGINT NOT NULL,
     listing_id CHAR(36) NOT NULL,
     PRIMARY KEY (user_id, listing_id),
     CONSTRAINT fk_user_bookmarks FOREIGN KEY (user_id) REFERENCES users(id),
     CONSTRAINT fk_listing_bookmarks FOREIGN KEY (listing_id) REFERENCES listings(uuid)
);

CREATE TABLE listing_images (
      uuid VARCHAR(36) PRIMARY KEY,
      listing_uuid VARCHAR(36),
      image_blob LONGBLOB NOT NULL,
      image_format VARCHAR(16) NOT NULL,
      FOREIGN KEY (listing_uuid) REFERENCES listings(uuid) ON DELETE CASCADE
);

CREATE TABLE chat (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    listing_id VARCHAR(36) NOT NULL,
    seller_id BIGINT NOT NULL,
    buyer_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (listing_id) REFERENCES listings(uuid),
    FOREIGN KEY (seller_id) REFERENCES users(id),
    FOREIGN KEY (buyer_id) REFERENCES users(id)
);

CREATE TABLE message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    chat_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (chat_id) REFERENCES chat(id),
    FOREIGN KEY (sender_id) REFERENCES users(id)
);

CREATE TABLE categories (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE,
  description VARCHAR(255),
  icon TEXT
);

CREATE TABLE sub_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    category_id BIGINT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

CREATE TABLE notifications (
     id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     user_id BIGINT NOT NULL,
     is_read BOOLEAN DEFAULT FALSE,
     timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     url TEXT NOT NULL,
     message TEXT NOT NULL,
     CONSTRAINT fk_user_notifications FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE user_history (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    listing_id CHAR(36) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_history FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_listing_history FOREIGN KEY (listing_id) REFERENCES listings(uuid)
);

CREATE TABLE reservations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    listing_id VARCHAR(36) NOT NULL,
    user_id BIGINT NOT NULL,
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (listing_id) REFERENCES listings(uuid),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Insert a user
INSERT INTO users (username, password) VALUES ('testuser', 'password');
INSERT INTO users (username, password) VALUES ('testuser2', 'password');