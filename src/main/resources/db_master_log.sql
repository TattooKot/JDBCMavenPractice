--liquibase formatted sql

--changeset tattoo_kot:1
create table labels (
    id int primary key auto_increment not null,
    name varchar(50) not null
    );

--rollback drop table labels;

--changeset tattoo_kot:2
create table posts_labels (
    post_id int not null,
    label_id int not null
);

--rollback drop table posts_labels;

--changeset tattoo_kot:3
create table posts (
    id int primary key auto_increment not null,
    content varchar(10000) not null,
    created date not null,
    updated date not null,
    postStatus varchar(30) not null
);

--rollback drop table posts;

--changeset tattoo_kot:4
create table writers (
                       id int primary key auto_increment not null,
                       firstName varchar(50) not null,
                       lastName varchar(50) not null
);

--rollback drop table writers;

--changeset tattoo_kot:5
create table writers_posts (
    writer_id int not null,
    post_id int not null
);

--rollback drop table writers_posts;