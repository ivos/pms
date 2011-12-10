
    alter table user_system_roles 
        drop constraint User_System_Roles__User;

    drop table user if exists;

    drop table user_system_roles if exists;

    drop sequence hibernate_sequence;

    create table user (
        id bigint not null,
        email varchar(100) not null unique,
        full_name varchar(100),
        password varchar(100) not null,
        version integer not null,
        primary key (id)
    );

    create table user_system_roles (
        user bigint not null,
        system_role varchar(32) not null,
        primary key (user, system_role)
    );

    alter table user_system_roles 
        add constraint User_System_Roles__User 
        foreign key (user) 
        references user;

    create sequence hibernate_sequence start with 1 increment by 1;
