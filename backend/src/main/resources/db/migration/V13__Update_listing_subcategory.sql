ALTER TABLE listings DROP COLUMN category;
ALTER TABLE listings ADD COLUMN category BIGINT;
ALTER TABLE listings DROP COLUMN subcategories;
ALTER TABLE listings ADD COLUMN subcategory BIGINT;