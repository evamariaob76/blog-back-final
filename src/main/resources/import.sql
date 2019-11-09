INSERT INTO clientes (username, nombre, apellido, email, create_at) VALUES('a85','Andrés', 'Guzmán', 'profesor@bolsadeideas.com', '2018-01-01');
INSERT INTO clientes (username, nombre, apellido, email, create_at) VALUES('j23','Mr. John', 'Doe', 'john.doe@gmail.com', '2018-01-02');
INSERT INTO clientes (username, nombre, apellido, email, create_at) VALUES('l85','Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2018-01-03');
INSERT INTO clientes (username, nombre, apellido, email, create_at) VALUES('r55','Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2018-01-04');
INSERT INTO clientes (username,nombre, apellido, email, create_at) VALUES('E85','Erich', 'Gamma', 'erich.gamma@gmail.com', '2018-02-01');
INSERT INTO clientes (username,nombre, apellido, email, create_at) VALUES('r85','Richard', 'Helm', 'richard.helm@gmail.com', '2018-02-10');
INSERT INTO clientes (username,nombre, apellido, email, create_at) VALUES('R985','Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2018-02-18');
INSERT INTO clientes (username,nombre, apellido, email, create_at) VALUES('J185','John', 'Vlissides', 'john.vlissides@gmail.com', '2018-02-28');
INSERT INTO clientes (username,nombre, apellido, email, create_at) VALUES('D85','Dr. James', 'Gosling', 'james.gosling@gmail.com', '2018-03-03');
INSERT INTO clientes (username,nombre, apellido, email, create_at) VALUES('M85','Magma', 'Lee', 'magma.lee@gmail.com', '2018-03-04');
INSERT INTO clientes (username,nombre, apellido, email, create_at) VALUES('T65','Tornado', 'Roe', 'tornado.roe@gmail.com', '2018-03-05');
INSERT INTO clientes (username,nombre, apellido, email, create_at) VALUES('j785','Jade', 'Doe', 'jane.doe@gmail.com', '2018-03-06');

INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('andres','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',1, 'Andres', 'Guzman','profesor@bolsadeideas.com');
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('admin','$2a$10$RmdEsvEfhI7Rcm9f/uZXPebZVCcPC7ZXZwV51efAvMAp1rIaRAfPK',1, 'John', 'Doe','jhon.doe@bolsadeideas.com');

INSERT INTO `roles` (nombre) VALUES ('ROLE_USER');
INSERT INTO `roles` (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO `usuarios_roles` (usuario_id, rol_id) VALUES (1, 1);
INSERT INTO `usuarios_roles` (usuario_id, rol_id) VALUES (2, 2);
INSERT INTO `usuarios_roles` (usuario_id, rol_id) VALUES (2, 1);



