create table bike_triumph
(
    immat             varchar(255) not null
        primary key,
    is_automatic      boolean,
    capcity_cc        integer,
    ciruculation_date date,
    cylinder_number   varchar(255),
    have_shifter      boolean,
    shifter_type      integer
);

create table car_porshe
(
    immat varchar(8) not null primary key,
    circulation_date  date,
    cylinder_capacity double precision,
    power             integer,
    power_type        integer,
    torque            integer,
    weight            integer,
    weight_unity      varchar(255)
);


