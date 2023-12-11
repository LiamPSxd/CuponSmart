DROP DATABASE cuponsmart;
CREATE DATABASE IF NOT EXISTS cuponsmart;

USE cuponsmart;

CREATE USER IF NOT EXISTS 'admin'@'localhost' IDENTIFIED BY 'equipo6';
GRANT ALL PRIVILEGES ON cuponsmart.* TO 'admin'@'localhost';
FLUSH PRIVILEGES;

SHOW GRANTS FOR admin@'localhost';

## Estado
DROP TABLE IF EXISTS estado;
CREATE TABLE IF NOT EXISTS estado(
	id INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO estado(nombre) VALUES ("Veracruz");
INSERT INTO estado(nombre) VALUES ("Chihuahua");
INSERT INTO estado(nombre) VALUES ("Jalisco");

SELECT * FROM estado;

## Municipio
DROP TABLE IF EXISTS municipio;
CREATE TABLE IF NOT EXISTS municipio(
	id INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(20) NOT NULL,
	idEstado INT,
    PRIMARY KEY (id),
    FOREIGN KEY (idEstado) REFERENCES estado(id)
);

INSERT INTO municipio(nombre, idEstado) VALUES ("Xalapa", 1);
INSERT INTO municipio(nombre, idEstado) VALUES ("Minatitlán", 1);
INSERT INTO municipio(nombre, idEstado) VALUES ("Chihuahua", 2);
INSERT INTO municipio(nombre, idEstado) VALUES ("Allende", 2);
INSERT INTO municipio(nombre, idEstado) VALUES ("El Salto", 3);
INSERT INTO municipio(nombre, idEstado) VALUES ("Guadalajara", 3);

SELECT * FROM municipio;

## Ciudad
DROP TABLE IF EXISTS ciudad;
CREATE TABLE IF NOT EXISTS ciudad(
	id INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(30) NOT NULL,
    idMunicipio INT,
    PRIMARY KEY (id),
    FOREIGN KEY (idMunicipio) REFERENCES municipio(id)
);

INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Xalapa-Enríquez", 1);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Lomas Verdes", 1);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Minatitlán", 2);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Mapachapa", 2);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Chihuahua", 3);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Ciudad Juárez", 3);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Valle de Ignacio Allende", 4);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Pueblito de Allende", 4);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("El Salto", 5);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Las Pintitas", 5);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Guadalajara", 6);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Membrillos", 6);

SELECT * FROM ciudad;

