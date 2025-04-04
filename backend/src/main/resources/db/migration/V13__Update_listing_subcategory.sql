ALTER TABLE listings
DROP COLUMN category,
ADD COLUMN category BIGINT,
DROP COLUMN subcategories,
ADD COLUMN subcategory BIGINT;