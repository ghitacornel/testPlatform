create table client
(
    id        int primary key GENERATED ALWAYS AS IDENTITY,
    name      varchar(50) not null unique,
    card_type varchar(50) not null,
    status    int         not null,
);