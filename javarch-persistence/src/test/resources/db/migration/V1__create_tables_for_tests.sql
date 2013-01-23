/* Single line comment */
CREATE TABLE user (
	id identity,
	nome_usuario VARCHAR(255),
	senha VARCHAR(32),
	email VARCHAR(255),
	date_created TIMESTAMP,
	last_updated TIMESTAMP
);

CREATE TABLE BLOG (
	id INT NOT NULL PRIMARY KEY,
	titulo varchar(255),
	id_usuario INT,
	FOREIGN KEY(id_usuario) REFERENCES user(id)
);

/*
Multi-line
comment
*/

-- Sql-style comment

-- Placeholder
--INSERT INTO ${tableName} (name) VALUES ('Mr. T');
--INSERT INTO test_user (name) VALUES ( $$'Mr. Semicolon+Linebreak;
--another line'$$);