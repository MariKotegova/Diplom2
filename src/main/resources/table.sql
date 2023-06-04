create schema if not exists MyCloudStorage;

create table if not exists MyCloudStorage.files
(
    id int primary key auto_increment,
    filename varchar(255) not null,
    size int not null,
    deleted int,
    path varchar(255) not null
    );


create table if not exists MyCloudStorage.users(
    id int primary key auto_increment,
    login varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    role varchar(255) NOT NULL,
    status varchar(255) NOT NULL

    );

create table if not exists MyCloudStorage.token
(
    id int primary key auto_increment,
    token varchar(255) not null
)

