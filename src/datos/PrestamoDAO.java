/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import modelo.EstadoPrestamo;
import modelo.MaterialBibliografico;
import modelo.Prestamo;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {
    
    // Metodo para regsitrar prestamos
    public boolean registrar(Prestamo prestamo) {

        String sql = "INSERT INTO prestamos "
                + "(id_usuario, id_material, fecha_prestamo, "
                + "fecha_devolucion_prevista, fecha_devolucion_real, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                System.out.println("No se pudo abrir la conexión");
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, prestamo.getUsuario().getId());
                ps.setInt(2, prestamo.getMaterial().getId());

                ps.setDate(
                        3,
                        Date.valueOf(prestamo.getFechaPrestamo())
                );

                ps.setDate(
                        4,
                        Date.valueOf(prestamo.getFechaDevolucionPrevista())
                );

                if (prestamo.getFechaDevolucionReal() == null) {

                    ps.setNull(5, Types.DATE);

                } else {

                    ps.setDate(
                            5,
                            Date.valueOf(prestamo.getFechaDevolucionReal())
                    );
                }

                ps.setString(
                        6,
                        prestamo.getEstado().name()
                );

                int filasInsertadas = ps.executeUpdate();

                return filasInsertadas > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar el préstamo");
            e.printStackTrace();
            return false;
        }
    }
    
    // Metodo para contar prestamos activos por usuario
    public int contarPrestamosActivos(int idUsuario) {

        String sql = "SELECT COUNT(*) AS cantidad "
                + "FROM prestamos "
                + "WHERE id_usuario = ? "
                + "AND estado = 'ACTIVO'";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)
            ) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("cantidad");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al contar los préstamos activos");
            e.printStackTrace();
        }

        return 0;
    }
    
    // Metodo para listar
    public List<Prestamo> listar() {

        List<Prestamo> listaPrestamos = new ArrayList<>();

        String sql = "SELECT p.*, "
                + "u.identificacion, "
                + "m.codigo "
                + "FROM prestamos p "
                + "INNER JOIN usuarios u "
                + "ON p.id_usuario = u.id_usuario "
                + "INNER JOIN materiales m "
                + "ON p.id_material = m.id_material";

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        MaterialDAO materialDAO = new MaterialDAO();

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
            ) {

            while (rs.next()) {

                int id = rs.getInt("id_prestamo");

                String identificacion =
                        rs.getString("identificacion");

                String codigoMaterial =
                        rs.getString("codigo");

                Usuario usuario =
                        usuarioDAO.buscarPorIdentificacion(
                                identificacion
                        );

                MaterialBibliografico material =
                        materialDAO.buscarPorCodigo(
                                codigoMaterial
                        );

                java.time.LocalDate fechaPrestamo =
                        rs.getDate("fecha_prestamo")
                                .toLocalDate();

                java.time.LocalDate fechaDevolucionPrevista =
                        rs.getDate("fecha_devolucion_prevista")
                                .toLocalDate();

                java.time.LocalDate fechaDevolucionReal = null;

                Date fechaRealSQL =
                        rs.getDate("fecha_devolucion_real");

                if (fechaRealSQL != null) {
                    fechaDevolucionReal =
                            fechaRealSQL.toLocalDate();
                }

                EstadoPrestamo estado =
                        EstadoPrestamo.valueOf(
                                rs.getString("estado")
                        );

                Prestamo prestamo = new Prestamo(
                        id,
                        usuario,
                        material,
                        fechaPrestamo,
                        fechaDevolucionPrevista,
                        fechaDevolucionReal,
                        estado
                );

                listaPrestamos.add(prestamo);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar los préstamos");
            e.printStackTrace();
        }

        return listaPrestamos;
    }
    
    // Metodo para registrar la devolucion
    public boolean registrarDevolucion(
            int idPrestamo,
            java.time.LocalDate fechaDevolucionReal) {

        String sql = "UPDATE prestamos "
                + "SET fecha_devolucion_real = ?, "
                + "estado = 'DEVUELTO' "
                + "WHERE id_prestamo = ? "
                + "AND estado = 'ACTIVO'";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)
            ) {

            ps.setDate(
                    1,
                    Date.valueOf(fechaDevolucionReal)
            );

            ps.setInt(2, idPrestamo);

            int filasActualizadas = ps.executeUpdate();

            return filasActualizadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar la devolución");
            e.printStackTrace();
            return false;
        }
    }
    
    // Metodo para buscar un prestamo por id
    public Prestamo buscarPorId(int idPrestamo) {

        String sql = "SELECT p.*, "
                + "u.identificacion, "
                + "m.codigo "
                + "FROM prestamos p "
                + "INNER JOIN usuarios u "
                + "ON p.id_usuario = u.id_usuario "
                + "INNER JOIN materiales m "
                + "ON p.id_material = m.id_material "
                + "WHERE p.id_prestamo = ?";

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        MaterialDAO materialDAO = new MaterialDAO();

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)
            ) {

            ps.setInt(1, idPrestamo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    String identificacion =
                            rs.getString("identificacion");

                    String codigoMaterial =
                            rs.getString("codigo");

                    Usuario usuario =
                            usuarioDAO.buscarPorIdentificacion(
                                    identificacion
                            );

                    MaterialBibliografico material =
                            materialDAO.buscarPorCodigo(
                                    codigoMaterial
                            );

                    java.time.LocalDate fechaPrestamo =
                            rs.getDate("fecha_prestamo")
                                    .toLocalDate();

                    java.time.LocalDate fechaDevolucionPrevista =
                            rs.getDate("fecha_devolucion_prevista")
                                    .toLocalDate();

                    java.time.LocalDate fechaDevolucionReal = null;

                    Date fechaRealSQL =
                            rs.getDate("fecha_devolucion_real");

                    if (fechaRealSQL != null) {
                        fechaDevolucionReal =
                                fechaRealSQL.toLocalDate();
                    }

                    EstadoPrestamo estado =
                            EstadoPrestamo.valueOf(
                                    rs.getString("estado")
                            );

                    return new Prestamo(
                            rs.getInt("id_prestamo"),
                            usuario,
                            material,
                            fechaPrestamo,
                            fechaDevolucionPrevista,
                            fechaDevolucionReal,
                            estado
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el préstamo");
            e.printStackTrace();
        }

        return null;
    }
    
}
