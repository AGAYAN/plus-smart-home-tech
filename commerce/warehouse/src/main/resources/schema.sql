CREATE TABLE IF NOT EXISTS warehouse.dimension (
    id SERIAL PRIMARY KEY,
    width DOUBLE PRECISION NOT NULL,
    height DOUBLE PRECISION NOT NULL,
    depth DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS warehouse.warehouseproduct (
    id SERIAL PRIMARY KEY,
    quantity INT NOT NULL,
    fragile BOOLEAN NOT NULL,
    weight DOUBLE PRECISION NOT NULL,
    dimension_id INT,
    CONSTRAINT fk_dimension FOREIGN KEY (dimension_id) REFERENCES warehouse.dimension (id)
    );