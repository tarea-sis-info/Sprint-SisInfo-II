/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admisionuniversitariaacademica;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VerificacionRegistro {

    // Método para verificar si un postulante está registrado en la BD
    public boolean verificarRegistro(String documento) {
        boolean registrado = false;

        try {
            // Conexión a la base de datos (ajusta a tu configuración)
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/miBD", "usuario", "password");

            // Consulta preparada
            String sql = "SELECT * FROM postulantes WHERE documento = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, documento);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                registrado = true; // sí existe
            }

            // Cerrar recursos
            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error en la verificación: " + e.getMessage());
        }

        return registrado;
    }

    // Pequeña prueba
    public static void main(String[] args) {
        VerificacionRegistro v = new VerificacionRegistro();

        if (v.verificarRegistro("123456")) {
            System.out.println("Postulante está registrado");
        } else {
            System.out.println("Postulante NO está registrado");
        }
    }
}