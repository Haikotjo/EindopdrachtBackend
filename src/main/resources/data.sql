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
