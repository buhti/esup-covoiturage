CREATE TABLE Stat (
	stat_id INT NOT NULL AUTO_INCREMENT,
	stat_category TINYINT(2) NOT NULL,
	stat_date DATE NOT NULL,
	stat_value INT NOT NULL,

	/* Indexes */
	PRIMARY KEY (stat_id),
	KEY (stat_category) USING BTREE,
	KEY (stat_date)
);
