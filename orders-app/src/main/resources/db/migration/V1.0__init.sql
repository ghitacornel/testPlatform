create table T_ORDER
(
    id                    int primary key GENERATED ALWAYS AS IDENTITY,
    user_id               int              not null,
    user_name             varchar(255)     not null,
    user_credit_card_type varchar(255)     not null,
    user_country          varchar(255)     not null,
    product_id            int              not null,
    product_name          varchar(255)     not null,
    product_color         varchar(255)     not null,
    price                 double precision not null,
    quantity              int              not null,
    company_id            int              not null,
    company_name          varchar(255)     not null,
    company_country       varchar(255)     not null,
    status                int              not null
);