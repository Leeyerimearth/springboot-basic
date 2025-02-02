create table customers
(
    customer_id   binary(16)                               not null
        primary key,
    name          varchar(20)                              not null,
    email         varchar(50)                              not null,
    last_login_at datetime(6)                              null,
    created_at    datetime(6) default CURRENT_TIMESTAMP(6) not null,
    constraint unq_user_email
        unique (email)
);

create table vouchers
(
    voucher_id          binary(16)                         not null primary key,
    voucher_type        varchar(10)                        not null,
    discount_amount     int                                not null,
    created_at          datetime(6) default CURRENT_TIMESTAMP(6) not null,
    owner_id            binary(16)                         null,
    owned_time          datetime(6)                        null,
    FOREIGN KEY (owner_id) references customers(customer_id)
);