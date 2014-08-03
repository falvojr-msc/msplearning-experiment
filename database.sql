create database msplearning_exp;

create table tb_owner (
	id bigint not null,
	name varchar(50) not null,
	primary key (id)
);

create table tb_user (
	id bigint not null auto_increment,
	date_last_login datetime not null,
	date_registration datetime not null,
	email varchar(50) not null,
	first_name varchar(50) not null,
	gender varchar(1) not null,
	last_name varchar(50) not null,
	password varchar(30) not null,
	id_owner bigint not null,
	primary key (id)
);

create table tb_discipline (
	id bigint not null auto_increment,
	description longtext,
	id_owner bigint not null,
	name varchar(50) not null,
	primary key (id)
);

create table tb_educational_content (
	id bigint not null auto_increment,
	footnote varchar(50),
	id_owner bigint not null,
	page bigint not null,
	title varchar(50) not null,
	url longtext not null,
	primary key (id)
);

alter table tb_user 
	add index fk_owner_user (id_owner), 
	add constraint fk_owner_user 
	foreign key (id_owner) 
	references tb_owner (id);
	
alter table tb_discipline 
	add index fk_owner_discipline (id_owner), 
	add constraint fk_owner_discipline 
	foreign key (id_owner) 
	references tb_owner (id);
	
alter table tb_educational_content 
	add index fk_owner_educational_content (id_owner), 
	add constraint fk_owner_educational_content 
	foreign key (id_owner) 
	references tb_owner (id);