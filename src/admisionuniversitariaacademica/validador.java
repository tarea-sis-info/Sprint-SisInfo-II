package admisionuniversitariaacademica;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date; 
import admisionuniversitariaacademica.conexionDB;

public class validador extends JFrame {

    private JTextField txtApellidos, txtNombres, txtFechaNacimiento, txtTelefono; 
    private JTextField txtColegio, txtDireccion, txtCarrera; 
    private JButton btnGuardar; 
    private JLabel lblMensajes; 

    public validador() { 
        super("Formulario de Postulantes"); 
        setSize(500, 500); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); 

        txtApellidos = new JTextField(20); 
        txtNombres = new JTextField(20); 
        txtFechaNacimiento = new JTextField(20); 
        txtTelefono = new JTextField(20); 
        txtColegio = new JTextField(20); 
        txtDireccion = new JTextField(20); 
        txtCarrera = new JTextField(20); 

        btnGuardar = new JButton("Guardar y Validar"); 
        lblMensajes = new JLabel(" "); 

    
        add(new JLabel("Apellidos:")); 
        add(txtApellidos); 
        add(new JLabel("Nombres:")); 
        add(txtNombres);
        
     
        add(new JLabel("Fecha de Nacimiento (yyyy-MM-dd):")); 
        add(txtFechaNacimiento); 
        
        add(new JLabel("Teléfono (Solo números):")); 
        add(txtTelefono); 
        add(new JLabel("Colegio:")); 
        add(txtColegio); 
        add(new JLabel("Dirección:")); 
        add(txtDireccion); 
        add(new JLabel("Carrera:")); 
        add(txtCarrera); 

        add(btnGuardar); 
        add(lblMensajes); 

        btnGuardar.addActionListener(this::validarYGuardar); 
    } 

    private void validarYGuardar(ActionEvent e) { 
        StringBuilder errores = new StringBuilder(); 
        // USAMOS GUIONES
        final String DATE_FORMAT = "yyyy-MM-dd";


        if (txtApellidos.getText().trim().isEmpty()) errores.append("- Los Apellidos son obligatorios.\n"); 
        if (txtNombres.getText().trim().isEmpty()) errores.append("- Los Nombres son obligatorios.\n"); 
        if (txtFechaNacimiento.getText().trim().isEmpty()) errores.append("- La Fecha de Nacimiento es obligatoria.\n"); 
        if (txtTelefono.getText().trim().isEmpty()) errores.append("- El Teléfono es obligatorio.\n"); 
        if (txtColegio.getText().trim().isEmpty()) errores.append("- El Colegio es obligatorio.\n"); 
        if (txtDireccion.getText().trim().isEmpty()) errores.append("- La Dirección es obligatoria.\n"); 
        if (txtCarrera.getText().trim().isEmpty()) errores.append("- La Carrera es obligatoria.\n"); 

    
        if (!txtTelefono.getText().trim().isEmpty()) { 
            try { 
                Long.parseLong(txtTelefono.getText().trim()); 
            } catch (NumberFormatException ex) { 
                errores.append("- El Teléfono solo puede contener números enteros y válidos.\n"); 
            } 
        } 

    
        if (!txtFechaNacimiento.getText().trim().isEmpty()) { 
            SimpleDateFormat formatoFecha = new SimpleDateFormat(DATE_FORMAT); 
            formatoFecha.setLenient(false); 
            try { 
                formatoFecha.parse(txtFechaNacimiento.getText().trim()); 
            } catch (ParseException ex) { 
                errores.append("- La Fecha de Nacimiento no es válida (Use " + DATE_FORMAT + ").\n"); 
            } 
        } 

   
        if (errores.length() > 0) { 
            lblMensajes.setText("<html><font color='red'>❌ Errores:<br>" + errores.toString().replace("\n", "<br>") + "</font></html>"); 
        } else { 
            lblMensajes.setText("¡Validación Exitosa! Intentando guardar..."); 
            guardarDatosEnSupabase(); 
        } 
    } 

    private void limpiarCampos() {
        txtApellidos.setText("");
        txtNombres.setText("");
        txtFechaNacimiento.setText("");
        txtTelefono.setText("");
        txtColegio.setText("");
        txtDireccion.setText("");
        txtCarrera.setText("");
    }


    private void guardarDatosEnSupabase() { 
        String SQL = "INSERT INTO postulantes (apellidos, nombres, fechanacimiento, telefono, colegio, direccion, carrera) VALUES (?, ?, ?, ?, ?, ?, ?)"; 

        try (Connection conn = conexionDB.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(SQL)) { 

            if (conn == null) { 
                lblMensajes.setText("<html><font color='red'>❌ Error de Conexión a Supabase (Verifica credenciales).</font></html>"); 
                return; 
            } 



            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 


            java.util.Date parsedDate = dateFormat.parse(txtFechaNacimiento.getText().trim()); 
            

            String fechaISO = dateFormat.format(parsedDate); 


            pstmt.setString(1, txtApellidos.getText().trim()); 
            pstmt.setString(2, txtNombres.getText().trim()); 
            

            pstmt.setString(3, fechaISO); 
            
            pstmt.setLong(4, Long.parseLong(txtTelefono.getText().trim())); 
            pstmt.setString(5, txtColegio.getText().trim()); 
            pstmt.setString(6, txtDireccion.getText().trim()); 
            pstmt.setString(7, txtCarrera.getText().trim()); 

            int filasAfectadas = pstmt.executeUpdate(); 

            if (filasAfectadas > 0) { 
                lblMensajes.setText("<html><font color='green'>✅ Registro Exitoso en Supabase!</font></html>"); 
                limpiarCampos(); 
            } else { 
                lblMensajes.setText("<html><font color='red'>❌ Error: No se pudo insertar el registro.</font></html>"); 
            } 

        } catch (SQLException e) { 
  
            if ("23505".equals(e.getSQLState())) { 
                lblMensajes.setText("<html><font color='red'>❌ ERROR DE DUPLICIDAD: Los datos (Teléfono o Clave) ya existen en la base de datos. ¡Verifica el número de Teléfono!</font></html>"); 
            } else { 
                lblMensajes.setText("<html><font color='red'>❌ Error de DB: " + e.getMessage() + "</font></html>"); 
            } 
        } catch (Exception e) { 
            lblMensajes.setText("<html><font color='red'>❌ Error inesperado: " + e.getMessage() + "</font></html>"); 
        } 
    } 

    public static void main(String[] args) { 
        SwingUtilities.invokeLater(() -> { 
            new validador().setVisible(true); 
        }); 
    } 
}