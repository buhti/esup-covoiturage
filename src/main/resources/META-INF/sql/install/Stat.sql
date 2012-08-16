CREATE TABLE Stat (
	stat_id INT NOT NULL AUTO_INCREMENT,
	stat_type TINYINT(2) NOT NULL,
	stat_date DATE NOT NULL,
	stat_value INT NOT NULL,

	/* Indexes */
	PRIMARY KEY (stat_id),
	KEY (stat_type) USING BTREE,
	KEY (stat_date)
);
