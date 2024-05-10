create table users(
    id bigint auto_increment,
    name varchar(255) not null,
    balance decimal(19, 2) not null,
    primary key (id)
);

create table users_transaction(
    id bigint auto_increment,
    user_id bigint not null,
    amount decimal(19, 2) not null,
    transaction_status varchar(255) not null,
    transaction_date timestamp not null,
    primary key (id),
    foreign key (user_id) references users(id)
);