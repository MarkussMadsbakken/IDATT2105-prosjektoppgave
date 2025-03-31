CREATE TABLE listings (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         price DOUBLE NOT NULL,
                         description TEXT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         pictures BLOB,
                         category VARCHAR(255),
                         subcategories JSON,
                         postal_code INT,
                         active BOOLEAN DEFAULT TRUE,
                         deleted BOOLEAN DEFAULT FALSE,
                         sold BOOLEAN DEFAULT FALSE,
                         owner_id BIGINT NOT NULL,
                         CONSTRAINT fk_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);