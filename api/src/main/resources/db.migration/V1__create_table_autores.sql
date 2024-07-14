CREATE TABLE autores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    fechaDeNacimiento DATE NOT NULL,
    fechaDeMuerte DATE NOT NULL
);