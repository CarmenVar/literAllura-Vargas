CREATE TABLE autores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    fecha_de_nacimiento DATE NOT NULL,
    fecha_de_muerte DATE NOT NULL
);