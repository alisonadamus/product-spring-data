CREATE TABLE products
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    cost        DECIMAL(10, 2) NOT NULL,
    photo_path   VARCHAR(255),
    description TEXT,
    category_id  BIGINT,
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE SET NULL
);