create table if not exists coordinate (
    id int unsigned not null,
    longitude decimal(11, 9) not null,
    latitude decimal(11, 9) not null,
    primary key (id)
);

create table if not exists marker (
    id int unsigned not null,
    name varchar(50) not null,
    description varchar(512),
    event_date date not null,
    grade int,
    should_visit_again boolean,
    created_date timestamp,
    modified_date timestamp,
    coordinate_id int not null,
    primary key (id),
    constraint fk_marker_coordinate
        foreign key (coordinate_id)
            references coordinate (id)
                    on delete cascade on update cascade
);