BEGIN;

DROP TABLE IF EXISTS products CASCADE;
CREATE TABLE products (id bigserial PRIMARY KEY , title VARCHAR(255), cost int);

DROP TABLE IF EXISTS customers CASCADE;
CREATE TABLE customers (id bigserial PRIMARY KEY , name VARCHAR(255));
CREATE INDEX "name" ON customers (name);

DROP TABLE IF EXISTS shopping;
CREATE TABLE shopping (product_id integer REFERENCES products (id), customer_id integer REFERENCES customers (id));

COMMIT;