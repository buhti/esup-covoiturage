CREATE TABLE Stat (
	stat_type SMALLINT(2) NOT NULL,
	stat_date DATE NOT NULL,
	stat_value INT NOT NULL,

	/* Indexes */
	PRIMARY KEY (stat_type, stat_date),
	KEY (stat_type) USING BTREE,
	KEY (stat_date)
) ENGINE = MYISAM;

CREATE TABLE StatCounter (
	count_key SMALLINT(2) NOT NULL,
	count_value INT NOT NULL,

	/* Indexes */
	PRIMARY KEY (count_key)
) ENGINE = MYISAM;
