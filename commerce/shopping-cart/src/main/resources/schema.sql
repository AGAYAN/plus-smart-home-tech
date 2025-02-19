CREATE SCHEMA IF NOT EXISTS cart;

CREATE TABLE IF NOT EXISTS cart.shopping_cart
(
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    product_id BIGINT,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(product_id)
);