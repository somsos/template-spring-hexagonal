--Notes
----1. For system users and roles start from -100, -99 and so on.

--Roles
INSERT INTO roles(id, authority) VALUES(-53, 'ROLE_registered');
INSERT INTO roles(id, authority) VALUES(-55, 'ROLE_admin_users');
INSERT INTO roles(id, authority) VALUES(-56, 'ROLE_admin_products');
INSERT INTO roles(id, authority) VALUES(-57, 'ROLE_admin_sells');


--Mario1
INSERT INTO users(id, username, password, created_at, email) VALUES(-100, 'mario1', '$2a$10$zyEAgLnFSxOzlww/V7DlNeWShEAwLp.fOo3Ds25nCrS5XyElb55Xm', now() - interval '100 day', 'mario1@email.com');
INSERT INTO users_roles(user_id, role_id) VALUES(-100, -55);
INSERT INTO users_roles(user_id, role_id) VALUES(-100, -56);

--Mario2
INSERT INTO users(id, username, password, created_at, email) VALUES(-99, 'mario2', '$2a$10$gSr8onLyPPNGe029IdJMPOD9lkv9q6kCfIcieOX4gSJ6rYIpW1rJi', now() - interval '99 day', 'mario2@email.com');
INSERT INTO users_roles(user_id, role_id) VALUES(-99, -56);

--Mario3
INSERT INTO users(id, username, password, created_at, email) VALUES(-98, 'mario3', '$2a$10$GF3mC0z3T6MkjAUoOxk04uwyvht0L457q71/lUM3f0F1tgxm.UNIS', now() - interval '98 day', 'mario3@email.com');
INSERT INTO users_roles(user_id, role_id) VALUES(-98, -57);