## Rol
DROP TABLE IF EXISTS rol;
CREATE TABLE IF NOT EXISTS rol(
	id INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO rol(nombre) VALUES ("Administrador General");
INSERT INTO rol(nombre) VALUES ("Administrador Comercial");

SELECT * FROM rol;

## Estatus
DROP TABLE IF EXISTS estatus;
CREATE TABLE IF NOT EXISTS estatus(
	id INT AUTO_INCREMENT NOT NULL,
    estado VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO estatus(estado) VALUES ("Activo");
INSERT INTO estatus(estado) VALUES ("Inactivo");

SELECT * FROM estatus;

## Categoria
DROP TABLE IF EXISTS categoria;
CREATE TABLE IF NOT EXISTS categoria(
	id INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO categoria(nombre) VALUES ("Aerolínea");
INSERT INTO categoria(nombre) VALUES ("Cafetería");
INSERT INTO categoria(nombre) VALUES ("Farmacia");
INSERT INTO categoria(nombre) VALUES ("Vacaciones");
INSERT INTO categoria(nombre) VALUES ("Cinema");
INSERT INTO categoria(nombre) VALUES ("Electrónica");

SELECT * FROM categoria;

## TipoPromocion
DROP TABLE IF EXISTS tipoPromocion;
CREATE TABLE IF NOT EXISTS tipoPromocion(
	id INT AUTO_INCREMENT NOT NULL,
    tipo VARCHAR(15) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO tipoPromocion(tipo) VALUES ("Descuento");
INSERT INTO tipoPromocion(tipo) VALUES ("Costo Rebajado");

SELECT * FROM tipoPromocion;

## Direccion
DROP TABLE IF EXISTS direccion;
CREATE TABLE IF NOT EXISTS direccion(
	id INT AUTO_INCREMENT NOT NULL,
    calle VARCHAR(30) NOT NULL,
    numero VARCHAR(5) NOT NULL,
    codigoPostal VARCHAR(5) NOT NULL,
    colonia VARCHAR(50) NULL,
    latitud VARCHAR(15) NOT NULL,
    longitud VARCHAR(15) NOT NULL,
    idCiudad INT,
    PRIMARY KEY (id),
    FOREIGN KEY (idCiudad) REFERENCES ciudad(id)
);

INSERT INTO direccion(calle, numero, codigoPostal, colonia, latitud, longitud, idCiudad) VALUES ("Calle UNO", "32", "91180", "Centenario", "19.55236628945", "-96.8894587793", 1);
INSERT INTO direccion(calle, numero, codigoPostal, colonia, latitud, longitud, idCiudad) VALUES ("Calzada Independencia Norte", "582", "44360", "J.M. Echauri", "20.68493267562", "-103.336432291", 11);

SELECT * FROM direccion;

## Empresa
DROP TABLE IF EXISTS empresa;
CREATE TABLE IF NOT EXISTS empresa(
	id INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(30) NOT NULL,
    nombreComercial VARCHAR(30) NOT NULL,
    logo LONGBLOB NOT NULL,
    nombreRepresentanteLegal VARCHAR(50) NOT NULL,
    correo VARCHAR(50) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    paginaWeb VARCHAR(100) NULL,
    rfc VARCHAR(13) NOT NULL UNIQUE,
    idEstatus INT,
    idDireccion INT,
    PRIMARY KEY (id),
    FOREIGN KEY (idEstatus) REFERENCES estatus(id),
    FOREIGN KEY (idDireccion) REFERENCES direccion(id)
);

INSERT INTO empresa(nombre, nombreComercial, logo, nombreRepresentanteLegal, correo, telefono, paginaWeb, rfc, idEstatus, idDireccion) VALUES ("Tacos y Gorditas Vázquez", "Tacos y Gorditas Vázquez", "0", "Lucía Hernández Hernández", "tacosgorditas@gmail.com", "2288497572", "www.tacosgorditasvazquez.com", "TGV000518HE3", 1, 2);

SELECT * FROM empresa;

## Sucursal
DROP TABLE IF EXISTS sucursal;
CREATE TABLE IF NOT EXISTS sucursal(
	id INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    nombreEncargado VARCHAR(50) NOT NULL,
    idDireccion INT,
    idEmpresa INT,
    PRIMARY KEY (id),
    FOREIGN KEY (idDireccion) REFERENCES direccion(id),
    FOREIGN KEY (idEmpresa) REFERENCES empresa(id)
);

INSERT INTO sucursal(nombre, telefono, nombreEncargado, idDireccion, idEmpresa) VALUES ("Tacos Vázquez", "2288475267", "Jorge Solano Méndez", 1, 1);
INSERT INTO sucursal(nombre, telefono, nombreEncargado, idDireccion, idEmpresa) VALUES ("Gorditas Vázquez", "2288696274", "María Vázquez Pérez", 1, 1);

SELECT * FROM sucursal;

## Usuario
DROP TABLE IF EXISTS usuario;
CREATE TABLE IF NOT EXISTS usuario(
	id INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    apellidoPaterno VARCHAR(20) NOT NULL,
    apellidoMaterno VARCHAR(20) NOT NULL,
    curp VARCHAR(18) NOT NULL UNIQUE,
    correo VARCHAR(50) NOT NULL UNIQUE,
    username VARCHAR(20) NOT NULL UNIQUE,
    contrasenia VARCHAR(30) NOT NULL,
    idRol INT,
    idEmpresa INT,
    PRIMARY KEY (id),
    FOREIGN KEY (idRol) REFERENCES rol(id),
    FOREIGN KEY (idEmpresa) REFERENCES empresa(id)
);

INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, curp, correo, username, contrasenia, idRol, idEmpresa) VALUES ("Gustavo", "Lozano", "Hernández", "LOHG950719HVZZRS01", "gustavo@admin.com", "Gus", "adminGus", 1, 1);
INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, curp, correo, username, contrasenia, idRol, idEmpresa) VALUES ("Jonathan", "Montano", "Montano", "MOMJ970723HJCNNN04", "jonathan@admin.com", "JB9", "Jonaboy", 2, 1);

SELECT * FROM usuario;

## Cliente
DROP TABLE IF EXISTS cliente;
CREATE TABLE IF NOT EXISTS cliente(
	id INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    apellidoPaterno VARCHAR(20) NOT NULL,
    apellidoMaterno VARCHAR(20) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    correo VARCHAR(50) NOT NULL,
    fechaNacimiento DATE NOT NULL,
    contrasenia VARCHAR(30) NOT NULL,
    idDireccion INT,
    PRIMARY KEY (id),
    FOREIGN KEY (idDireccion) REFERENCES direccion(id)
);

INSERT INTO cliente(nombre, apellidoPaterno, apellidoMaterno, telefono, correo, fechaNacimiento, contrasenia, idDireccion) VALUES ("Liam", "Pérez", "Sulvarán", "2288586861", "liam@gmail.com", "2002-05-18", "liam", 1);

SELECT * FROM cliente;

## Promocion
DROP TABLE IF EXISTS promocion;
CREATE TABLE IF NOT EXISTS promocion(
	id INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(30) NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    imagen LONGBLOB NOT NULL,
    fechaInicio DATE NOT NULL,
    fechaTermino DATE NOT NULL,
    restricciones VARCHAR(200) NULL,
    numeroCupones INT NOT NULL,
    codigo VARCHAR(8) NOT NULL UNIQUE,
    valor FLOAT NOT NULL,
    idEstatus INT,
    idCategoria INT,
    idEmpresa INT,
    idTipoPromocion INT,
    PRIMARY KEY (id),
    FOREIGN KEY (idEstatus) REFERENCES estatus(id),
    FOREIGN KEY (idCategoria) REFERENCES categoria(id),
    FOREIGN KEY (idEmpresa) REFERENCES empresa(id),
    FOREIGN KEY (idTipoPromocion) REFERENCES tipoPromocion(id)
);

INSERT INTO promocion(nombre, descripcion, imagen, fechaInicio, fechaTermino, restricciones, numeroCupones, codigo, valor, idEstatus, idCategoria, idEmpresa, idTipoPromocion) VALUES ("Viaje Gratis", "Obtén un vuelo gratis a cualquier parte del mundo", "0", "2023-12-04", "2023-12-06", "Aplica solo en Guadalajara", 2, "VIAJFR33", "100", 1, 1, 1, 1);

SELECT * FROM promocion;

## PromocionSucursal
DROP TABLE IF EXISTS promocionSucursal;
CREATE TABLE IF NOT EXISTS promocionSucursal(
	idPromocion INT,
    idSucursal INT,
    PRIMARY KEY (idPromocion, idSucursal),
    FOREIGN KEY (idPromocion) REFERENCES promocion(id),
    FOREIGN KEY (idSucursal) REFERENCES sucursal(id)
);

INSERT INTO promocionSucursal VALUES (1, 1);

SELECT * FROM promocionSucursal;

## Proyecciones
USE cuponsmart;

SELECT * FROM empresa;

UPDATE empresa SET logo = "" WHERE id = 17;