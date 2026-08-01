/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import modelo.Estudiante;
import modelo.Profesor;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    
    // Metodo para registrar usuarios
    public boolean registrar (Usuario usuario) {
        
        String sql = "INSERT INTO usuarios "  
                + "(identificacion, nombre, correo, tipo_usuario, activo) "
                + "VALUES (?, ?, ?, ?, ?)"; // la consulta registra un nuevo usuario en la tabla sql
        
        try (
                
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql) // preparedStatement es mas seguro para enviar datos
            ) {
            
            ps.setString(1, usuario.getIdentificacion());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getCorreo());
            
            if (usuario instanceof Estudiante) {
                ps.setString(4, "ESTUDIANTE");
            } else if (usuario instanceof Profesor) {
                ps.setString(4, "PROFESOR");
            } else {
                throw new IllegalArgumentException(
                        "El tipo de usuario no es válido"
                );
            }
            
            ps.setBoolean(5, usuario.isActivo());
            
            ps.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar el usuario");
             e.printStackTrace();
             return false;
        }
    }
    
    // Metodo para listar
    
    public List<Usuario> listar() {
        
        List<Usuario> listaUsuarios = new ArrayList<>();
    
        String sql = "SELECT * FROM usuarios";
    
        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery() // guarda temporalmente los resultados obtenidos desde MySQL.
            ) {

            while (rs.next()) {

                int id = rs.getInt("id_usuario");
                String identificacion = rs.getString("identificacion");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                String tipoUsuario = rs.getString("tipo_usuario");
                boolean activo = rs.getBoolean("activo");

                Usuario usuario;

                if(tipoUsuario.equals("ESTUDIANTE")) {

                    usuario = new Estudiante(
                            id,
                            identificacion,
                            nombre,
                            correo,
                            activo
                    );
                } else {
                    
                    usuario = new Profesor(
                            id,
                            identificacion,
                            nombre,
                            correo,
                            activo
                    );
                }

                listaUsuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los usuarios");
            e.printStackTrace();
        }

        return listaUsuarios;
        
    }
    
    // Metodo para buscar por identificacion
    public Usuario buscarPorIdentificacion(String identificacionBuscada) {
        
        String sql = "SELECT * FROM Usuarios WHERE identificacion = ?";
        
        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)
                ) {
            
            ps.setString(1, identificacionBuscada);
            
            try (ResultSet rs = ps.executeQuery()) {
                
                if (rs.next()) {
                    
                    int id = rs.getInt("id_usuario");
                    String identificacion = rs.getString("identificacion");
                    String nombre = rs.getString("nombre");
                    String correo = rs.getString("correo");
                    String tipoUsuario = rs.getString("tipo_usuario");
                    boolean activo = rs.getBoolean("activo");
                    
                    if (tipoUsuario.equals("ESTUDIANTE")) {
                        
                        return new Estudiante(
                                id,
                                identificacion,
                                nombre,
                                correo,
                                activo
                        );
                        
                    } else {
                        
                        return new Profesor(
                                id,
                                identificacion,
                                nombre,
                                correo,
                                activo
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar usuario.");
            e.printStackTrace();
        }
        
        return null;
    }
}
