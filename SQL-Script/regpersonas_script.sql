CREATE DATABASE IF NOT EXISTS regpersonas;

USE regpersonas;

DROP TABLE IF EXISTS admins;
DROP TABLE IF EXISTS personas;

INSERT INTO regpersonas.admins(nombre, contrasena, correo, permisos) VALUE ('Paulo', 'monterroso', 'paulo@gmail.com', 'agregar,borrar,modificar');

TRUNCATE TABLE admins;

SELECT * FROM admins WHERE nombre = Paulo;

UPDATE admins SET permisos='agregar,borrar' WHERE correo = 'paulo@gmail.com';

SELECT * FROM personas;
SELECT * FROM admins;