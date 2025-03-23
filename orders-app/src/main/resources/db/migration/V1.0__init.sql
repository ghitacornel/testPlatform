create table T_ORDER
(
    id         int primary key GENERATED ALWAYS AS IDENTITY,
    client_id  int      not null,
    product_id int      not null,
    quantity   int      not null,
    status     smallint not null
);
create table ORDER_ARCHIVE
(
    id         int primary key,
    client_id  int      not null,
    product_id int      not null,
    quantity   int      not null,
    status     smallint not null
);