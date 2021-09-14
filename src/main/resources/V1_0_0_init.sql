create table products (
    id bigserial PRIMARY KEY,
    title text,
    cost int
);

create table customers (
    id bigserial PRIMARY KEY, name text
);

create index "name" on customers (name);

create table shopping (
    product_id integer REFERENCES products (id),
    customer_id integer REFERENCES customers (id)
)