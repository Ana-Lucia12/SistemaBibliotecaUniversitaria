/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author yarie
 */
public class Libro extends MaterialBibliografico{
    
    //Variable propia
    private String isbn;

    //Constructor
    public Libro(int id, String codigo, String titulo,
            String autor, int anio,
            EstadoMaterial estado,String isbn){

        super(id,codigo,titulo,autor,anio,estado, TipoMaterial.LIBRO);

        this.isbn = isbn;
    }
    
    // Getters y Setters propios del libro

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    

    //Métodos sobreescritos
    @Override
    public boolean estaDisponible() {
        return estado==EstadoMaterial.DISPONIBLE;
    }

    @Override
    public void prestar() {
        estado=EstadoMaterial.PRESTADO;
    }

    @Override
    public void devolver() {
        estado=EstadoMaterial.DISPONIBLE;
    }
    
}
