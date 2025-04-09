ALTER TABLE listings
ADD CONSTRAINT fk_listing_category FOREIGN KEY (category) REFERENCES categories(id) ON DELETE SET NULL,
ADD CONSTRAINT fk_listing_subcategory FOREIGN KEY (subcategory) REFERENCES sub_categories(id) ON DELETE SET NULL;