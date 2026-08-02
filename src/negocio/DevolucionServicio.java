/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import datos.MaterialDAO;
import datos.PrestamoDAO;
import modelo.EstadoMaterial;
import modelo.EstadoPrestamo;
import modelo.Prestamo;

import java.time.LocalDate;

public class DevolucionServicio {

    private final PrestamoDAO prestamoDAO;
    private final MaterialDAO materialDAO;

    public DevolucionServicio() {
        this.prestamoDAO = new PrestamoDAO();
        this.materialDAO = new MaterialDAO();
    }

    // metodo para registrar devoluciones
    public boolean registrarDevolucion(
            int idPrestamo,
            LocalDate fechaDevolucion) {

        if (idPrestamo <= 0) {
            throw new IllegalArgumentException(
                    "El ID del préstamo no es válido"
            );
        }

        if (fechaDevolucion == null) {
            throw new IllegalArgumentException(
                    "La fecha de devolución es obligatoria"
            );
        }

        Prestamo prestamo =
                prestamoDAO.buscarPorId(idPrestamo);

        if (prestamo == null) {
            throw new IllegalArgumentException(
                    "El préstamo no existe"
            );
        }

        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new IllegalArgumentException(
                    "El préstamo ya fue devuelto"
            );
        }

        if (fechaDevolucion.isBefore(
                prestamo.getFechaPrestamo())) {

            throw new IllegalArgumentException(
                    "La fecha de devolución no puede ser anterior a la fecha del préstamo"
            );
        }
        
        // Registrar la devolucion
        boolean devolucionRegistrada =
                prestamoDAO.registrarDevolucion(
                        idPrestamo,
                        fechaDevolucion
                );

        if (!devolucionRegistrada) {
            return false;
        }
        
        // cambia el material a disponible
        boolean materialActualizado =
                materialDAO.actualizarEstado(
                        prestamo.getMaterial().getId(),
                        EstadoMaterial.DISPONIBLE
                );

        return materialActualizado;
    }
}
