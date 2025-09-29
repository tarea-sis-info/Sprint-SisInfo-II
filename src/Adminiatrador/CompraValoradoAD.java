package Adminiatrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author User
 */
public class CompraValoradoAD {

    /**
     * @param args the command line arguments
     */
    

 public static boolean verificarRegistro(String cedula) {
        String sql = "SELECT 1 FROM postulantes WHERE cedula = ?";

        try (Connection con = conexionPagoBD.getConnection();
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
        String sql = "INSERT INTO pago (cedula,estadopago,montodepago,comprobante) VALUES (?,TRUE,10,?)";

        try (Connection con = conexionPagoBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cedula);
            ps.setString(2, cedula);
            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 
 
 /*public static void main(String[] args) {
    String cedula = "1234567";

    int id = verificarRegistro(cedula);

    if (id != -1) {
        System.out.println("El postulante con cédula " + cedula + " ya existe. ID en la base: " + id);
    } else {
        System.out.println("No existe ningún postulante con la cédula " + cedula);
    }
}
 */
 
/*public static void main(String[] args) {
    int idPostulante = 1; 

    boolean exito = registrarPagoValorado(idPostulante);

    if (exito) {
        System.out.println("Pago registrado correctamente para el postulante con ID: " + idPostulante);
    } else {
        System.out.println("No se pudo registrar el pago para el postulante con ID: " + idPostulante);
    }
}*/


}


    
    
    
    
    
    
    
    
    
    
    
   
    
