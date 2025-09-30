/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Postulante;

import DataBaseConection.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author fabic
 */
public class logicaPostulante {
    
    public static boolean guardarDatosEstudiante( String nombres, String apellidos,String fechanacimiento, String telefono, String colegio,String direccion, String carrera, String cedula) {

        String checkSql = "SELECT COUNT(*) FROM postulantes WHERE cedula = ?";
        String insertSql = "INSERT INTO postulantes (nombres, apellidos, fechanacimiento, telefono, colegio, direccion, carrera, cedula) "
                         + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(checkSql)) {
                ps.setString(1, cedula);
                ResultSet rs = ps.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(null, "Esta cedula ya está registrada");
                    return false;
                }
            }

            try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                ps.setString(1, nombres);
                ps.setString(2, apellidos);
                ps.setString(3, fechanacimiento);
                ps.setString(4, telefono);
                ps.setString(5, colegio);
                ps.setString(6, direccion);
                ps.setString(7, carrera);
                ps.setString(8, cedula);

                int filas = ps.executeUpdate();
                if (filas > 0) {
                    JOptionPane.showMessageDialog(null, "Los datos se guardaron correctamente");
                    return true;
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se guardaron los datos correctamente: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
    
    
    
    public static boolean asignacionAula(int idPostulante, String apellido, String fecha, String hora, String lugar) {
    String aula;
    char inicial = Character.toUpperCase(apellido.charAt(0));
    if (inicial >= 'A' && inicial <= 'M') {
        aula = "Aula 101";
    } else {
        aula = "Aula 102";
    }

    String sql = "INSERT INTO asignacionAula (id_postulante, fecha, hora, lugar, aula) VALUES (?, ?, ?, ?, ?)";

    try (Connection con = ConexionDB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idPostulante);
        ps.setDate(2, java.sql.Date.valueOf(fecha)); 
        ps.setTime(3, java.sql.Time.valueOf(hora)); 
        ps.setString(4, lugar);
        ps.setString(5, aula);

        int filas = ps.executeUpdate();
        return filas > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    
    public static String verInformacionExamen(String cedula) {
        
    String info = "";
    String sqlPostulante = "SELECT id, nombre, apellidos FROM postulante WHERE cedula = ?";
    String sqlExamen = "SELECT fecha, hora, lugar, aula FROM asignacionAula WHERE id_postulante = ?";

    try (Connection con = ConexionDB.getConnection();
         PreparedStatement psPostulante = con.prepareStatement(sqlPostulante)) {

        psPostulante.setString(1, cedula);
        ResultSet rsPostulante = psPostulante.executeQuery();

        if (rsPostulante.next()) {
            int idPostulante = rsPostulante.getInt("id");
            String nombre = rsPostulante.getString("nombre");
            String apellidos = rsPostulante.getString("apellidos");

            try (PreparedStatement psExamen = con.prepareStatement(sqlExamen)) {
                psExamen.setInt(1, idPostulante);
                ResultSet rsExamen = psExamen.executeQuery();

                if (rsExamen.next()) {
                    info = "Postulante: " + nombre + " " + apellidos +
                           "\nFecha: " + rsExamen.getDate("fecha") +
                           "\nHora: " + rsExamen.getTime("hora") +
                           "\nLugar: " + rsExamen.getString("lugar") +
                           "\nAula: " + rsExamen.getString("aula");
                } else {
                    info = "No se encontró información de examen para " + nombre + " " + apellidos;
                }
            }

        } else {
            info = "Cédula no registrada.";
        }

    } catch (SQLException e) {
        e.printStackTrace();
        info = "Error al consultar la información.";
    }

    return info;
}
  
}

