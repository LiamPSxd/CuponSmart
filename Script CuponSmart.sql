## Proyecciones - Pruebas
USE cuponsmart;

##SELECT * FROM cliente;
##SELECT * FROM direccion;

## Script
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
INSERT INTO estado(nombre) VALUES ("Nuevo León");
INSERT INTO estado(nombre) VALUES ("Puebla");
INSERT INTO estado(nombre) VALUES ("Guanajuato");
INSERT INTO estado(nombre) VALUES ("Michoacán");
INSERT INTO estado(nombre) VALUES ("Chiapas");
INSERT INTO estado(nombre) VALUES ("Sonora");
INSERT INTO estado(nombre) VALUES ("Oaxaca");
INSERT INTO estado(nombre) VALUES ("Sinaloa");
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
INSERT INTO municipio(nombre, idEstado) VALUES ("Monterrey", 3);
INSERT INTO municipio(nombre, idEstado) VALUES ("San Nicolás", 3);
INSERT INTO municipio(nombre, idEstado) VALUES ("Puebla", 4);
INSERT INTO municipio(nombre, idEstado) VALUES ("Tehuacán", 4);
INSERT INTO municipio(nombre, idEstado) VALUES ("León", 5);
INSERT INTO municipio(nombre, idEstado) VALUES ("Irapuato", 5);
INSERT INTO municipio(nombre, idEstado) VALUES ("Morelia", 6);
INSERT INTO municipio(nombre, idEstado) VALUES ("Uruapan", 6);
INSERT INTO municipio(nombre, idEstado) VALUES ("Tuxtla Gutiérrez", 7);
INSERT INTO municipio(nombre, idEstado) VALUES ("Tapachula", 7);
INSERT INTO municipio(nombre, idEstado) VALUES ("Hermosillo", 8);
INSERT INTO municipio(nombre, idEstado) VALUES ("Ciudad Obregón", 8);
INSERT INTO municipio(nombre, idEstado) VALUES ("Oaxaca de Juárez", 9);
INSERT INTO municipio(nombre, idEstado) VALUES ("Salina Cruz", 9);
INSERT INTO municipio(nombre, idEstado) VALUES ("Culiacán", 10);
INSERT INTO municipio(nombre, idEstado) VALUES ("Mazatlán", 10);
INSERT INTO municipio(nombre, idEstado) VALUES ("El Salto", 11);
INSERT INTO municipio(nombre, idEstado) VALUES ("Guadalajara", 11);

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
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Monterrey", 5);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("San Nicolás de los Garza", 6);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Puebla de Zaragoza", 7);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Tehuacán", 8);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("León", 9);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Irapuato", 10);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Morelia", 11);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Uruapan", 12);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Tuxtla Gutiérrez", 13);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Tapachula", 14);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Hermosillo", 15);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Ciudad Obregón", 16);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Oaxaca de Juárez", 17);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Salina Cruz", 18);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Culiacán", 19);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Mazatlán", 20);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("El Salto", 21);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Las Pintitas", 21);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Guadalajara", 22);
INSERT INTO ciudad(nombre, idMunicipio) VALUES ("Membrillos", 22);


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
INSERT INTO categoria(nombre) VALUES ("Comida");
INSERT INTO categoria(nombre) VALUES ("Cafetería");
INSERT INTO categoria(nombre) VALUES ("Deportes");
INSERT INTO categoria(nombre) VALUES ("Lectura");
INSERT INTO categoria(nombre) VALUES ("Farmacia");
INSERT INTO categoria(nombre) VALUES ("Electrónica");
INSERT INTO categoria(nombre) VALUES ("Zapatería");
INSERT INTO categoria(nombre) VALUES ("Otros");

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
INSERT INTO direccion(calle, numero, codigoPostal, colonia, latitud, longitud, idCiudad) VALUES ("Calzada Independencia Norte", "582", "44360", "J.M. Echauri", "20.68493267562", "-103.336432291", 2);
INSERT INTO direccion(calle, numero, codigoPostal, colonia, latitud, longitud, idCiudad) VALUES ("Calle DOS", "45", "91000", "Centro", "19.5512345678", "-96.8887654321", 3);
INSERT INTO direccion(calle, numero, codigoPostal, colonia, latitud, longitud, idCiudad) VALUES ("Calle TRES", "78", "91200", "Colonia Nueva", "19.5539876543", "-96.8865432109", 4);
INSERT INTO direccion(calle, numero, codigoPostal, colonia, latitud, longitud, idCiudad) VALUES ("Avenida Principal", "123", "90010", "Zona Comercial", "19.5501112233", "-96.8877665544", 5);
INSERT INTO direccion(calle, numero, codigoPostal, colonia, latitud, longitud, idCiudad) VALUES ("Calle Cuatro", "56", "91300", "Barrio Antiguo", "19.5544332211", "-96.8855226699", 6);
INSERT INTO direccion(calle, numero, codigoPostal, colonia, latitud, longitud, idCiudad) VALUES ("Avenida de la Playa", "789", "91990", "Playa Bonita", "19.5577998855", "-96.8822113344", 7);
INSERT INTO direccion(calle, numero, codigoPostal, colonia, latitud, longitud, idCiudad) VALUES ("Calle Cinco", "101", "91450", "Vista Hermosa", "19.5566778899", "-96.8899776655", 8);
INSERT INTO direccion(calle, numero, codigoPostal, colonia, latitud, longitud, idCiudad) VALUES ("Paseo de la Montaña", "22", "90880", "Montañosa", "19.5588667799", "-96.8844552233", 9);
INSERT INTO direccion(calle, numero, codigoPostal, colonia, latitud, longitud, idCiudad) VALUES ("Avenida del Río", "154", "91670", "Río Grande", "19.5555444666", "-96.8811223344", 10);
INSERT INTO direccion(calle, numero, codigoPostal, colonia, latitud, longitud, idCiudad) VALUES ("Calle Seis", "32", "91780", "Viejo Barrio", "19.5533221100", "-96.8877665544", 11);
INSERT INTO direccion(calle, numero, codigoPostal, colonia, latitud, longitud, idCiudad) VALUES ("Boulevard de la Luna", "987", "91890", "Lunar", "19.5566554433", "-96.8800111222", 12);


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

