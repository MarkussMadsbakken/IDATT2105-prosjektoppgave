CREATE TABLE IF NOT EXISTS reservations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    listing_id VARCHAR(36) NOT NULL,
    buyer_id BIGINT NOT NULL,
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (listing_id) REFERENCES listings(uuid),
    FOREIGN KEY (buyer_id) REFERENCES users(id)
);