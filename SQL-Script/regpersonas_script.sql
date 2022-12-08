CREATE DATABASE IF NOT EXISTS regpersonas;

USE regpersonas;

DROP TABLE administrators;
DROP TABLE people;

SELECT * FROM administrators;
SELECT * FROM regpersonas.persons ORDER BY (name);

TRUNCATE people;
TRUNCATE administrators;
