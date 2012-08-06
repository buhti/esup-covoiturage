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
	distance VARCHAR(8) NOT NULL,
	recurrent TINYINT(1) NOT NULL,

	/* RouteRecurrent */
	start_date DATE,
	end_date DATE,
	wayout_time VARCHAR(5),
	wayback_time VARCHAR(5),

	/* RouteOccasional */
	wayout_date DATETIME,
	wayback_date DATETIME,

	/* Indexes */
	PRIMARY KEY (route_id),
	INDEX fk_owner_id (owner_id),
	INDEX USING BTREE (recurrent),
	SPATIAL INDEX sp_from_point (from_point),
	SPATIAL INDEX sp_to_point (to_point)
);
