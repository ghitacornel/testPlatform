create table product
(
    id         int primary key GENERATED ALWAYS AS IDENTITY,
    name       varchar(255)     not null,
    color      varchar(255)     not null,
    price      double precision not null,
    quantity   int              not null,
    company_id int              not null,
    status     smallint         not null
);