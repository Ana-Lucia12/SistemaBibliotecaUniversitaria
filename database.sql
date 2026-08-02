CREATE DATABASE IF NOT EXISTS sistemaBiblioteca;

USE sistemaBiblioteca;

CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    identificacion VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL,
    tipo_usuario ENUM('ESTUDIANTE', 'PROFESOR') NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE materiales (
    id_material INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    anio_publicacion INT NOT NULL,
    tipo_material ENUM('LIBRO', 'REVISTA') NOT NULL,
    estado ENUM('DISPONIBLE', 'PRESTADO') NOT NULL DEFAULT 'DISPONIBLE',
    isbn VARCHAR(20) NULL,
    numero_edicion INT NULL
);

CREATE TABLE prestamos (
    id_prestamo INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_material INT NOT NULL,
    fecha_prestamo DATE NOT NULL,
    fecha_devolucion_prevista DATE NOT NULL,
    fecha_devolucion_real DATE NULL,
    estado ENUM('ACTIVO', 'DEVUELTO') NOT NULL DEFAULT 'ACTIVO',

    CONSTRAINT fk_prestamo_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario),

    CONSTRAINT fk_prestamo_material
        FOREIGN KEY (id_material)
        REFERENCES materiales(id_material)
);

INSERT INTO usuarios
(identificacion, nombre, correo, tipo_usuario, activo)
VALUES
('1-1111-1111', 'María Rodríguez', 'maria@universidad.com', 'ESTUDIANTE', TRUE),
('2-2222-2222', 'Carlos Gómez', 'carlos@universidad.com', 'PROFESOR', TRUE);

INSERT INTO materiales
(codigo, titulo, autor, anio_publicacion, tipo_material,
 estado, isbn, numero_edicion)
VALUES
(
    'LIB001',
    'Introducción a Java',
    'Luis Fernández',
    2024,
    'LIBRO',
    'PRESTADO',
    '978-1234567890',
    NULL
),
(
    'REV001',
    'Tecnología Universitaria',
    'Editorial Académica',
    2025,
    'REVISTA',
    'DISPONIBLE',
    NULL,
    10
);

INSERT INTO prestamos
(id_usuario, id_material, fecha_prestamo, fecha_devolucion_prevista)
VALUES
(1, 1, '2026-07-31', '2026-08-14');