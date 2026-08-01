/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import modelo.Libro;
import modelo.MaterialBibliografico;
import modelo.Revista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class MaterialDAO {
    
    // Metodo para registrar material
    public boolean registrar(MaterialBibliografico material) {
        
        String sql = "INSERT INTO materiales "
                + "(codigo, titulo, autor, anio_publicacion, "
                + "tipo_material, estado, isbn, numero_edicion) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try(
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
            ) {
            
            ps.setString(1, material.getCodigo());
            ps.setString(2, material.getTitulo());
            ps.setString(3, material.getAutor());
            ps.setInt(4, material.getAnio());
            ps.setString(5, material.getTipoMaterial().name());
            ps.setString(6, material.getEstado().name());
            
            if (material instanceof Libro) {
                
                Libro libro = (Libro) material;
                
                ps.setString(7, libro.getIsbn());
                ps.setNull(8, Types.INTEGER);
                
            } else if (material instanceof Revista) {
                
                Revista revista = (Revista) material;
                
                ps.setNull(7, Types.VARCHAR);
                ps.setInt(8, revista.getNumeroEdicion());
                
            } else {
                throw new IllegalArgumentException(
                        "El tipo de material no es válido"
                );           
            }
            
            ps.executeUpdate();
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al registrar el material");
            e.printStackTrace();
            return false;
        }
    }
    
}
