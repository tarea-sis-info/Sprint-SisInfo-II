package Adminiatrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompraValoradoAD {
    private static Connection getConn() throws SQLException {
        return conexionPagoBD.getConnection();
    }

    public static boolean verificarRegistro(String cedula) {
        final String sql = "SELECT 1 FROM postulantes WHERE cedula = ?";
        try (Connection con = getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean registrarPagoValorado(String cedula) {
        final String sql = "INSERT INTO pago (cedula, estadopago, montodepago, comprobante, fechapago) "
                + "VALUES (?, TRUE, 10, ?, now())";
        try (Connection con = getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cedula);
            ps.setString(2, cedula);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean registrarPagoInscripcionPorNombre(String nombreEntrada, double monto) {
        final String nombreNorm = nombreEntrada == null ? "" : nombreEntrada.trim();
        final String SQL_FIND =
            "SELECT cedula, TRIM(nombres) AS nombres, TRIM(apellidos) AS apellidos " +
            "FROM postulantes " +
            "WHERE TRIM(LOWER(nombres)) = TRIM(LOWER(?)) " +
            "   OR TRIM(LOWER(TRIM(nombres) || ' ' || TRIM(apellidos))) = TRIM(LOWER(?)) " +
            "LIMIT 1";
        final String SQL_INSERT_PAGO =
            "INSERT INTO pago (cedula, estadopago, montodepago, comprobante, nombre, fechapago) " +
            "VALUES (?, TRUE, ?, ?, ?, now())";
        final String SQL_UPDATE_POST =
            "UPDATE postulantes SET estadoexamen = 'HABILITADO' WHERE cedula = ?";

        try (Connection con = getConn()) {
            con.setAutoCommit(false);

            String cedula = null;
            String nombreCompleto = null;

            try (PreparedStatement psFind = con.prepareStatement(SQL_FIND)) {
                psFind.setString(1, nombreNorm);
                psFind.setString(2, nombreNorm);
                try (ResultSet rs = psFind.executeQuery()) {
                    if (rs.next()) {
                        cedula = rs.getString("cedula");
                        String nom = rs.getString("nombres");
                        String ape = rs.getString("apellidos");
                        if (ape != null && !ape.trim().isEmpty()) {
                            nombreCompleto = (nom == null ? "" : nom.trim()) + " " + ape.trim();
                        } else {
                            nombreCompleto = nom == null ? "" : nom.trim();
                        }
                    }
                }
            }

            if (cedula == null || cedula.isEmpty()) {
                con.rollback();
                return false; 
            }
            String comprobante = cedula;

            int inserted;
            try (PreparedStatement psIns = con.prepareStatement(SQL_INSERT_PAGO)) {
                psIns.setString(1, cedula);
                psIns.setDouble(2, monto);
                psIns.setString(3, comprobante);
                psIns.setString(4, (nombreCompleto == null || nombreCompleto.isEmpty()) ? nombreNorm : nombreCompleto);
                inserted = psIns.executeUpdate();
            }

            if (inserted <= 0) {
                con.rollback();
                return false;
            }
            try (PreparedStatement psUpd = con.prepareStatement(SQL_UPDATE_POST)) {
                psUpd.setString(1, cedula);
                psUpd.executeUpdate();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

