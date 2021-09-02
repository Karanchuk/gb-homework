BEGIN;

DROP TABLE IF EXISTS products;
CREATE TABLE products (id bigserial PRIMARY KEY , title VARCHAR(255), cost int);

COMMIT;