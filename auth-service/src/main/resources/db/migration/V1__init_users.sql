-- V1__init.sql
-- Flyway migration: criação inicial do schema

CREATE EXTENSION IF NOT EXISTS unaccent;

CREATE TABLE IF NOT EXISTS users
(
    id       UUID PRIMARY KEY,
    cpf      TEXT        NOT NULL,
    name     TEXT        NOT NULL,
    username TEXT        NOT NULL UNIQUE,
    password TEXT        NOT NULL,
    role     VARCHAR(20) NOT NULL
);