--Dummy Users (all password are mario3)
INSERT INTO users(id, username, password, created_at, email, updated_at) VALUES(1, 'john', '$2a$10$GF3mC0z3T6MkjAUoOxk04uwyvht0L457q71/lUM3f0F1tgxm.UNIS', now() - interval '1 day', 'john_doe@email.com', now() - interval '3 hours');
INSERT INTO users(id, username, password, created_at, email) VALUES(2, 'jane', '$2a$10$GF3mC0z3T6MkjAUoOxk04uwyvht0L457q71/lUM3f0F1tgxm.UNIS', now() - interval '2 days', 'jane_smith@email.com');
INSERT INTO users(id, username, password, created_at, email) VALUES(3, 'lucas', '$2a$10$GF3mC0z3T6MkjAUoOxk04uwyvht0L457q71/lUM3f0F1tgxm.UNIS', now() - interval '3 days', 'lucas_wilson@email.com');
INSERT INTO users(id, username, password, created_at, email) VALUES(4, 'emily', '$2a$10$GF3mC0z3T6MkjAUoOxk04uwyvht0L457q71/lUM3f0F1tgxm.UNIS', now() - interval '4 days', 'emily_jones@email.com');
INSERT INTO users(id, username, password, created_at, email) VALUES(5, 'david', '$2a$10$GF3mC0z3T6MkjAUoOxk04uwyvht0L457q71/lUM3f0F1tgxm.UNIS', now() - interval '5 days', 'david_brown@email.com');
INSERT INTO users(id, username, password, created_at, email) VALUES(6, 'susan', '$2a$10$GF3mC0z3T6MkjAUoOxk04uwyvht0L457q71/lUM3f0F1tgxm.UNIS', now() - interval '6 days', 'susan_clark@email.com');
INSERT INTO users(id, username, password, created_at, email) VALUES(7, 'michael', '$2a$10$GF3mC0z3T6MkjAUoOxk04uwyvht0L457q71/lUM3f0F1tgxm.UNIS', now() - interval '7 days', 'michael_lee@email.com');
INSERT INTO users(id, username, password, created_at, email) VALUES(8, 'lily', '$2a$10$GF3mC0z3T6MkjAUoOxk04uwyvht0L457q71/lUM3f0F1tgxm.UNIS', now() - interval '8 days', 'lily_martin@email.com');
INSERT INTO users(id, username, password, created_at, email) VALUES(9, 'alex', '$2a$10$GF3mC0z3T6MkjAUoOxk04uwyvht0L457q71/lUM3f0F1tgxm.UNIS', now() - interval '9 days', 'alex_taylor@email.com');
INSERT INTO users(id, username, password, created_at, email) VALUES(10, 'emily3', '$2a$10$GF3mC0z3T6MkjAUoOxk04uwyvht0L457q71/lUM3f0F1tgxm.UNIS', now(), 'emily3@email.com');
INSERT INTO users(id, username, password, created_at, email) VALUES(11, 'mike4', '$2a$10$GF3mC0z3T6MkjAUoOxk04uwyvht0L457q71/lUM3f0F1tgxm.UNIS', now() - interval '1 day 10 minutes', 'mike4@email.com');
INSERT INTO users(id, username, password, created_at, email) VALUES(12, 'anna5', '$2a$10$GF3mC0z3T6MkjAUoOxk04uwyvht0L457q71/lUM3f0F1tgxm.UNIS', now() - interval '2 days 20 minutes', 'anna5@email.com');
INSERT INTO users(id, username, password, created_at, email) VALUES(13, 'paul6', '$2a$10$GF3mC0z3T6MkjAUoOxk04uwyvht0L457q71/lUM3f0F1tgxm.UNIS', now() - interval '3 days 30 minutes', 'paul6@email.com');
INSERT INTO users(id, username, password, created_at, email) VALUES(14, 'zoe7', '$2a$10$GF3mC0z3T6MkjAUoOxk04uwyvht0L457q71/lUM3f0F1tgxm.UNIS', now() - interval '4 days 40 minutes', 'zoe7@email.com');
INSERT INTO users(id, username, password, created_at, email) VALUES(15, 'john8', '$2a$10$GF3mC0z3T6MkjAUoOxk04uwyvht0L457q71/lUM3f0F1tgxm.UNIS', now() - interval '5 days 50 minutes', 'john8@email.com');
INSERT INTO users(id, username, password, created_at, email) VALUES(16, 'lucy9', '$2a$10$GF3mC0z3T6MkjAUoOxk04uwyvht0L457q71/lUM3f0F1tgxm.UNIS', now() - interval '6 days 60 minutes', 'lucy9@email.com');
INSERT INTO users(id, username, password, created_at, email) VALUES(17, 'tom10', '$2a$10$GF3mC0z3T6MkjAUoOxk04uwyvht0L457q71/lUM3f0F1tgxm.UNIS', now() - interval '7 days 70 minutes', 'tom10@email.com');
INSERT INTO users(id, username, password, created_at, email) VALUES(18, 'sara11', '$2a$10$GF3mC0z3T6MkjAUoOxk04uwyvht0L457q71/lUM3f0F1tgxm.UNIS', now() - interval '8 days 80 minutes', 'sara11@email.com');
INSERT INTO users(id, username, password, created_at, email) VALUES(19, 'mark12', '$2a$10$GF3mC0z3T6MkjAUoOxk04uwyvht0L457q71/lUM3f0F1tgxm.UNIS', now() - interval '9 days 90 minutes', 'mark12@email.com');

--Products

