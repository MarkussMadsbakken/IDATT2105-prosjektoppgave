CREATE TABLE IF NOT EXISTS user_history (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  listing_id CHAR(36) NOT NULL,
  timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_user_history FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT fk_listing_history FOREIGN KEY (listing_id) REFERENCES listings(uuid)
);