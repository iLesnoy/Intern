create table roles
(
    id   bigint       not null
        constraint roles_pkey
            primary key,
    name varchar(255) not null
        constraint uk_ofx66keruapi6vyqpv6f2or37
            unique
);

alter table roles
    owner to root;

create table category
(
    id              numeric(19, 2) not null
        constraint category_pkey
            primary key,
    description     varchar(255),
    name            varchar(255)   not null
        constraint uk_46ccwnsi9409t36lurvtyljak
            unique,
    parent_category varchar(255)
);

alter table category
    owner to root;

create table company
(
    id          numeric(19, 2) not null
        constraint company_pkey
            primary key,
    created     timestamp      not null,
    description varchar(255)   not null,
    email       varchar(255)   not null,
    name        varchar(255)   not null
);

alter table company
    owner to root;

create table item
(
    id          uuid         not null
        constraint item_pkey
            primary key,
    created     timestamp    not null,
    description varchar(255),
    name        varchar(255) not null,
    category_id numeric(19, 2)
        constraint fk2n9w8d0dp4bsfra9dcg0046l4
            references category
);

alter table item
    owner to root;

create table position
(
    id         numeric(19, 2) not null
        constraint position_pkey
            primary key,
    amount     numeric(19, 2) not null,
    created    timestamp      not null,
    created_by varchar(255)   not null,
    company_id numeric(19, 2)
        constraint fkkpqfp6ontwcnqpkrfwv02iw1y
            references company,
    item_id    uuid
        constraint fk4povmr863xpok7k7blig5ndqx
            references item
);

alter table position
    owner to root;

create table users
(
    id         uuid        not null
        constraint users_pkey
            primary key,
    created    timestamp   not null,
    email      varchar(32) not null
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
            unique,
    name       varchar(36) not null,
    role_id    integer     not null,
    updated    timestamp   not null,
    username   varchar(36) not null,
    company_id numeric(19, 2)
        constraint fkbwv4uspmyi7xqjwcrgxow361t
            references company
);

alter table users
    owner to root;

create table databasechangeloglock
(
    id          integer not null
        constraint databasechangeloglock_pkey
            primary key,
    locked      boolean not null,
    lockgranted timestamp,
    lockedby    varchar(255)
);

alter table databasechangeloglock
    owner to root;

create table databasechangelog
(
    id            varchar(255) not null,
    author        varchar(255) not null,
    filename      varchar(255) not null,
    dateexecuted  timestamp    not null,
    orderexecuted integer      not null,
    exectype      varchar(10)  not null,
    md5sum        varchar(35),
    description   varchar(255),
    comments      varchar(255),
    tag           varchar(255),
    liquibase     varchar(20),
    contexts      varchar(255),
    labels        varchar(255),
    deployment_id varchar(10)
);

alter table databasechangelog
    owner to root;

