create schema if not exists public;
drop table if exists users cascade;
drop table if exists authentications cascade;

create table users
(
    id bigserial not null
        constraint info_pk
            primary key,
    first_name varchar,
    last_name varchar,
    phone_number varchar unique,
    password varchar
);