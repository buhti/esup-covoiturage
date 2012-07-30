create table Customer (
	customer_id int not null auto_increment,
	login varchar(50) not null,
	email varchar(100) not null,
	firstname varchar(50) not null,
	lastname varchar(50) not null,
	primary key (customer_id),
	unique index login_unique (login),
	unique index email_unique (email));
