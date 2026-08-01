/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/sistemaBiblioteca";
    
    private static final String USER = "root";
    
    private static final String PASS = "fresitA12."; // Agregar contraseña
    
    public static Connection conectar(){
        
        Connection con = null;
        try {
        
            con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexion exitosa");
                    
        } catch(SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace(); // Muestra el error de conexión
        }
        
        return con;
    }
    
}
