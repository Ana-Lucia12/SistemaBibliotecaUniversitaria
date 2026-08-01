/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author yarie
 */
public abstract class MaterialBibliografico implements Prestable{
    
    //Variables
    protected int id;
    protected String codigo;
    protected String titulo;
    protected String autor;
    protected int anio;
    protected EstadoMaterial estado;

    //Constructor
    public MaterialBibliografico(int id, String codigo, String titulo,
            String autor, int anio, EstadoMaterial estado) {

        this.id=id;
        this.codigo=codigo;
        this.titulo=titulo;
        this.autor=autor;
        this.anio=anio;
        this.estado=estado;
    }
    
}
