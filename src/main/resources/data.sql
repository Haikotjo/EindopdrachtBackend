-- Insert ingredients
INSERT INTO ingredients (name, quantity) VALUES ('Sugar', 100);
INSERT INTO ingredients (name, quantity) VALUES ('Flour', 50);
INSERT INTO ingredients (name, quantity) VALUES ('Butter', 30);

-- Insert menu items including new ones for Spaghetti Bolognese and Vegetable Lasagna
INSERT INTO menu_items (name, price, description, image) VALUES ('Cheese Pizza', 10.00, 'Cheese pizza with extra cheese topping', 'cheese_pizza.jpg');
INSERT INTO menu_items (name, price, description, image) VALUES ('Veggie Pizza', 12.00, 'Pizza with a variety of vegetables', 'veggie_pizza.jpg');
INSERT INTO menu_items (name, price, description, image) VALUES ('Spaghetti Bolognese', 15.00, 'Classic spaghetti with homemade bolognese sauce', 'spaghetti_bolognese.jpg');
INSERT INTO menu_items (name, price, description, image) VALUES ('Vegetable Lasagna', 14.00, 'Layers of pasta, fresh veggies, and rich tomato sauce', 'vegetable_lasagna.jpg');

-- Associate ingredients with menu items
INSERT INTO menu_item_ingredients (menu_item_id, ingredient_id) VALUES (1, 1); -- Cheese Pizza heeft Sugar
INSERT INTO menu_item_ingredients (menu_item_id, ingredient_id) VALUES (1, 3); -- Cheese Pizza heeft Butter
INSERT INTO menu_item_ingredients (menu_item_id, ingredient_id) VALUES (2, 2); -- Veggie Pizza heeft Flour
INSERT INTO menu_item_ingredients (menu_item_id, ingredient_id) VALUES (2, 3); -- Veggie Pizza heeft Butter

-- Insert some menus
INSERT INTO menus (name, description) VALUES ('Italian Specials', 'A selection of Italian cuisine favorites');
INSERT INTO menus (name, description) VALUES ('Vegetarian Delights', 'A variety of healthy vegetarian dishes');

-- Associate menu_items with menus
INSERT INTO menu_menu_item (menu_id, menu_item_id) VALUES (1, 1); -- Cheese Pizza in Italian Specials
INSERT INTO menu_menu_item (menu_id, menu_item_id) VALUES (1, 3); -- Spaghetti Bolognese in Italian Specials
INSERT INTO menu_menu_item (menu_id, menu_item_id) VALUES (2, 2); -- Veggie Pizza in Vegetarian Delights
INSERT INTO menu_menu_item (menu_id, menu_item_id) VALUES (2, 4); -- Vegetable Lasagna in Vegetarian Delights

-- Insert roles
INSERT INTO roles (rolename) VALUES ('CUSTOMER');
INSERT INTO roles (rolename) VALUES ('OWNER');
INSERT INTO roles (rolename) VALUES ('ADMIN');

-- Insert users
INSERT INTO users (name, email, password, phone_number) VALUES ('John Doe', 'john.doe@example.com', '$2a$10$l8Aw3v4sJpSHWUjtXUdkO.v766JvM6Ro9vrQ3IogQOU1U88Tu46ky', '1234567890');
INSERT INTO users (name, email, password, phone_number) VALUES ('Jane Smith', 'jane.smith@example.com', '$2a$10$K6pbGwTYnNmWSM1kiLfJvOxbEnxy7K1hCAi0rIJ9d3OzICW3sUI1y', '0987654321');
INSERT INTO users (name, email, password, phone_number) VALUES ('Alice Johnson', 'alice.johnson@example.com', '$2a$10$HS5wLdrmMRuBgF9d18Tbnux/Lqe4Zu25xc87qlSyLTqmlzrqGgXS2', '1122334455');
INSERT INTO users (name, email, password, phone_number) VALUES ('Owner One', 'owner.one@example.com', '$2a$10$2q1rPfRqfs8braxU9c3WzeihkPD4jvILzFtWgeOiR3OnnkO4B9HWe', '1111111111');
INSERT INTO users (name, email, password, phone_number) VALUES ('Owner Two', 'owner.two@example.com', '$2a$10$KTQEBBo.dZXZL6USfu/qV.wxt./HSi09KCB5crxQFAvpLCCwnzW7O', '2222222222');
INSERT INTO users (name, email, password, phone_number) VALUES ('Owner Three', 'owner.three@example.com', '$2a$10$tMOde/XYW82ohctp6jkHjOMQQ3repa3vupD20/WfO2O94X6mv8Dfe', '3333333333');
INSERT INTO users (name, email, password, phone_number) VALUES ('Admin User', 'admin.user@example.com', '$2a$10$5CVbOzUidUJMUYJkdlOMXuIggBw5/61Fa1dzM5CEB/8fs2BTPnCfO', '555-9999');

