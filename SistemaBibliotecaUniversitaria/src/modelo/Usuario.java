package modelo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author yarie
 */
public abstract class Usuario {

    //Variables
    protected int id;
    protected String identificacion;
    protected String nombre;
    protected String correo;

    //Constructor
    public Usuario(int id, String identificacion, String nombre, String correo) {
        this.id = id;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.correo = correo;
    }

    //Método para obtener limite de préstamos de materiales bibliográficos
    public abstract int obtenerLimitePrestamos();
}
