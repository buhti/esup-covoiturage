CREATE TABLE Route (
	route_id INT NOT NULL AUTO_INCREMENT,
	owner_id INT NOT NULL,
	driver TINYINT(1) NOT NULL,
	seats TINYINT(2) NOT NULL,
	from_point POINT NOT NULL,
	from_city VARCHAR(80) NOT NULL,
	from_address VARCHAR(255) NOT NULL,
	to_point POINT NOT NULL,
	to_city VARCHAR(80) NOT NULL,
	to_address VARCHAR(255) NOT NULL,
	distance SMALLINT(4) NOT NULL,
	recurrent TINYINT(1) NOT NULL,
	round_trip TINYINT(1) NOT NULL,

	/* RouteRecurrent */
	start_date DATE,
	end_date DATE,
	wayout_time VARCHAR(5),
	wayback_time VARCHAR(5),
	week_days VARCHAR(7),

	/* RouteOccasional */
	wayout_date DATETIME,
	wayback_date DATETIME,

	/* Indexes */
	PRIMARY KEY (route_id),
	KEY fk_owner_id (owner_id),
	KEY b_recurrent (recurrent) USING BTREE,
	SPATIAL KEY sp_from_point (from_point),
	SPATIAL KEY sp_to_point (to_point)
) ENGINE = MYISAM;
