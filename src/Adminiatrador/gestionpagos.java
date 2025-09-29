/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Adminiatrador;

import DataBaseConection.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author fabic
 */
public class gestionpagos {
    public String consultarEstadoPago(String cedula) {
    String estado = "El postulante no tiene pagos registrados";

    try (Connection conn = ConexionDB.getConnection()) {
        String sql = "SELECT p.estadopago " + "FROM pago p " + "INNER JOIN postulantes po ON p.idpostulante = po.idpostulante " + "WHERE po.cedula = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, cedula);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            boolean estadopago = rs.getBoolean("estadopago");

            if (estadopago == true) {
                estado = "Estado de pago: pagado";
            } 
            if (estadopago == false) {
                estado = "Estado de pago: no pagado";
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        estado = "Error al ver el estado de pago";
    }

    return estado;
}

}
