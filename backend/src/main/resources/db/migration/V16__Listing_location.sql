ALTER TABLE listings DROP COLUMN postal_code;
ALTER TABLE listings ADD COLUMN longitude DOUBLE;
ALTER TABLE listings ADD COLUMN latitude DOUBLE;