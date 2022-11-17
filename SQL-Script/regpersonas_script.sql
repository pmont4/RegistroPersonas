CREATE DATABASE IF NOT EXISTS regpersonas;

USE regpersonas;

DROP TABLE IF EXISTS admins;
DROP TABLE IF EXISTS personas;

INSERT INTO regpersonas.admins(nombre, contrasena, correo, permisos) VALUE ('Paulo', 'monterroso', 'paulo@gmail.com', 'agregar,borrar,modificar');

TRUNCATE TABLE admins;
TRUNCATE TABLE personas;

SELECT * FROM admins WHERE correo = 'paulo@gmail.com';

UPDATE admins SET permisos='agregar,borrar' WHERE correo = 'paulo@gmail.com';

SELECT * FROM personas;
SELECT * FROM admins;

TRUNCATE paulo_log;

SELECT * FROM paulo_log;

SHOW TABLES LIKE 'personas';