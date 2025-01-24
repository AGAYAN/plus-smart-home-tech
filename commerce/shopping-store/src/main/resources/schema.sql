CREATE SCHEMA IF NOT EXISTS store;

CREATE TABLE IF NOT EXISTS store.products
(
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    description TEXT,
    image_src VARCHAR(512),
    quantity_state VARCHAR(50),
    product_state VARCHAR(50),
    rating DOUBLE PRECISION,
    product_category VARCHAR(100),
    price DOUBLE PRECISION
);