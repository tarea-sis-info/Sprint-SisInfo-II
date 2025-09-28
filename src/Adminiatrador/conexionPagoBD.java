/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Adminiatrador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class conexionPagoBD {
    private static String url = "jdbc:postgresql://db.svmjofkdktqtvtrfhtrv.supabase.co:5432/postgres";
    private static String usuario = "postgres";
    private static String contraseña = "sisinfoII2025";

    public static Connection conectar() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Conexión exitosa");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con; 
    }
    public static Connection getConnection() {
    return conectar();
}

}
