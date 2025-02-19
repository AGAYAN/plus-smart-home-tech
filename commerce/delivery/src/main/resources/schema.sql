CREATE TABLE addresses (
    address_id SERIAL PRIMARY KEY,
    country VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    house VARCHAR(50),
    flat VARCHAR(50)
);

CREATE TABLE deliveries (
    delivery_id SERIAL PRIMARY KEY,
    from_address_id INTEGER REFERENCES addresses(address_id) ON DELETE CASCADE,
    to_address_id INTEGER REFERENCES addresses(address_id) ON DELETE CASCADE,
    order_id VARCHAR(255) NOT NULL,
    delivery_state VARCHAR(50)
);