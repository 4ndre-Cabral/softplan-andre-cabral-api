create table opinions (
	id bigint not null auto_increment,
    description varchar(255) not null,
    date_register datetime not null,
    user_id bigint not null,
    procedure_id bigint not null,
    
    primary key (id)
);

create table procedures (
	id bigint not null auto_increment,
    description varchar(255) not null,
    date_register datetime not null,
    user_register_id bigint not null,
    
    primary key (id)
);

create table procedure_opinion (
	id bigint not null auto_increment,
    user_id bigint not null,
    procedure_id bigint not null,
    
    primary key (id)
);

alter table opinions add constraint fk_user_opinion
foreign key (user_id) references users (id);

alter table opinions add constraint fk_procedure_opinion
foreign key (procedure_id) references procedures (id);

alter table procedures add constraint fk_user_register_procedure
foreign key (user_register_id) references users (id);

alter table procedure_opinion add constraint fk_user_procedure_opinion
foreign key (user_id) references users (id);

alter table procedure_opinion add constraint fk_procedure_opinion_procedure
foreign key (procedure_id) references procedures (id);