INSERT INTO empresa(nombre, nombreComercial, logo, nombreRepresentanteLegal, correo, telefono, paginaWeb, rfc, idEstatus, idDireccion) VALUES ("Tacos y Gorditas Vázquez", "Tacos y Gorditas Vázquez", "", "Lucía Hernández Hernández", "tacosgorditas@gmail.com", "2288497572", "www.tacosgorditasvazquez.com", "TGV000518HE3", 1, 1);
INSERT INTO empresa(nombre, nombreComercial, logo, nombreRepresentanteLegal, correo, telefono, paginaWeb, rfc, idEstatus, idDireccion) VALUES ("Burger King", "Burger King", "", "Carlos Rodríguez", "info@burgerking.com", "2281452417", "www.burgerking.com", "BRK123456789", 1, 2);
INSERT INTO empresa(nombre, nombreComercial, logo, nombreRepresentanteLegal, correo, telefono, paginaWeb, rfc, idEstatus, idDireccion) VALUES ("Pizza Hut", "Pizza Hut", "", "Marta Sánchez", "info@pizzahut.com", "2281452419", "www.pizzahut.com", "PHU987654321", 1, 3);
INSERT INTO empresa(nombre, nombreComercial, logo, nombreRepresentanteLegal, correo, telefono, paginaWeb, rfc, idEstatus, idDireccion) VALUES ("Café del Sol", "Café del Sol", "", "Ana Gómez", "info@cafedelsol.com", "2281452430", "www.cafedelsol.com", "CDS456789012", 1, 4);
INSERT INTO empresa(nombre, nombreComercial, logo, nombreRepresentanteLegal, correo, telefono, paginaWeb, rfc, idEstatus, idDireccion) VALUES ("Panadería La Esquina", "La Esquina", "", "Javier Martínez", "info@laesquina.com", "2281452440", "www.laesquina.com", "ESQ345678901", 1, 5);
INSERT INTO empresa(nombre, nombreComercial, logo, nombreRepresentanteLegal, correo, telefono, paginaWeb, rfc, idEstatus, idDireccion) VALUES ("Sushi Express", "Sushi Express", "", "Elena Vargas", "info@sushiexpress.com", "2281452411", "www.sushiexpress.com", "SUE789012345", 1, 6);
INSERT INTO empresa(nombre, nombreComercial, logo, nombreRepresentanteLegal, correo, telefono, paginaWeb, rfc, idEstatus, idDireccion) VALUES ("Gimnasio Fitness Plus", "Fitness Plus", "", "Luis Ramírez", "info@fitnessplus.com", "2281489417", "www.fitnessplus.com", "FIT234567890", 1, 7);
INSERT INTO empresa(nombre, nombreComercial, logo, nombreRepresentanteLegal, correo, telefono, paginaWeb, rfc, idEstatus, idDireccion) VALUES ("Librería Cultural", "Cultural", "", "Laura Herrera", "info@culturallibreria.com", "2281962417", "www.culturallibreria.com", "LIB678901234", 1, 8);
INSERT INTO empresa(nombre, nombreComercial, logo, nombreRepresentanteLegal, correo, telefono, paginaWeb, rfc, idEstatus, idDireccion) VALUES ("Farmacia San José", "San José", "", "Mario Jiménez", "info@farmaciasanjose.com", "2281453317", "www.farmaciasanjose.com", "FSJ789012345", 1, 9);
INSERT INTO empresa(nombre, nombreComercial, logo, nombreRepresentanteLegal, correo, telefono, paginaWeb, rfc, idEstatus, idDireccion) VALUES ("Electrónicos TechCity", "TechCity", "", "Sofía Pérez", "info@techcity.com", "2281452237", "www.techcity.com", "TEC123456789", 1, 10);
INSERT INTO empresa(nombre, nombreComercial, logo, nombreRepresentanteLegal, correo, telefono, paginaWeb, rfc, idEstatus, idDireccion) VALUES ("Florería Primavera", "Primavera", "", "Alejandro Flores", "info@floreriaprimavera.com", "2281782417", "www.floreriaprimavera.com", "FLP987654321", 1, 11);


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
INSERT INTO sucursal(nombre, telefono, nombreEncargado, idDireccion, idEmpresa) VALUES ("Gorditas Vázquez", "2288696274", "María Vázquez Pérez", 12, 1);
INSERT INTO sucursal(nombre, telefono, nombreEncargado, idDireccion, idEmpresa) VALUES ("Burger King - Sucursal Centro", "2288694574", "María López", 2, 2);
INSERT INTO sucursal(nombre, telefono, nombreEncargado, idDireccion, idEmpresa) VALUES ("Pizza Hut - Sucursal Zona Norte", "2288678274", "Pedro Ramírez", 3, 3);
INSERT INTO sucursal(nombre, telefono, nombreEncargado, idDireccion, idEmpresa) VALUES ("Café del Sol - Sucursal Plaza del Sol", "2288906274", "Laura Gutiérrez", 4, 4);
INSERT INTO sucursal(nombre, telefono, nombreEncargado, idDireccion, idEmpresa) VALUES ("La Esquina - Sucursal Avenida Principal", "2288696883", "Carlos Soto", 5, 5);
INSERT INTO sucursal(nombre, telefono, nombreEncargado, idDireccion, idEmpresa) VALUES ("Sushi Express - Sucursal Playa Bonita", "2288672074", "Ana Torres", 6, 6);
INSERT INTO sucursal(nombre, telefono, nombreEncargado, idDireccion, idEmpresa) VALUES ("Fitness Plus - Sucursal Zona Fitness", "2288226274", "Javier González", 7, 7);
INSERT INTO sucursal(nombre, telefono, nombreEncargado, idDireccion, idEmpresa) VALUES ("Cultural - Sucursal Centro Histórico", "2288666474", "Marta García", 8, 8);
INSERT INTO sucursal(nombre, telefono, nombreEncargado, idDireccion, idEmpresa) VALUES ("San José - Sucursal Avenida Principal", "2288696200", "Luis Hernández", 9, 9);
INSERT INTO sucursal(nombre, telefono, nombreEncargado, idDireccion, idEmpresa) VALUES ("TechCity - Sucursal Zona Tecnológica", "2288626114", "Sofía Pérez", 10, 10);
INSERT INTO sucursal(nombre, telefono, nombreEncargado, idDireccion, idEmpresa) VALUES ("Florería Primavera - Sucursal Jardín", "2288699944", "Alejandro Flores", 11, 11);

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
INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, curp, correo, username, contrasenia, idRol, idEmpresa) VALUES ("Carlos", "Ramírez", "García", "RAGC910519HDFNRR08", "carlos@burgerking.com", "CRBG", "BurgerCarlos", 2, 2);
INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, curp, correo, username, contrasenia, idRol, idEmpresa) VALUES ("Marta", "Sánchez", "López", "SAML890421MDFTHR02", "marta@pizzahut.com", "MSL", "PizzaMarta", 2, 3);
INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, curp, correo, username, contrasenia, idRol, idEmpresa) VALUES ("Eduardo", "González", "Martínez", "GOME750312HDFNTD01", "eduardo@cafedelsol.com", "EGM", "SolEduardo", 2, 4);
INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, curp, correo, username, contrasenia, idRol, idEmpresa) VALUES ("Laura", "Martínez", "Soto", "MASL920630HDFNSL09", "laura@laesquina.com", "LMS", "EsquinaLaura", 2, 5);
INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, curp, correo, username, contrasenia, idRol, idEmpresa) VALUES ("Javier", "Torres", "Gómez", "TOGJ840721HDFNVR01", "javier@sushiexpress.com", "JTG", "ExpressJavier", 2, 6);
INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, curp, correo, username, contrasenia, idRol, idEmpresa) VALUES ("Ana", "Gutiérrez", "López", "GULA880512MDFNNS07", "ana@fitnessplus.com", "AGL", "PlusAna", 2, 7);
INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, curp, correo, username, contrasenia, idRol, idEmpresa) VALUES ("Carlos", "López", "García", "LOGC830625HDFNSL03", "carlos@culturallibreria.com", "CLG", "CulturalCarlos", 2, 8);
INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, curp, correo, username, contrasenia, idRol, idEmpresa) VALUES ("Marta", "Hernández", "Ramírez", "HERM890803HDFNRR07", "marta@farmaciasanjose.com", "MHR", "SanMarta", 2, 9);
INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, curp, correo, username, contrasenia, idRol, idEmpresa) VALUES ("Luis", "Pérez", "Gómez", "PEGL920714HDFNLS02", "luis@techcity.com", "LPG", "TechLuis", 2, 10);
INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, curp, correo, username, contrasenia, idRol, idEmpresa) VALUES ("Sofía", "Gómez", "Hernández", "GOHS850925MDFNFF00", "sofia@floreriaprimavera.com", "SGH", "PrimaveraSofia", 2, 11);

