create table company
(
    id       int primary key GENERATED ALWAYS AS IDENTITY,
    name     varchar(255) not null unique,
    url      varchar(255) not null unique,
    industry varchar(255) not null,
    country  varchar(255) not null,
    status   varchar(50) not null CHECK ( status IN ('ACTIVE', 'RETIRED'))
);