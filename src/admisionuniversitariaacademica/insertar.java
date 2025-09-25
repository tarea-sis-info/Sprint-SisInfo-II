/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admisionuniversitariaacademica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class insertar {

    public static boolean guardarDatosEstudiante( long id, String nombres, String apellidos,String fechanacimiento, String telefono, String colegio,String direccion, String carrera) {

        String checkSql = "SELECT COUNT(*) FROM postulantes WHERE id = ?";
        String insertSql = "INSERT INTO postulantes (id, nombres, apellidos, fechanacimiento, telefono, colegio, direccion, carrera) "
                         + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = conexionDB.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(checkSql)) {
                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(null, "Este ID ya está registrado");
                    return false;
                }
            }

            try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                ps.setLong(1, id);
                ps.setString(2, nombres);
                ps.setString(3, apellidos);
                ps.setString(4, fechanacimiento);
                ps.setString(5, telefono);
                ps.setString(6, colegio);
                ps.setString(7, direccion);
                ps.setString(8, carrera);

                int filas = ps.executeUpdate();
                if (filas > 0) {
                    JOptionPane.showMessageDialog(null, "Los datos se guardaron corectamente");
                    return true;
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se guardaron los datos: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        guardarDatosEstudiante(
            235677, 
            "Juan", 
            "Ramirez",  
            "2000-09-21", 
            "7136729", 
            "San Agustin", 
            "calle america", 
            "Ingeniería Civil"
        );
    }
}
