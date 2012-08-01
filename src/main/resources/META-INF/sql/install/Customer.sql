CREATE TABLE Customer (
	customer_id INT NOT NULL AUTO_INCREMENT,
	login VARCHAR(50) NOT NULL,
	email VARCHAR(100) NOT NULL,
	firstname VARCHAR(50) NOT NULL,
	lastname VARCHAR(50) NOT NULL,

	/* Indexes */
	PRIMARY KEY (customer_id),
	UNIQUE INDEX login_unique (login),
	UNIQUE INDEX email_unique (email)
);
