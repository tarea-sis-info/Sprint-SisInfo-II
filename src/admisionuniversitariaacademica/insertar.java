package admisionuniversitariaacademica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class insertar {

    public static boolean guardarDatosEstudiante( String nombres, String apellidos,String fechanacimiento, String telefono, String colegio,String direccion, String carrera, String cedula) {

        String checkSql = "SELECT COUNT(*) FROM postulantes WHERE cedula = ?";
        String insertSql = "INSERT INTO postulantes (nombres, apellidos, fechanacimiento, telefono, colegio, direccion, carrera, cedula) "
                         + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = conexionDB.getConnection()) {
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

    public static void main(String[] args) {
        guardarDatosEstudiante(
    
            "Juan", 
            "Ramirez",  
            "2000-09-21", 
            "7136729", 
            "San Agustin", 
            "calle america", 
            "Ingeniería Civil",
            "1234567"
        );
    }
}
