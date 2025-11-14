create type user_role as enum ('ADMIN', 'USER');

create table users
(
    id       serial
        constraint users_pk
            primary key,
    username varchar(255) not null unique ,
    email    varchar(255) not null unique,
    role     user_role default 'USER'
);

create table employees
(
    id         serial
        constraint employees_pk
            primary key,
    names      varchar(255) not null,
    position   varchar(255),
    department varchar(255),
    hireDate   date,
    user_id    integer,
    foreign key (user_id) references users(id)
);