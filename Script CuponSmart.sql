DROP DATABASE cuponsmart;
CREATE DATABASE IF NOT EXISTS cuponsmart;

USE cuponsmart;

CREATE USER IF NOT EXISTS 'admin'@'localhost' IDENTIFIED BY 'equipo6';
GRANT ALL PRIVILEGES ON cuponsmart.* TO 'admin'@'localhost';
FLUSH PRIVILEGES;

SHOW GRANTS FOR admin@'localhost';

DROP TABLE IF EXISTS estado;
CREATE TABLE IF NOT EXISTS estado(
	id INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS municipio;
CREATE TABLE IF NOT EXISTS municipio(
	id INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(20) NOT NULL,
	idEstado INT,
    PRIMARY KEY (id),
    FOREIGN KEY (idEstado) REFERENCES estado(id)
);

DROP TABLE IF EXISTS ciudad;
CREATE TABLE IF NOT EXISTS ciudad(
	id INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(30) NOT NULL,
    idMunicipio INT,
    PRIMARY KEY (id),
    FOREIGN KEY (idMunicipio) REFERENCES municipio(id)
);

DROP TABLE IF EXISTS rol;
CREATE TABLE IF NOT EXISTS rol(
	id INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS estatus;
CREATE TABLE IF NOT EXISTS estatus(
	id INT AUTO_INCREMENT NOT NULL,
    estado VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS categoria;
CREATE TABLE IF NOT EXISTS categoria(
	id INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS tipoPromocion;
CREATE TABLE IF NOT EXISTS tipoPromocion(
	id INT AUTO_INCREMENT NOT NULL,
    tipo VARCHAR(15) NOT NULL,
    PRIMARY KEY (id)
);

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

DROP TABLE IF EXISTS promocionSucursal;
CREATE TABLE IF NOT EXISTS promocionSucursal(
	idPromocion INT,
    idSucursal INT,
    PRIMARY KEY (idPromocion, idSucursal),
    FOREIGN KEY (idPromocion) REFERENCES promocion(id),
    FOREIGN KEY (idSucursal) REFERENCES sucursal(id)
);