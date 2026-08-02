/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import datos.MaterialDAO;
import datos.PrestamoDAO;
import excepciones.LimitePrestamosException;
import excepciones.MaterialNoDisponibleException;
import modelo.EstadoMaterial;
import modelo.EstadoPrestamo;
import modelo.MaterialBibliografico;
import modelo.Prestamo;
import modelo.Usuario;

import java.time.LocalDate;

public class PrestamoServicio {
    // atributos
    private final PrestamoDAO prestamoDAO;
    private final MaterialDAO materialDAO;
    
    // constructor
    public PrestamoServicio() {
        this.prestamoDAO = new PrestamoDAO();
        this.materialDAO = new MaterialDAO();
    }
    
    // Metodo para registrar el prestamo
    public boolean registrarPrestamo(
            Usuario usuario,
            MaterialBibliografico material,
            LocalDate fechaPrestamo,
            LocalDate fechaDevolucionPrevista)
            throws MaterialNoDisponibleException,
            LimitePrestamosException {

        if (usuario == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar un usuario"
            );
        }

        if (material == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar un material"
            );
        }

        if (!usuario.isActivo()) {
            throw new IllegalArgumentException(
                    "El usuario se encuentra inactivo"
            );
        }

        if (fechaPrestamo == null
                || fechaDevolucionPrevista == null) {

            throw new IllegalArgumentException(
                    "Las fechas son obligatorias"
            );
        }

        if (fechaDevolucionPrevista.isBefore(fechaPrestamo)) {

            throw new IllegalArgumentException(
                    "La fecha prevista de devolución no puede ser "
                    + "anterior a la fecha del préstamo"
            );
        }

        MaterialBibliografico materialActual =
                materialDAO.buscarPorCodigo(material.getCodigo());

        if (materialActual == null) {
            throw new IllegalArgumentException(
                    "El material no existe en la base de datos"
            );
        }

        if (!materialActual.estaDisponible()) {

            throw new MaterialNoDisponibleException(
                    "El material seleccionado no está disponible"
            );
        }

        int prestamosActivos =
                prestamoDAO.contarPrestamosActivos(usuario.getId());

        int limitePermitido =
                usuario.obtenerLimitePrestamos();

        if (prestamosActivos >= limitePermitido) {

            throw new LimitePrestamosException(
                    "El usuario alcanzó su límite de préstamos"
            );
        }

        Prestamo prestamo = new Prestamo(
                0,
                usuario,
                materialActual,
                fechaPrestamo,
                fechaDevolucionPrevista,
                null,
                EstadoPrestamo.ACTIVO
        );

        boolean prestamoRegistrado =
                prestamoDAO.registrar(prestamo);

        if (!prestamoRegistrado) {
            return false;
        }

        boolean estadoActualizado =
                materialDAO.actualizarEstado(
                        materialActual.getId(),
                        EstadoMaterial.PRESTADO
                );

        return estadoActualizado;
    }
    
}
