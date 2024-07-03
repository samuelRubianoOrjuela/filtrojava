DROP DATABASE IF EXISTS cinecampus;
CREATE DATABASE cinecampus;
USE cinecampus;

CREATE TABLE genero (
    id INT PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(50)
);

CREATE TABLE pais (
    id INT PRIMARY KEY AUTO_INCREMENT,
    descripcion  VARCHAR(50)
);

CREATE TABLE actor (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre  VARCHAR(40),
    idnacionalidad INT,
    edad INT,
    idgenero INT,
    CONSTRAINT fk_actor_genero FOREIGN KEY (idgenero) REFERENCES genero (id),
    CONSTRAINT fk_actor_pais FOREIGN KEY (idnacionalidad) REFERENCES pais (id)
);

CREATE TABLE tipoactor (
    id INT PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(50)
);

CREATE TABLE formato (
    id INT PRIMARY KEY AUTO_INCREMENT,
    descripcion  VARCHAR(50),
    cantidad INT
);

CREATE TABLE pelicula (
    id  INT PRIMARY KEY AUTO_INCREMENT,
    codInterno VARCHAR(5),
    nombre VARCHAR(50),
    duracion VARCHAR(20),
    sinopsis TEXT
);

CREATE TABLE peliculaFormato (
    idpelicula  INT PRIMARY KEY AUTO_INCREMENT,
    idformato  INT,
    cantidad INT,
    CONSTRAINT fk_peliculaFormato_pelicula FOREIGN KEY (idpelicula) REFERENCES pelicula (id),
    CONSTRAINT fk_peliculaFormato_formato FOREIGN KEY (idformato) REFERENCES  formato (id)
);

CREATE TABLE peliculaProtagonista (
    idpelicula INT PRIMARY KEY AUTO_INCREMENT,
    idprotagonista INT,
    idtipoactor INT,
    CONSTRAINT fk_peliculaProtagonista_pelicula FOREIGN KEY (idpelicula) REFERENCES pelicula (id),
    CONSTRAINT fk_peliculaProtagonista_actor FOREIGN KEY (idprotagonista) REFERENCES actor (id),
    CONSTRAINT fk_peliculaProtagonista_tipoActor FOREIGN KEY (idtipoactor) REFERENCES tipoactor (id)
);
 
INSERT INTO pais (descripcion) VALUES
    ('Colombia'),
    ('Venezuela');

INSERT INTO genero (descripcion) VALUES
    ('hombre'),
    ('mujer');
    