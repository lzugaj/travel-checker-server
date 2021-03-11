drop table if exists "COORDINATE";
drop table if exists "MARKER";

create table "COORDINATE" (
    id bigserial not null,
    longitude decimal(10, 9) not null,
    latitude decimal(10, 9) not null,
    primary key (id)
);

create table "MARKER" (
    id bigserial not null,
    name varchar(50) not null,
    description varchar(512),
    event_date date not null,
    grade int,
    should_visit_again boolean,
    created_date timestamp,
    modified_date timestamp,
    coordinate_id int not null,
    primary key (id),
    constraint fk_marker_coordinate foreign key(coordinate_id) references "COORDINATE" (id)
);