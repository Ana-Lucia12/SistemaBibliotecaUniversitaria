/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import datos.MaterialDAO;
import modelo.MaterialBibliografico;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MaterialServicio {
    
    // Atributo
    private final MaterialDAO materialDAO;

    // Constructor
    public MaterialServicio() {
        this.materialDAO = new MaterialDAO();
    }

    // Devuelve una lista de autores sin repetirlos
    public Set<String> obtenerAutoresUnicos() {

        List<MaterialBibliografico> materiales =
                materialDAO.listar();

        Set<String> autores = new HashSet<>();

        for (MaterialBibliografico material : materiales) {

            autores.add(material.getAutor());
        }

        return autores;
    }
}