SELECT * FROM usuario;

## Cliente
DROP TABLE IF EXISTS cliente;
CREATE TABLE IF NOT EXISTS cliente(
	id INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    apellidoPaterno VARCHAR(20) NOT NULL,
    apellidoMaterno VARCHAR(20) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    correo VARCHAR(50) NOT NULL UNIQUE,
    fechaNacimiento DATE NOT NULL,
    contrasenia VARCHAR(30) NOT NULL,
    foto LONGBLOB NOT NULL,
    idDireccion INT,
    PRIMARY KEY (id),
    FOREIGN KEY (idDireccion) REFERENCES direccion(id)
);

INSERT INTO cliente(nombre, apellidoPaterno, apellidoMaterno, telefono, correo, fechaNacimiento, contrasenia, foto, idDireccion) VALUES ("Liam", "Pérez", "Sulvarán", "2288586861", "liam@gmail.com", "2002-05-18", "liam", "", 1);
INSERT INTO cliente(nombre, apellidoPaterno, apellidoMaterno, telefono, correo, fechaNacimiento, contrasenia, foto, idDireccion) VALUES ("Sophia", "García", "Hernández", "555-1111", "sophia@gmail.com", "1995-08-22", "sophia", "", 2);
INSERT INTO cliente(nombre, apellidoPaterno, apellidoMaterno, telefono, correo, fechaNacimiento, contrasenia, foto, idDireccion) VALUES ("Ethan", "Martínez", "López", "555-2222", "ethan@gmail.com", "1988-03-12", "ethan", "", 3);
INSERT INTO cliente(nombre, apellidoPaterno, apellidoMaterno, telefono, correo, fechaNacimiento, contrasenia, foto, idDireccion) VALUES ("Isabella", "Ramírez", "Soto", "555-3333", "isabella@gmail.com", "2000-11-05", "isabella", "", 4);
INSERT INTO cliente(nombre, apellidoPaterno, apellidoMaterno, telefono, correo, fechaNacimiento, contrasenia, foto, idDireccion) VALUES ("Mason", "Gutiérrez", "Torres", "555-4444", "mason@gmail.com", "1993-06-28", "mason", "", 5);
INSERT INTO cliente(nombre, apellidoPaterno, apellidoMaterno, telefono, correo, fechaNacimiento, contrasenia, foto, idDireccion) VALUES ("Ava", "López", "Gómez", "555-5555", "ava@gmail.com", "1999-01-15", "ava", "", 1);

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

