CREATE TABLE IF NOT EXISTS bookmarks (
  user_id BIGINT NOT NULL,
  listing_id CHAR(36) NOT NULL,
  PRIMARY KEY (user_id, listing_id)
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT fk_listing FOREIGN KEY (listing_id) REFERENCES listings(uuid),
);