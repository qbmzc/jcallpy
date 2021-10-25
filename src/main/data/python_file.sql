-- auto-generated definition
create table python_file
(
    id          bigint auto_increment
        primary key,
    file_path   varchar(500)      null,
    file_name   varchar(255)      null,
    remarks     varchar(1000)     null,
    state       tinyint default 0 null,
    create_time datetime          null,
    update_time datetime          null,
    version     bigint            null,
    is_deleted  tinyint           null
);