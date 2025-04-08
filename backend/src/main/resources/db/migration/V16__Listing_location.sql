ALTER TABLE listings
DROP COLUMN postal_code,
ADD COLUMN longitude DOUBLE,
ADD COLUMN latitude DOUBLE;