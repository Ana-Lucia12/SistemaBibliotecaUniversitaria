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
    protected boolean activo;

    //Constructor
    public Usuario(int id, String identificacion, String nombre, String correo, boolean activo) {
        this.id = id;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.correo = correo;
        this.activo = activo;
    }
    
    // Getters

    public int getId() {
        return id;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public boolean isActivo() {
        return activo;
    }
    
    // Setters  

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    
    public void setActivo(boolean activo) {    
        this.activo = activo;
    }

    //Método para obtener limite de préstamos de materiales bibliográficos
    public abstract int obtenerLimitePrestamos();
}
