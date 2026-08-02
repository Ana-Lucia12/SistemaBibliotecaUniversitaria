# Sistema de Biblioteca Universitaria

## Integrantes

- Ana Lucía Vargas Rodríguez
- Yariela Matarrita Arias

## Descripción del proyecto

El Sistema de Biblioteca Universitaria es una aplicación de escritorio desarrollada en Java para administrar una biblioteca.

El sistema permite registrar y consultar usuarios y materiales bibliográficos. También permite gestionar préstamos y devoluciones, verificando que el material se encuentre disponible y que el usuario no haya alcanzado su límite de préstamos.

Existen dos tipos de usuarios:

- Estudiantes, con un límite de 3 préstamos activos.
- Profesores, con un límite de 5 préstamos activos.

Los materiales bibliográficos pueden ser libros o revistas.

## Funciones principales

- Registrar y consultar usuarios.
- Registrar y consultar libros y revistas.
- Buscar usuarios por identificación.
- Buscar materiales por código.
- Registrar préstamos.
- Validar la disponibilidad de los materiales.
- Validar el límite de préstamos de cada usuario.
- Registrar devoluciones.
- Actualizar el estado de los materiales.
- Eliminar materiales de prueba.
- Cargar préstamos en segundo plano mediante SwingWorker.
- Obtener autores sin repetir mediante una colección Set.

## Tecnologías utilizadas

- Java.
- Java Swing.
- JDBC.
- MySQL.
- MySQL Workbench.
- NetBeans.
- Git.
- GitHub.

## Organización del proyecto

El código se encuentra separado en los siguientes paquetes:

### modelo

Contiene las clases que representan la información del sistema, como:

- Usuario.
- Estudiante.
- Profesor.
- MaterialBibliografico.
- Libro.
- Revista.
- Prestamo.
- Interfaces y enums.

### datos

Contiene la conexión con MySQL y las clases encargadas de realizar las consultas a la base de datos:

- ConexionBD.
- UsuarioDAO.
- MaterialDAO.
- PrestamoDAO.

### negocio

Contiene las validaciones y reglas principales del sistema:

- PrestamoServicio.
- DevolucionServicio.
- MaterialServicio.

### excepciones

Contiene las excepciones personalizadas:

- MaterialNoDisponibleException.
- LimitePrestamosException.

### util

Contiene la carga concurrente de préstamos mediante SwingWorker:

- CargadorPrestamosWorker.

### presentacion

Contiene las ventanas, paneles, botones, tablas y demás componentes de la interfaz gráfica.

### principal

Contiene la clase Main encargada de iniciar la aplicación.

## Configuración de la base de datos

La base de datos utilizada se llama:

sistemaBiblioteca

El proyecto incluye un archivo: database.sql

Este archivo permite crear la base de datos, sus tablas, relaciones y datos iniciales.

Las tablas principales son:

usuarios.
materiales.
prestamos.

La tabla prestamos se relaciona con usuarios y `materiales mediante llaves foráneas.

## Pasos para crear la base de datos

- Abrir MySQL Workbench.
- Abrir una nueva pestaña de consultas.
- Abrir el archivo database.sql.
- Ejecutar todo el contenido del archivo.
- Comprobar que se haya creado la base de datos sistemaBiblioteca.
- Verificar que existan las tablas usuarios, materiales y prestamos.

## Configuración de la conexión
  
En la clase ConexiónBD

se deben revisar los siguientes datos:

private static final String URL =
        "jdbc:mysql://localhost:3306/sistemaBiblioteca"; 

private static final String USER = "root";

private static final String PASS = "CONTRASEÑA_DE_MYSQL";

Se debe reemplazar CONTRASEÑA por la contraseña utilizada en MySQL Workbench.

También es necesario agregar la librería MySQL Connector/J dentro de las librerías del proyecto en NetBeans.

## Pasos para ejecutar
- Descargar o clonar el repositorio.
- Abrir el proyecto en NetBeans.
- Ejecutar el archivo database.sql en MySQL Workbench.
- Configurar el usuario y la contraseña en ConexionBD.java.
- Agregar MySQL Connector/J en las librerías del proyecto.
- Verificar que el servicio de MySQL esté activo.
- Ejecutar la clase Main.
- Utilizar las diferentes opciones de la interfaz para registrar usuarios, materiales, préstamos y devoluciones.

## Requisitos

- Java JDK instalado.
- NetBeans.
- MySQL Server.
- MySQL Workbench.
- MySQL Connector/J.
- Acceso a la base de datos local.

## Repositorio de Git
https://github.com/Ana-Lucia12/SistemaBibliotecaUniversitaria.git
