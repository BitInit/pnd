/******************************************/
/*   数据库全名 = pnd   */
/******************************************/

CREATE TABLE users (
	username varchar(50) NOT NULL PRIMARY KEY,
	password varchar(500) NOT NULL,
	enabled boolean NOT NULL
);
