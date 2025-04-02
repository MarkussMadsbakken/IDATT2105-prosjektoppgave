CREATE TABLE listing_images (
    uuid VARCHAR(36) PRIMARY KEY,
    listing_uuid VARCHAR(36) NOT NULL,
    image_blob BLOB NOT NULL,
    FOREIGN KEY (listing_uuid) REFERENCES listings(uuid)
);