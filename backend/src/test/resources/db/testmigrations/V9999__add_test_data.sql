INSERT INTO users (username, password, first_name, last_name) VALUES (
 ('testuser1', 'password1', 'Test', 'User1'),
 ('testuser2', 'password2', 'Test', 'User2'),
 ('testuser3', 'password3', 'Test', 'User3')
);

INSERT INTO categories (name, description, icon) VALUES (
 ('Category1', 'Description1', 'Icon1'),
 ('Category2', 'Description2', 'Icon2'),
 ('Category3', 'Description3', 'Icon3')
);

INSERT INTO sub_categories (name, description, category_id) VALUES (
 ('SubCategory1', 'Description1', (SELECT id FROM categories WHERE name = 'Category1'),
 ('SubCategory2', 'Description2', (SELECT id FROM categories WHERE name = 'Category1')),
 ('SubCategory3', 'Description3', (SELECT id FROM categories WHERE name = 'Category2'))
);

INSERT INTO listings (
  uuid, 
  name, 
  price, 
  description, 
  category, 
  subcategory, 
  postal_code, 
  owner_id
) VALUES
  (
    '123e4567-e89b-12d3-a456-426614174000', 
    'Test Listing 1', 
    100.0, 
    'Description for test listing 1', 
    (SELECT id FROM categories WHERE name = 'Category1'), 
    (SELECT id FROM sub_categories WHERE name = 'SubCategory1'), 
    1234, 
    (SELECT id FROM users WHERE username = 'testuser1')
  ),(
    '123e4567-e89b-12d3-a456-426614174001', 
    'Test Listing 2', 
    200.0, 
    'Description for test listing 2', 
    (SELECT id FROM categories WHERE name = 'Category3'), 
    NULL, 
    5678, 
    (SELECT id FROM users WHERE username = 'testuser1')
  ), (
    '123e4567-e89b-12d3-a456-426614174002', 
    'Test Listing 3', 
    300.0, 
    'Description for test listing 3', 
    (SELECT id FROM categories WHERE name = 'Category2'), 
    (SELECT id FROM sub_categories WHERE name = 'SubCategory3'), 
    9101, 
    (SELECT id FROM users WHERE username = 'testuser2')
  )
);

INSERT INTO bookmarks (user_id, listing_id) VALUES (
  (SELECT id FROM users WHERE username = 'testuser1'), 
  (SELECT id FROM listings WHERE name = 'Test Listing 2')
), (
  (SELECT id FROM users WHERE username = 'testuser2'), 
  (SELECT id FROM listings WHERE name = 'Test Listing 1')
);

INSERT INTO notifications (user_id, url, message) VALUES (
  (SELECT id FROM users WHERE username = 'testuser1'),
  'http://example.com/listing/123e4567-e89b-12d3-a456-426614174000',
  'You have a new message regarding your listing.'
), (
  (SELECT id FROM users WHERE username = 'testuser2'),
  'http://example.com/listing/123e4567-e89b-12d3-a456-426614174001',
  'Your listing has been viewed.'
), (
  (SELECT id FROM users WHERE username = 'testuser3'),
  'http://example.com/listing/123e4567-e89b-12d3-a456-426614174002',
  'Your listing has been bookmarked.'
);
-- Note: The UUIDs in the listings table are just examples. In a real scenario, you would generate these UUIDs dynamically.

INSERT INTO user_history (user_id, listing_id) VALUES (
  (SELECT id FROM users WHERE username = 'testuser1'), 
  (SELECT uuid FROM listings WHERE name = 'Test Listing 1')
), (
  (SELECT id FROM users WHERE username = 'testuser1'), 
  (SELECT uuid FROM listings WHERE name = 'Test Listing 2')
), (
  (SELECT id FROM users WHERE username = 'testuser3'), 
  (SELECT uuid FROM listings WHERE name = 'Test Listing 3')
);

