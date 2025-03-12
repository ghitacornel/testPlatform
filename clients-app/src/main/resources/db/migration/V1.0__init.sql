create table client
(
    id        int primary key GENERATED ALWAYS AS IDENTITY,
    name      varchar(255) not null unique,
    card_type varchar(255) not null,
    country   varchar(255) not null,
    status    varchar(50)  not null CHECK ( status IN ('ACTIVE', 'RETIRED'))
);
create table client_archive
(
    id        int primary key,
    name      varchar(255) not null,
    card_type varchar(255) not null,
    country   varchar(255) not null,
    status    varchar(50)  not null CHECK ( status IN ('ACTIVE', 'RETIRED'))
);