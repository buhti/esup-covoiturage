CREATE TABLE Customer (
	customer_id INT NOT NULL AUTO_INCREMENT,
	login VARCHAR(50) NOT NULL,
	email VARCHAR(100) NOT NULL,
	firstname VARCHAR(50) NOT NULL,
	lastname VARCHAR(50) NOT NULL,
	chatting BOOLEAN NOT NULL,
	smoking BOOLEAN NOT NULL,
	listening_music BOOLEAN NOT NULL,

	/* Indexes */
	PRIMARY KEY (customer_id),
	UNIQUE KEY login_unique (login),
	UNIQUE KEY email_unique (email)
) ENGINE = MYISAM;