INSERT INTO products(id, name, price, amount, description, created_at) VALUES(1, 'Ceramic Mug', 11.99, 50, 'A stylish ceramic mug for your favorite beverages.', '2024-01-10');
INSERT INTO products(id, name, price, amount, description, created_at) VALUES(2, 'Stainless Steel Water Bottle', 15.50, 30, 'Keep your drinks cold or hot with this durable water bottle.', '2024-01-11');
INSERT INTO products(id, name, price, amount, description, created_at) VALUES(3, 'Bluetooth Speaker', 29.99, 20, 'Portable speaker with excellent sound quality.', '2024-01-12');
INSERT INTO products(id, name, price, amount, description, created_at) VALUES(4, 'Yoga Mat', 24.99, 40, 'Eco-friendly yoga mat for your fitness routine.', '2024-01-13');
INSERT INTO products(id, name, price, amount, description, created_at) VALUES(5, 'Desk Organizer', 19.99, 15, 'Keep your workspace tidy with this desk organizer.', '2024-01-14');
INSERT INTO products(id, name, price, amount, description, created_at) VALUES(6, 'Wireless Earbuds', 49.99, 25, 'Experience true wireless freedom with these earbuds.', '2024-01-15');
INSERT INTO products(id, name, price, amount, description, created_at) VALUES(7, 'Backpack', 39.99, 100, 'Spacious and stylish backpack for everyday use.', '2024-01-16');
INSERT INTO products(id, name, price, amount, description, created_at) VALUES(8, 'LED Desk Lamp', 34.99, 35, 'Brighten your workspace with this adjustable desk lamp.', '2024-01-17');
INSERT INTO products(id, name, price, amount, description, created_at) VALUES(9, 'Notebooks Set', 12.50, 60, 'A set of colorful notebooks for school or office use.', '2024-01-18');
INSERT INTO products(id, name, price, amount, description, created_at) VALUES(10, 'Cooking Utensils Set', 19.99, 15, 'Essential kitchen tools for every home chef.', '2024-01-19');
INSERT INTO products(id, name, price, amount, description, created_at) VALUES(11, 'Wall Art Print', 25.00, 71, 'Beautiful art print to decorate your space.', '2024-01-20');
INSERT INTO products(id, name, price, amount, description, created_at) VALUES(12, 'Portable Charger', 29.99, 22, 'Never run out of battery with this portable charger.', '2024-01-21');
INSERT INTO products(id, name, price, amount, description, created_at) VALUES(13, 'Fitness Tracker', 49.99, 40, 'Monitor your health and fitness with this tracker.', '2024-01-22');
INSERT INTO products(id, name, price, amount, description, created_at) VALUES(14, 'Coffee Maker', 89.99, 60, 'Brew your perfect cup of coffee every morning.', '2024-01-23');
INSERT INTO products(id, name, price, amount, description, created_at) VALUES(15, 'Smartphone Stand', 14.99, 18, 'Adjustable stand for your smartphone or tablet.', '2024-01-24');
INSERT INTO products(id, name, price, amount, description, created_at) VALUES(16, 'Pet Bed', 34.99, 10, 'Cozy bed for your furry friend.', '2024-01-25');
INSERT INTO products(id, name, price, amount, description, created_at) VALUES(17, 'Potted Plant', 16.99, 12, 'Bring life to your home with this lovely potted plant.', '2024-01-26');
INSERT INTO products(id, name, price, amount, description, created_at) VALUES(18, 'Travel Pillow', 19.99, 25, 'Comfortable pillow for your travels.', '2024-01-27');
INSERT INTO products(id, name, price, amount, description, created_at) VALUES(19, 'Skincare Set', 39.99, 35, 'Complete set for glowing skin.', '2024-01-28');
INSERT INTO products(id, name, price, amount, description, created_at) VALUES(20, 'Board Game', 29.99, 15, 'Fun board game for family and friends.', '2024-01-29');
INSERT INTO products(id, name, price, amount, description, created_at, deleted_at) VALUES(21, 'Game 2', 70.50, 15, 'for fun and more.', '2024-03-21', '2024-03-22');

SELECT setval('products_id_seq', 30, true);


