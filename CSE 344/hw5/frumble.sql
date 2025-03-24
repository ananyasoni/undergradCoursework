-- Part a:
-- FDs: name -> price, month -> discount
-- FD: name -> price
SELECT name, COUNT(DISTINCT price) as distinct_prices
FROM sales
GROUP BY name
HAVING COUNT(DISTINCT price) > 1;

-- FD: month -> discount
SELECT month, COUNT(DISTINCT discount) as distinct_discounts
FROM sales
GROUP BY month
HAVING COUNT(DISTINCT discount) > 1;

-- FD that does not hold: name -> discount
SELECT name, COUNT(DISTINCT discount) as distinct_discounts
FROM sales
GROUP BY name
HAVING COUNT(DISTINCT discount) > 1;

-- FD: (name, discount) -> price
SELECT name, discount, COUNT(DISTINCT price) as distinct_prices
FROM sales
GROUP BY name, discount
HAVING COUNT(DISTINCT price) > 1;

-- FD: (name, price, month) -> discount
SELECT name, price, month, COUNT(DISTINCT discount) as distinct_discounts
FROM sales
GROUP BY name, price, month
HAVING COUNT(DISTINCT discount) > 1;

-- FD that does not hold: (month, price, discount) -> name
SELECT month, price, discount, COUNT(DISTINCT name) as distinct_names
FROM sales
GROUP BY month, price, discount
HAVING COUNT(DISTINCT name) > 1;

-- Part b:
-- original sales table with all tuples from csv
CREATE TABLE sales (
    name TEXT,
    discount TEXT,
    month TEXT,
    price INT
);

-- R1(name, price)
CREATE TABLE products (
    name TEXT PRIMARY KEY,
    price INT
);

-- R2(month, discount)
CREATE TABLE monthlyDiscounts (
    month TEXT PRIMARY KEY,
    discount TEXT
);

-- R3(month, name)
CREATE TABLE monthsProductSold (
    name TEXT,
    month TEXT,
    PRIMARY KEY (name, month),
    FOREIGN KEY(name) REFERENCES products(name)
    FOREIGN KEY(month) REFERENCES monthlyDiscounts(month)
);

-- Part c:
-- Insert into products (name, price)
-- 36 rows
INSERT INTO products
SELECT DISTINCT name, price
FROM sales;

-- Insert into monthlyDiscounts (month, discount)
-- 12 rows
INSERT INTO monthlyDiscounts
SELECT DISTINCT month, discount
FROM sales;

-- Insert into monthsProductSold (name, month)
-- 426 rows
INSERT INTO monthsProductSold
SELECT DISTINCT name, month
FROM sales;

