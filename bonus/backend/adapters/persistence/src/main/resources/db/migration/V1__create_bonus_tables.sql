create table tbl_bonus
(
    id         serial      not null,
    bonus_code varchar(50) not null,
    info       varchar(200),
    valid_from date,
    valid_to   date,

    primary key (id)
);

create table tbl_bonus_condition
(
    id              serial      not null,
    bonus_id        integer,
    min_investment  integer,
    max_investment  integer,

    payout_type     varchar(20) not null,
    payout_absolute integer,
    payout_relative decimal(5, 4),

    primary key (id),
    foreign key (bonus_id) references tbl_bonus (id) on delete cascade
);
