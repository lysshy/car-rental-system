drop table if exists sys_car;
create table sys_car(
    id serial primary key,
    plate_number varchar(20),
    rent_price int4,
    status int2,
    brand_type int2,
    update_time timestamp(0)
);

drop table if exists sys_car_rental_record;
create table sys_car_rental_record(
    id serial primary key,
    rent_time timestamp(0),
    return_time timestamp(0),
    car_id int4,
    mobile varchar(20),
    user_name varchar(50),
    create_time timestamp(0),
    update_time timestamp(0),
    create_id int4,
    update_id int4,
    remark varchar(200),
    status int2,
    total_price int4,
    actual_return_time timestamp(0)
);

drop table if exists sys_user;
create table sys_user
(
    id serial primary key,
    username varchar(50),
    password varchar(100),
    create_time timestamp,
    update_time timestamp
);