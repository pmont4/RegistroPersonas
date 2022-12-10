CREATE DATABASE IF NOT EXISTS regpersonas;

USE regpersonas;

DROP TABLE administrators;
DROP TABLE people;

SELECT * FROM administrators;
SELECT * FROM regpersonas.people ORDER BY (name);

SELECT * FROM paulo_log;

TRUNCATE people;
TRUNCATE administrators;

DROP TABLE paulo_log;

UPDATE people SET birth_date = '1966-01-02' WHERE id = 7;
