CREATE TABLE libros (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) UNIQUE NOT NULL,
    idioma VARCHAR(255) NOT NULL,
    numeroDeDescargas DOUBLE NOT NULL,
    autor_id BIGINT NOT NULL,
    FOREIGN KEY (autor_id) REFERENCES autores(id)
);