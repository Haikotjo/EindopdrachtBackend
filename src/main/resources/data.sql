INSERT INTO ingredients (name, quantity) VALUES ('Sugar', 100);
INSERT INTO ingredients (name, quantity) VALUES ('Flour', 50);
INSERT INTO ingredients (name, quantity) VALUES ('Butter', 30);


INSERT INTO menu_items (name, price, description, image) VALUES ('Cheese Pizza', 10.00, 'Cheese pizza with extra cheese topping', 'cheese_pizza.jpg');
INSERT INTO menu_items (name, price, description, image) VALUES ('Veggie Pizza', 12.00, 'Pizza with a variety of vegetables', 'veggie_pizza.jpg');

INSERT INTO menu_item_ingredients (menu_item_id, ingredient_id) VALUES (1, 1); -- Cheese Pizza heeft Sugar
INSERT INTO menu_item_ingredients (menu_item_id, ingredient_id) VALUES (1, 3); -- Cheese Pizza heeft Butter
INSERT INTO menu_item_ingredients (menu_item_id, ingredient_id) VALUES (2, 2); -- Veggie Pizza heeft Flour
INSERT INTO menu_item_ingredients (menu_item_id, ingredient_id) VALUES (2, 3); -- Veggie Pizza heeft Butter

