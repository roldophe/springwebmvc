-- ----------------------------
-- Table: Categories
-- ----------------------------
CREATE TABLE IF NOT EXISTS categories (
  id SERIAL PRIMARY KEY,
  name VARCHAR(60) UNIQUE NOT NULL,
  description VARCHAR(255)
);

-- ----------------------------
-- Table: Suppliers
-- ----------------------------
CREATE TABLE IF NOT EXISTS suppliers (
 id SERIAL PRIMARY KEY,
 company VARCHAR(100) UNIQUE NOT NULL,
 since DATE,
 status BOOLEAN
);

-- ----------------------------
-- Table: Products
-- ----------------------------
CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    slug VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    price DECIMAL(10,2) NOT NULL,
    in_stock BOOLEAN DEFAULT true,
    supplier_id INT,
    CONSTRAINT fk_supplier
        FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
            ON DELETE SET NULL
);

-- ----------------------------
-- Table: Product-Categories (Many-to-Many)
-- ----------------------------
CREATE TABLE IF NOT EXISTS product_categories (
  product_id INT NOT NULL,
  category_id INT NOT NULL,
  PRIMARY KEY (product_id, category_id),
  CONSTRAINT fk_pc_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
  CONSTRAINT fk_pc_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

-- ----------------------------
-- Seed: Categories
-- ----------------------------
INSERT INTO categories (name, description) VALUES
   ('Electronics', 'Phones, TVs, gadgets'),
   ('Home Appliances', 'Kitchen & cleaning devices'),
   ('Fashion', 'Clothes and accessories'),
   ('Books', 'Fiction, non-fiction, textbooks, and more'),
   ('Toys & Games', 'Entertainment for all ages'),
   ('Sports & Outdoors', 'Athletic equipment and outdoor recreation'),
   ('Beauty & Personal Care', 'Cosmetics, skincare, and grooming products'),
   ('Furniture', 'Home and office furnishings'),
   ('Automotive', 'Car parts, accessories, and tools'),
   ('Pet Supplies', 'Food, toys, and accessories for pets');

-- ----------------------------
-- Seed: Suppliers
-- ----------------------------
INSERT INTO suppliers (company, since, status) VALUES
   ('TechVision Corp', '2020-01-15', true),
   ('HomeStyle Inc', '2018-07-22', true),
   ('FashionForward Ltd', '2019-03-10', true),
   ('BookWorld Publishers', '2015-05-12', true),
   ('ToyLand Enterprises', '2019-09-30', true),
   ('SportsMaster Co.', '2017-03-18', true),
   ('GlamBeauty Inc', '2021-02-14', true),
   ('FurniCraft Designs', '2016-11-05', true),
   ('AutoParts Unlimited', '2018-08-22', true),
   ('PetCare Solutions', '2020-07-19', true);

-- ----------------------------
-- Seed: Sample Products
-- ----------------------------
INSERT INTO products (slug, name, description, price, in_stock, supplier_id) VALUES
    ('smartphone-x10', 'Smartphone X10', 'Flagship phone', 899.00, true, 1),
    ('smart-tv-55', 'Smart TV 55"', '4K UHD television', 1299.00, true, 1),
    ('denim-jacket', 'Denim Jacket', 'Classic unisex jacket', 59.00, true, 3);

-- ----------------------------
-- Seed: Product-Category Relationships (Initial)
-- ----------------------------
INSERT INTO product_categories (product_id, category_id) VALUES
     (1, 1),         -- Smartphone X10 → Electronics
     (2, 1), (2, 2), -- Smart TV → Electronics + Home Appliances
     (3, 3);         -- Denim Jacket → Fashion

-- ----------------------------
-- Seed: Specialized Products
-- ----------------------------
-- Books (Supplier ID 4)
INSERT INTO products (slug, name, description, price, in_stock, supplier_id) VALUES
 ('fantasy-novel-series', 'Epic Fantasy Series Box Set', 'Complete 5-book fantasy series with illustrated map', 149.00, true, 4),
 ('cookbook-international', 'International Cuisine Cookbook', 'Collection of recipes from around the world', 89.00, true, 4),
 ('science-textbook', 'Modern Physics Textbook', 'University-level physics with practice problems', 299.00, false, 4),
 ('self-help-mindfulness', 'Mindfulness for Beginners', 'Guide to meditation and stress relief', 59.00, true, 4),
 ('business-leadership', 'Leadership Strategies', 'Business management and team leadership techniques', 129.00, true, 4),

-- Toys & Games (Supplier ID 5)
 ('building-blocks-set', 'Creative Building Blocks 500pc', 'Educational building blocks compatible with major brands', 89.00, true, 5),
 ('board-game-strategy', 'Strategic Conquest Board Game', 'Family strategy game for 2-6 players', 79.00, true, 5),
 ('remote-control-car', 'Off-Road RC Car', 'Remote control car with all-terrain capabilities', 199.00, true, 5),
 ('plush-animal-collection', 'Plush Animal Set', 'Set of 5 cuddly and soft stuffed animals', 69.00, false, 5),
 ('educational-robot-kit', 'Programmable Robot Kit', 'STEM learning robot kit with coding tutorials', 299.00, true, 5),

-- Sports & Outdoors (Supplier ID 6)
 ('yoga-mat-premium', 'Premium Yoga Mat', 'Non-slip, eco-friendly yoga mat with carrying strap', 79.00, true, 6),
 ('camping-tent-4person', '4-Person Camping Tent', 'Waterproof tent with fast setup system', 249.00, true, 6),
 ('fitness-tracker-sport', 'Sport Fitness Tracker', 'Water-resistant fitness tracker with heart monitor', 149.00, false, 6),
 ('basketball-official', 'Official Basketball', 'Regulation size and weight basketball', 59.00, true, 6),
 ('hiking-backpack-trail', 'Trail Hiking Backpack', '35L backpack with hydration system', 179.00, true, 6),

-- Beauty & Personal Care (Supplier ID 7)
 ('skincare-set-complete', 'Complete Skincare Set', 'Five-step skincare routine products', 129.00, true, 7),
 ('hair-dryer-professional', 'Professional Hair Dryer', 'Salon-quality dryer with diffuser attachment', 149.00, true, 7),
 ('makeup-palette-deluxe', 'Deluxe Makeup Palette', 'All-in-one eyeshadow and blush palette', 99.00, false, 7),
 ('electric-razor-premium', 'Premium Electric Razor', 'Rechargeable razor with multiple attachments', 119.00, true, 7),
 ('perfume-signature', 'Signature Fragrance', 'Luxury perfume with lasting scent', 199.00, true, 7),

-- Furniture (Supplier ID 8)
 ('office-chair-ergonomic', 'Ergonomic Office Chair', 'Adjustable office chair with lumbar support', 299.00, true, 8),
 ('coffee-table-modern', 'Modern Coffee Table', 'Sleek design coffee table with storage', 199.00, false, 8),
 ('bookshelf-wooden', 'Wooden Bookshelf', '5-tier wooden bookshelf with adjustable shelves', 249.00, true, 8),
 ('bed-frame-queen', 'Queen Size Bed Frame', 'Sturdy bed frame with headboard', 399.00, true, 8),
 ('dining-table-set', 'Dining Table Set', 'Table with 4 chairs in contemporary style', 599.00, true, 8),

-- Automotive (Supplier ID 9)
 ('car-battery-premium', 'Premium Car Battery', 'Long-lasting car battery with 3-year warranty', 149.00, true, 9),
 ('dash-camera-hd', 'HD Dash Camera', 'Wide-angle dashboard camera with night vision', 99.00, false, 9),
 ('floor-mats-all-weather', 'All-Weather Floor Mats', 'Custom-fit car floor mats, set of 4', 79.00, true, 9),
 ('tool-kit-automotive', 'Automotive Tool Kit', '65-piece tool set for car maintenance', 199.00, true, 9),
 ('car-seat-covers', 'Deluxe Car Seat Covers', 'Full set of seat covers with premium fabric', 129.00, true, 9),

-- Pet Supplies (Supplier ID 10)
 ('dog-bed-orthopedic', 'Orthopedic Dog Bed', 'Memory foam bed for medium to large dogs', 89.00, true, 10),
 ('cat-tree-multilevel', 'Multi-level Cat Tree', 'Cat climbing tree with scratching posts', 149.00, false, 10),
 ('pet-food-premium', 'Premium Pet Food 20lb', 'High-quality nutritional pet food', 79.00, true, 10),
 ('aquarium-starter-kit', 'Aquarium Starter Kit', '10-gallon complete aquarium setup', 199.00, true, 10),
 ('pet-grooming-kit', 'Pet Grooming Kit', 'Complete grooming tools for dogs and cats', 69.00, true, 10);

-- ----------------------------
-- Seed: Product-Category Relationships (Specialized)
-- ----------------------------
INSERT INTO product_categories (product_id, category_id) VALUES
 -- Books
 (4, 4), (5, 4), (6, 4), (7, 4), (8, 4),
 -- Toys & Games
 (9, 5), (10, 5), (11, 5), (12, 5), (13, 5), (13, 1),
 -- Sports & Outdoors
 (14, 6), (15, 6), (16, 6), (16, 1), (17, 6), (18, 6),
 -- Beauty & Personal Care
 (19, 7), (20, 7), (21, 7), (22, 7), (23, 7),
 -- Furniture
 (24, 8), (25, 8), (26, 8), (27, 8), (28, 8),
 -- Automotive
 (29, 9), (30, 9), (30, 1), (31, 9), (32, 9), (33, 9),
 -- Pet Supplies
 (34, 10), (35, 10), (36, 10), (37, 10), (38, 10);
