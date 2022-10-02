create table T_ORDER
(
    id         int primary key GENERATED ALWAYS AS IDENTITY,
    name       varchar(50)      not null,
    color      varchar(50)      not null,
    price      double precision not null,
    quantity   int              not null,
    company_id int              not null,
    status     int              not null,
    version    int              not null
);