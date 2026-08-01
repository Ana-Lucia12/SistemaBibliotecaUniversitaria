/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author yarie
 */

public class Estudiante extends Usuario{
    
    //Constructor
        public Estudiante(int id, String identificacion, String nombre, String correo, boolean activo) {
            
        super(id, identificacion, nombre, correo, activo);
    }

    // Método sobreescrito para obtener el límite de prestamos de materiales bibliográficos.
    @Override
    public int obtenerLimitePrestamos() {
        return 3;
    }
    
}