INSERT INTO promocion(nombre, descripcion, imagen, fechaInicio, fechaTermino, restricciones, numeroCupones, codigo, valor, idEstatus, idCategoria, idEmpresa, idTipoPromocion) VALUES ("Viaje Gratis", "Obtén un vuelo gratis a cualquier parte del mundo", "", "2023-12-04", "2023-12-06", "Aplica solo en Guadalajara", 2, "VIAJFR33", "100", 1, 1, 1, 1);
INSERT INTO promocion(nombre, descripcion, imagen, fechaInicio, fechaTermino, restricciones, numeroCupones, codigo, valor, idEstatus, idCategoria, idEmpresa, idTipoPromocion) VALUES ("Descuento en Menú", "Ahorra un 20% en tu menú favorito", "", "2023-12-10", "2024-12-20", "Válido solo en sucursal Centro", 5, "BURG2024", "20", 1, 2, 2, 1);
INSERT INTO promocion(nombre, descripcion, imagen, fechaInicio, fechaTermino, restricciones, numeroCupones, codigo, valor, idEstatus, idCategoria, idEmpresa, idTipoPromocion) VALUES ("Menos $50 en Pizzas Grandes", "Recibe un descuento de 50 pesos al comprar pizzas grandes", "", "2023-12-05", "2023-12-15", "Aplica solo para llevar", 10, "PIZZA241", "50", 1, 2, 3, 2);
INSERT INTO promocion(nombre, descripcion, imagen, fechaInicio, fechaTermino, restricciones, numeroCupones, codigo, valor, idEstatus, idCategoria, idEmpresa, idTipoPromocion) VALUES ("Happy Hour de Café", "Disfruta de tus cafés favoritos a mitad de precio", "", "2023-12-08", "2024-12-10", "Válido de 5 pm a 7 pm", 8, "CAFEHAPY", "50", 1, 3, 4, 1);
INSERT INTO promocion(nombre, descripcion, imagen, fechaInicio, fechaTermino, restricciones, numeroCupones, codigo, valor, idEstatus, idCategoria, idEmpresa, idTipoPromocion) VALUES ("Panecillos de la Abuela", "Compra 1 docena y te descontamos $30", "", "2023-12-12", "2023-12-20", "Válido solo para panecillos tradicionales", 15, "PANABUEL", "30", 1, 3, 5, 2);
INSERT INTO promocion(nombre, descripcion, imagen, fechaInicio, fechaTermino, restricciones, numeroCupones, codigo, valor, idEstatus, idCategoria, idEmpresa, idTipoPromocion) VALUES ("Rolls a Mitad de Precio", "Todos los rolls a mitad de precio los martes", "", "2023-12-06", "2023-12-13", "Válido solo los martes", 12, "SUSHITUE", "50", 1, 3, 6, 1);
INSERT INTO promocion(nombre, descripcion, imagen, fechaInicio, fechaTermino, restricciones, numeroCupones, codigo, valor, idEstatus, idCategoria, idEmpresa, idTipoPromocion) VALUES ("Membresía Anual con Descuento", "Obtén un 30% de descuento en la membresía anual", "", "202-12-01", "2024-12-31", "Aplica para nuevos socios", 20, "FITNESS3", "30", 1, 4, 7, 1);
INSERT INTO promocion(nombre, descripcion, imagen, fechaInicio, fechaTermino, restricciones, numeroCupones, codigo, valor, idEstatus, idCategoria, idEmpresa, idTipoPromocion) VALUES ("Compra 2, Paga menos", "Lleva 2 libros y te descontamos $100", "", "2023-12-03", "2023-12-15", "Aplica en todos los géneros", 15, "LIBRO3X2", "100", 1, 5, 8, 2);
INSERT INTO promocion(nombre, descripcion, imagen, fechaInicio, fechaTermino, restricciones, numeroCupones, codigo, valor, idEstatus, idCategoria, idEmpresa, idTipoPromocion) VALUES ("Descuento en Medicamentos", "Obtén un 15% de descuento en todos los medicamentos", "", "2023-12-05", "2023-12-10", "Válido con receta médica", 10, "SALUD150", "15", 1, 6, 9, 1);
INSERT INTO promocion(nombre, descripcion, imagen, fechaInicio, fechaTermino, restricciones, numeroCupones, codigo, valor, idEstatus, idCategoria, idEmpresa, idTipoPromocion) VALUES ("Electrónicos a Precios Bajos", "Descuentos del 10% en toda la línea de productos", "", "2023-12-07", "2024-12-14", "No aplica en productos ya rebajados", 15, "TECHSALE", "25", 1, 7, 10, 1);
INSERT INTO promocion(nombre, descripcion, imagen, fechaInicio, fechaTermino, restricciones, numeroCupones, codigo, valor, idEstatus, idCategoria, idEmpresa, idTipoPromocion) VALUES ("Flores de Temporada", "Compra un ramo de flores de temporada y recibe un descuento de $30", "", "2023-12-02", "2024-12-10", "Válido solo para flores de temporada", 10, "FLORES10", "30", 1, 8, 11, 2);

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
