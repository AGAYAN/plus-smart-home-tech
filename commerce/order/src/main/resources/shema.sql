CREATE TABLE orders (
    order_id SERIAL PRIMARY KEY,
    shopping_cart_id VARCHAR(255) NOT NULL,
    payment_id VARCHAR(255),
    delivery_id VARCHAR(255),
    state VARCHAR(50),
    delivery_weight DOUBLE PRECISION,
    delivery_volume DOUBLE PRECISION,
    fragile BOOLEAN,
    total_price NUMERIC(10, 2),
    delivery_price NUMERIC(10, 2),
    product_price NUMERIC(10, 2)
);

CREATE TABLE order_products (
    order_id INTEGER REFERENCES orders(order_id) ON DELETE CASCADE,
    product_id BIGINT,
    quantity INTEGER,
    PRIMARY KEY (order_id, product_id)
)
