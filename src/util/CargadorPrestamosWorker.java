/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import datos.PrestamoDAO;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.SwingWorker;  // SwingWorker permite hacer una tarea pesada en segundo plano.
import modelo.Prestamo;

public class CargadorPrestamosWorker
        extends SwingWorker<List<Prestamo>, Void> {

    private final PrestamoDAO prestamoDAO;
    private final Consumer<List<Prestamo>> accionAlFinalizar;
    private final Consumer<String> accionSiFalla;

    public CargadorPrestamosWorker(
            Consumer<List<Prestamo>> accionAlFinalizar,
            Consumer<String> accionSiFalla) {

        this.prestamoDAO = new PrestamoDAO();
        this.accionAlFinalizar = accionAlFinalizar;
        this.accionSiFalla = accionSiFalla;
    }

    @Override
    protected List<Prestamo> doInBackground() {

        return prestamoDAO.listar();
    }

    @Override
    protected void done() {

        try {

            List<Prestamo> prestamos = get();

            if (accionAlFinalizar != null) {
                accionAlFinalizar.accept(prestamos);
            }

        } catch (Exception e) {

            if (accionSiFalla != null) {
                accionSiFalla.accept(
                        "No fue posible cargar los préstamos"
                );
            }

            e.printStackTrace();
        }
    }
}

