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
    protected TipoMaterial tipoMaterial;

    //Constructor
    public MaterialBibliografico(int id, String codigo, String titulo,
            String autor, int anio, EstadoMaterial estado, TipoMaterial tipoMaterial) {

        this.id=id;
        this.codigo=codigo;
        this.titulo=titulo;
        this.autor=autor;
        this.anio=anio;
        this.estado=estado;
        this.tipoMaterial = tipoMaterial;
    }
    
    // Getters

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnio() {
        return anio;
    }

    public EstadoMaterial getEstado() {
        return estado;
    }

    public TipoMaterial getTipoMaterial() {
        return tipoMaterial;
    }
    
    // Setter para cambiar el estado

    public void setEstado(EstadoMaterial estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return titulo;
    }
}
