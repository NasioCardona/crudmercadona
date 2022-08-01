# crud-mercadona Project

#MAVEN BUILD DEPENDENCY 

<dependency>
   <groupId>io.quarkus</groupId>
   <artifactId>quarkus-container-image-docker</artifactId>
</dependency>

#MAVEN INSTRUCTION PACKAGE

.\mvnw clean package

#DOCKER RUN INSTRUCTION

docker run -d -p 8080:8080 --name crud-mercadona -d ***USER***/crudmercadona:0.0.1-SNAPSHOT

***USER*** -> User computer

#CONTACT
nasiocardona@gmail.com

##BD MYSQL + DOCKER LOCAL + SCRIPTS
1. Instalación MYSQL + Scripts + Creación y arranque Docker Image

1.	Abrir consola powerShell o bash y lanzar las siguientes instrucciones para crear y arrancar la imagen docker:

>	docker pull mysql
>	docker run -d -p 33060:33060 --name mysql-crud-mercadona -e MYSQL_ROOT_PASSWORD=crud_mercadona -d mysql:latest
>	docker exec -it mysql-crud-mercadona bash

2.	Ejecutar la siguiente instrucción para acceder a MYSQL:

>	mysql -u root -pcrud_mercadona

3.	Una vez dentro de MYSQL Ejecutaremos el siguiente SCRIPT para crear la base de datos y tablas asociadas:

>	CREATE DATABASE crud_mercadona;
>	USE crud_mercadona;

#(SCRIPTS BD) CREACIÓN TABLAS Y TUPLAS

CREATE TABLE products (
  id MEDIUMINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(200) CHARSET utf8 COLLATE utf8_spanish2_ci NOT NULL, 
  d_create TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  d_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE categories (
  id MEDIUMINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(200) CHARSET utf8 COLLATE utf8_spanish2_ci NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE detail (
  id_product MEDIUMINT NOT NULL,
  id_categories MEDIUMINT NOT NULL,    
  stock MEDIUMINT,
  price decimal(12,2),
  UNIQUE(id_product),
  FOREIGN KEY (id_product) REFERENCES products(id),
  FOREIGN KEY (id_categories) REFERENCES categories(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#TRIGGER EN DETAIL PARA ACTUALIZAR LA FECHA DE ACTUALIZACIÓN
delimiter //
CREATE TRIGGER date_update AFTER UPDATE ON detail 
FOR EACH ROW 
BEGIN
	UPDATE products set d_update = NOW() where id = NEW.id_product;
END;//
delimiter ;

#INSERCIÓN TUPLAS EJEMPLO

INSERT INTO products (name) VALUES
('Atun claro aceite oliva (abre facil solapin)'), 
('Papel higienico doble rollo 2 capas'), 
('Papel cocina blanco doble capa doble rollo'), 
('Tomate frito'), 
('Fuet espetec extra'), 
('Toallitas humedas bebé frescas y perfumadas con aloe vera'), 
('Pañuelos papel blancos');

INSERT INTO categories (name) VALUES
('HACENDADO'),
('BOSQUE VERDE'),
('DELIPLUS');

INSERT INTO detail (id_product, id_categories, stock, price) VALUES
(1,1,100,4.66),
(2,2,70,4.90),
(3,2,200,2.18),
(4,1,130,0.85),
(5,1,1000,2.05),
(6,3,10,1.04),
(7,2,137,1.20);
