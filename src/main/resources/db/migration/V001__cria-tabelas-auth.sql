create table users (
	id bigint not null auto_increment,
    username varchar(60) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    
    primary key (id)
);

create table roles (
	id bigint not null auto_increment,
    name varchar(20) not null,
    
    primary key (id)
);


create table user_roles (
	id bigint not null auto_increment,
    user_id bigint not null,
    role_id bigint not null,
    
    primary key (id)
);

alter table user_roles add constraint fk_user_role
foreign key (user_id) references users (id);

alter table user_roles add constraint fk_role_user_role
foreign key (role_id) references roles (id);

INSERT INTO `roles`(`name`) VALUES('ROLE_ADMIN');
INSERT INTO `roles`(`name`) VALUES('ROLE_TRIADOR');
INSERT INTO `roles`(`name`) VALUES('ROLE_FINALIZADOR');

-- Senha 123456
INSERT INTO `users`(`username`, `email`, `password`) VALUES('admin', 'admin@softplan.com', '$2a$10$7cd660r..5p.DI6vJxFQ8eCcRMOXUJ0HvVw.QeM9xej5vEmf71sCq');

INSERT INTO `user_roles`(`user_id`, `role_id`) VALUES(1,1);
