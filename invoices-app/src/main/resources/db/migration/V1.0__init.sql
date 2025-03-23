create table invoice
(
    id                 int primary key,

    order_quantity     int,

    client_id          int,
    client_name        varchar(255),
    client_card_type   varchar(255),
    client_country     varchar(255),

    company_id         int,
    company_name       varchar(255),
    company_url        varchar(255),
    company_industry   varchar(255),
    company_country    varchar(255),

    product_id         int,
    product_name       varchar(255),
    product_color      varchar(255),
    product_price      double precision,

    status             smallint  not null,
    creation_date_time timestamp not null
);