create table company
(
    id       int primary key GENERATED ALWAYS AS IDENTITY,
    name     varchar(50),
    url      varchar(50),
    industry varchar(50)
);