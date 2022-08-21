create schema if not exists public;

create table users
(
    id bigserial not null
        constraint info_pk
            primary key,
    first_name varchar,
    last_name varchar,
    phone_number varchar,
    password varchar,
);