--Product images
INSERT INTO product_images(id, id_product, name, type, created_at, image) VALUES(1, 1, 'image1.png', 'image/png', '2024-01-11 09:57:00', '\x78daeb0cf073e7e592e2626060e0f5f4700902d20240ccc9c10624adb5d6180129c6e22077278675e7645e02392ce98ebe8e0c0c1bfbb9ff24b28254167844163330f01d026146510daf06a0a0bea78b63884670724a54cdfffffbffcfe159c224b66c3b4ffd3f4e6383898b25591c66393670fa0431b8f14a54304b483084b473afab17b588006a65f074f57359e794d004002a1b2662');
INSERT INTO product_images(id, id_product, name, type, created_at, image) VALUES(2, 1, 'image2.png', 'image/png', '2024-01-22 09:57:00', '\x78daeb0cf073e7e592e2626060e0f5f4700902d20240ccc9c10624adb5d6180129c6e22077278675e7645e02392ce98ebe8e0c0c1bfbb9ff24b28254167844163330f01d026146510daf06a0a0b2a78b638846707248d4c39ff31b27cf6c8c39e0d2992690d07429a5399099ed1e031b1bc3ff50269b3b967edf81ca193c5dfd5cd639253401007fc024c8');
INSERT INTO product_images(id, id_product, name, type, created_at, image) VALUES(3, 1, 'image3.png', 'image/png', '2024-02-25 09:57:00', '\x78daeb0cf073e7e592e2626060e0f5f4700902d20240ccc9c10624adb5d6180129c6e22077278675e7645e02392ce98ebe8e0c0c1bfbb9ff24b28254167844163330f01d0161c6a9da7cd24041194f17c7108de0e43fff3b1fda33b336707a4de675e39560633330606038769499e9f251dd4d40750c9eae7e2eeb9c129a001ea61f25');
INSERT INTO product_images(id, id_product, name, type, created_at, image) VALUES(4, 1, 'image4.png', 'image/png', '2024-03-10 09:57:00', '\x78daeb0cf073e7e592e2626060e0f5f4700902d20240ccc9c10624adb5d6180129c6e22077278675e7645e02392ce98ebe8e0c0c1bfbb9ff24b28254167844163330f01d026146510daf06a0a08ca78b63884670724a42c20ffe2329cc6a1abe6612a6091212070e3030446e6614d42fd74d06aa63f074f57359e794d0040003f91e1a');

--2
INSERT INTO product_images(id, id_product, name, type, created_at, image) VALUES(5, 2, 'image5.png', 'image/png', '2024-04-10 09:57:00', '\x78daeb0cf073e7e592e2626060e0f5f4700902d20240ccc9c10624adb5d6180129c6e22077278675e7645e02392ce98ebe8e0c0c1bfbb9ff24b28254167844163330f01d026146510daf06a0a0b2a78b638846707248d4c39ff31b27cf6c8c39e0d2992690d07429a5399099ed1e031b1bc3ff50269b3b967edf81ca193c5dfd5cd639253401007fc024c8');
INSERT INTO product_images(id, id_product, name, type, created_at, image) VALUES(6, 2, 'image6.png', 'image/png', '2024-05-12 09:57:00', '\x78daeb0cf073e7e592e2626060e0f5f4700902d20240ccc9c10624adb5d6180129c6e22077278675e7645e02392ce98ebe8e0c0c1bfbb9ff24b28254167844163330f01d026146510daf06a0a0bea78b63884670724a54cdfffffbffcfe159c224b66c3b4ffd3f4e6383898b25591c66393670fa0431b8f14a54304b483084b473afab17b588006a65f074f57359e794d004002a1b2662');

--3
INSERT INTO product_images(id, id_product, name, type, created_at, deleted_at, image) VALUES(7, 3, 'image7.png', 'image/png', '2024-01-10 09:57:00', '2024-03-22 09:57:00', '\x78daeb0cf073e7e592e2626060e0f5f4700902d20240ccc9c10624adb5d6180129c6e22077278675e7645e02392ce98ebe8e0c0c1bfbb9ff24b28254167844163330f01d026146510daf06a0a0bea78b63884670724a54cdfffffbffcfe159c224b66c3b4ffd3f4e6383898b25591c66393670fa0431b8f14a54304b483084b473afab17b588006a65f074f57359e794d004002a1b2662');
SELECT setval('product_images_id_seq', 30, true);