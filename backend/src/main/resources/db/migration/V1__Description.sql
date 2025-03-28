CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       first_name VARCHAR(50),
                       last_name VARCHAR(50),
                       created_at TIMESTAMP,
                       is_admin BOOLEAN
);