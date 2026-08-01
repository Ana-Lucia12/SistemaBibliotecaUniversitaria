/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import modelo.EstadoMaterial;
import modelo.Libro;
import modelo.MaterialBibliografico;
import modelo.Revista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

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
    
    // Metodo para listar materiales
    public List<MaterialBibliografico> listar() {
    
        List<MaterialBibliografico> listaMateriales = new ArrayList<>();
        
        String sql = "SELECT * FROM materiales";
        
        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
            ) {
            
            while (rs.next()) {
                
                int id = rs.getInt("id_material");
                String codigo = rs.getString("codigo");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                int anio = rs.getInt("anio_publicacion");
                String tipoMaterial = rs.getString("tipo_material");
                
                EstadoMaterial estado = EstadoMaterial.valueOf(
                        rs.getString("estado")
                );
                
                MaterialBibliografico material;
                
                if (tipoMaterial.equals("LIBRO")) {
                    
                    String isbn = rs.getString("isbn");
                    
                    material = new Libro (
                            id,
                            codigo,
                            titulo,
                            autor,
                            anio,
                            estado,
                            isbn
                    );
                } else {
                    
                    int numeroEdicion = rs.getInt("numero_edicion");
                    
                    material = new Revista(
                            id,
                            codigo,
                            titulo,
                            autor,
                            anio,
                            estado,
                            numeroEdicion
                    );
                }
                
                listaMateriales.add(material);
            }           
        } catch (SQLException e) {
            System.out.println("Error al listar los materiales");
            e.printStackTrace();
        }
        return listaMateriales;
    }
    
    // Metodo para buscar por codigo
    public MaterialBibliografico buscarPorCodigo(String codigoBuscado) {

        String sql = "SELECT * FROM materiales WHERE codigo = ?";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)
            ) {

            ps.setString(1, codigoBuscado);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    int id = rs.getInt("id_material");
                    String codigo = rs.getString("codigo");
                    String titulo = rs.getString("titulo");
                    String autor = rs.getString("autor");
                    int anio = rs.getInt("anio_publicacion");
                    String tipoMaterial = rs.getString("tipo_material");

                    EstadoMaterial estado = EstadoMaterial.valueOf(
                            rs.getString("estado")
                    );

                    if (tipoMaterial.equals("LIBRO")) {

                        String isbn = rs.getString("isbn");

                        return new Libro(
                                id,
                                codigo,
                                titulo,
                                autor,
                                anio,
                                estado,
                                isbn
                        );

                    } else {

                        int numeroEdicion = rs.getInt("numero_edicion");

                        return new Revista(
                                id,
                                codigo,
                                titulo,
                                autor,
                                anio,
                                estado,
                                numeroEdicion
                        );
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el material");
            e.printStackTrace();
        }

        return null;
    }
    
    // Metodo para actualizar el estado de un material
    public boolean actualizarEstado( int idMaterial, EstadoMaterial nuevoEstado) {
        
        String sql = "UPDATE materiales "
                + "SET estado = ? "
                + "WHERE id_material = ?";
        
        try(
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
            ) {
            
            ps.setString(1, nuevoEstado.name());
            ps.setInt(2, idMaterial);
            
            int filasActualizadas = ps.executeUpdate();
            
            return filasActualizadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar el estado del material");
            e.printStackTrace();
            return false;
        }
    }
}
