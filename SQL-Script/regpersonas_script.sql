CREATE DATABASE IF NOT EXISTS regpersonas;

USE regpersonas;

DROP TABLE administrators;

SELECT * FROM administrators;
SELECT * FROM regpersonas.persons ORDER BY (name);

TRUNCATE persons;
TRUNCATE administrators;
