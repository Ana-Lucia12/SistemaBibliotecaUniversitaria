/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author yarie
 */
public class Revista extends MaterialBibliografico{
    
    //Variable propia
    private int numeroEdicion;

    //Constructor
    public Revista(int id, String codigo, String titulo,
            String autor, int anio,EstadoMaterial estado,String isbn, int numeroEdicion){

        super(id,codigo,titulo,autor,anio,estado);

        this.numeroEdicion = numeroEdicion ;
    }

    //Métodos sobreescritos
    @Override
    public boolean estaDisponible() {
        return estado == EstadoMaterial.DISPONIBLE;
    }

    @Override
    public void prestar() {
        estado = EstadoMaterial.PRESTADO;
    }

    @Override
    public void devolver() {
        estado = EstadoMaterial.DISPONIBLE;
    }
    
}
