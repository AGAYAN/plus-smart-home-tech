CREATE TABLE payments (
    payment_id SERIAL PRIMARY KEY,
    order_id VARCHAR(255) NOT NULL,
    total_payment NUMERIC(10, 2) NOT NULL,
    delivery_total NUMERIC(10, 2) NOT NULL,
    fee_total NUMERIC(10, 2) NOT NULL
);