create table ORDERS
(
    id            int primary key GENERATED ALWAYS AS IDENTITY,
    client_id     int      not null,
    product_id    int      not null,
    quantity      int      not null,
    status        smallint not null,
    reject_reason varchar(255)
);
create table ORDERS_ARCHIVE
(
    id            int primary key,
    client_id     int      not null,
    product_id    int      not null,
    quantity      int      not null,
    status        smallint not null,
    reject_reason varchar(255)
);