-- Associate users with roles
INSERT INTO user_roles (user_id, roles_rolename) VALUES ((SELECT id FROM users WHERE email='john.doe@example.com'), 'CUSTOMER');
INSERT INTO user_roles (user_id, roles_rolename) VALUES ((SELECT id FROM users WHERE email='jane.smith@example.com'), 'CUSTOMER');
INSERT INTO user_roles (user_id, roles_rolename) VALUES ((SELECT id FROM users WHERE email='alice.johnson@example.com'), 'CUSTOMER');

INSERT INTO user_roles (user_id, roles_rolename) VALUES ((SELECT id FROM users WHERE email='owner.one@example.com'), 'OWNER');
INSERT INTO user_roles (user_id, roles_rolename) VALUES ((SELECT id FROM users WHERE email='owner.two@example.com'), 'OWNER');
INSERT INTO user_roles (user_id, roles_rolename) VALUES ((SELECT id FROM users WHERE email='owner.three@example.com'), 'OWNER');

INSERT INTO user_roles (user_id, roles_rolename) VALUES ((SELECT id FROM users WHERE email='admin.user@example.com'), 'ADMIN');


-- Insert delivery addresses ensuring correct alignment with user IDs and table fields
INSERT INTO delivery_addresses (street, house_number, city, postcode, country, user_id) VALUES ('Maple Street', 123, 'Springfield', '12345', 'USA', 1);
INSERT INTO delivery_addresses (street, house_number, city, postcode, country, user_id) VALUES ('Elm Street', 456, 'Shelbyville', '67890', 'USA', 2);

-- Insert restaurants
INSERT INTO restaurants (name, address, phone_number, user_id) VALUES ('Italian Bistro', '123 Main Street, Springfield', '555-1234', (SELECT id FROM users WHERE email='owner.one@example.com'));
INSERT INTO restaurants (name, address, phone_number, user_id) VALUES ('Veggie Delight', '456 Oak Avenue, Shelbyville', '555-5678', (SELECT id FROM users WHERE email='owner.two@example.com'));
INSERT INTO restaurants (name, address, phone_number, user_id) VALUES ('Sushi Place', '789 Maple Road, Capital City', '555-8765', (SELECT id FROM users WHERE email='owner.three@example.com'));

-- Associate menus with restaurants
UPDATE menus SET restaurant_id = 1 WHERE id = 1; -- Italian Specials hoort bij Italian Bistro
UPDATE menus SET restaurant_id = 2 WHERE id = 2; -- Vegetarian Delights hoort bij Veggie Delight

-- Insert orders with orderDateTime
INSERT INTO orders (fulfilled, customer_id, restaurant_id, delivery_address_id, order_date_time) VALUES (true, 1, 1, 1, '2024-06-02T10:15:30'); -- Order 1, fulfilled, by John Doe from Italian Bistro to Maple Street
INSERT INTO orders (fulfilled, customer_id, restaurant_id, delivery_address_id, order_date_time) VALUES (false, 2, 2, 2, '2024-06-01T09:20:25'); -- Order 2, not fulfilled, by Jane Smith from Veggie Delight to Elm Street
INSERT INTO orders (fulfilled, customer_id, restaurant_id, delivery_address_id, order_date_time) VALUES (true, 1, 1, 1, '2024-06-02T14:45:00'); -- Order 3, fulfilled, by John Doe from Italian Bistro to Maple Street

-- Associate menu_items with orders
INSERT INTO order_menu_items (order_id, menu_item_id) VALUES (1, 1); -- Order 1 heeft Cheese Pizza
INSERT INTO order_menu_items (order_id, menu_item_id) VALUES (1, 3); -- Order 1 heeft Spaghetti Bolognese
INSERT INTO order_menu_items (order_id, menu_item_id) VALUES (2, 2); -- Order 2 heeft Veggie Pizza
INSERT INTO order_menu_items (order_id, menu_item_id) VALUES (2, 4); -- Order 2 heeft Vegetable Lasagna
INSERT INTO order_menu_items (order_id, menu_item_id) VALUES (3, 1); -- Order 3 heeft Cheese Pizza
INSERT INTO order_menu_items (order_id, menu_item_id) VALUES (3, 4); -- Order 3 heeft Vegetable Lasagna
