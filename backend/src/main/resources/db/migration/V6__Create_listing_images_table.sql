CREATE TABLE IF NOT EXISTS listing_images (
    uuid VARCHAR(36) PRIMARY KEY,
    listing_uuid VARCHAR(36),
    image_blob LONGBLOB NOT NULL,
    image_format VARCHAR(16) NOT NULL,
    FOREIGN KEY (listing_uuid) REFERENCES listings(uuid) ON DELETE CASCADE
);