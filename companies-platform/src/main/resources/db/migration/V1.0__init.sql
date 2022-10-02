create table company
(
    id       int primary key GENERATED ALWAYS AS IDENTITY,
    name     varchar(50) not null unique,
    url      varchar(50) not null unique,
    industry varchar(50) not null
);