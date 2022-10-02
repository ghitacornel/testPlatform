create table client
(
    id   int primary key GENERATED ALWAYS AS IDENTITY,
    name varchar(50),
    card_type varchar(50)
);