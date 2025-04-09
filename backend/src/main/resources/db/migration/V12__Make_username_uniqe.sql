-- Make the username column unique in the users table
ALTER TABLE users ADD CONSTRAINT unique_username UNIQUE (username);