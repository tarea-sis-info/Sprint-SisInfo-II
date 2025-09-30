package Adminiatrador; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DataBaseConection.ConexionDB; 

public class InterfazHabilitados extends JFrame { 

    private JPanel panelConsulta;
    private JTextField txtCedula; 
    private JButton btnConsultar;
    private JLabel lblMensajeConsulta;

    private JPanel panelEstado;
    private JLabel lblEstadoActual;
    private JButton btnImprimirBoleta;
    private JButton btnNuevaConsulta; 

    public InterfazHabilitados() {
        super("Consulta de Estado de Postulación");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        panelConsulta = new JPanel();
        panelConsulta.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 5, 5); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; panelConsulta.add(new JLabel("Cédula del Postulante:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; txtCedula = new JTextField(15); panelConsulta.add(txtCedula, gbc); 

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; 
        btnConsultar = new JButton("Consultar Estado");
        panelConsulta.add(btnConsultar, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; 
        lblMensajeConsulta = new JLabel(" ");
        lblMensajeConsulta.setForeground(Color.RED);
        panelConsulta.add(lblMensajeConsulta, gbc);

        btnConsultar.addActionListener(this::realizarConsulta);

        panelEstado = new JPanel();
        panelEstado.setLayout(new GridBagLayout());
        GridBagConstraints gbcEstado = new GridBagConstraints();
        gbcEstado.insets = new Insets(10, 10, 10, 10);
        gbcEstado.fill = GridBagConstraints.HORIZONTAL;

        gbcEstado.gridx = 0; gbcEstado.gridy = 0; gbcEstado.gridwidth = 2;
        panelEstado.add(new JLabel("Estado de tu Postulación:"), gbcEstado);
        
        gbcEstado.gridx = 0; gbcEstado.gridy = 1; gbcEstado.gridwidth = 2;
        lblEstadoActual = new JLabel("Cargando...");
        lblEstadoActual.setFont(new Font("Arial", Font.BOLD, 16));
        panelEstado.add(lblEstadoActual, gbcEstado);

        gbcEstado.gridx = 0; gbcEstado.gridy = 2; gbcEstado.gridwidth = 2;
        btnImprimirBoleta = new JButton("Imprimir Boleta");
        btnImprimirBoleta.setVisible(false); 
        panelEstado.add(btnImprimirBoleta, gbcEstado);
        btnImprimirBoleta.addActionListener(e -> JOptionPane.showMessageDialog(this, "¡Simulando impresión de boleta!"));


        gbcEstado.gridx = 0; gbcEstado.gridy = 3; gbcEstado.gridwidth = 2;
        btnNuevaConsulta = new JButton("Realizar Nueva Consulta");
        panelEstado.add(btnNuevaConsulta, gbcEstado);
        btnNuevaConsulta.addActionListener(e -> mostrarPanelConsulta());

        add(panelConsulta);
        mostrarPanelConsulta();
    }

    private void mostrarPanelConsulta() {
        setContentPane(panelConsulta);
        txtCedula.setText(""); 
        lblMensajeConsulta.setText(" ");
        revalidate();
        repaint();
    }

    private void mostrarPanelEstado(String estado) {
        lblEstadoActual.setText(estado);
        lblEstadoActual.setForeground(getColorForEstado(estado)); 

        if ("Pago pendiente".equalsIgnoreCase(estado)) {
            btnImprimirBoleta.setVisible(true);
        } else {
            btnImprimirBoleta.setVisible(false);
        }

        setContentPane(panelEstado);
        revalidate();
        repaint();
    }
    
    private Color getColorForEstado(String estado) {
        if ("Habilitado".equalsIgnoreCase(estado)) {
            return new Color(0, 150, 0); 
        } else if ("Pago pendiente".equalsIgnoreCase(estado)) {
            return Color.RED; 
        } else if ("En revisión".equalsIgnoreCase(estado)) {
            return Color.BLUE; 
        }
        return Color.BLACK; 
    }

    private void realizarConsulta(ActionEvent e) {
        String cedulaText = txtCedula.getText().trim(); 

        if (cedulaText.isEmpty()) {
            lblMensajeConsulta.setText("La Cédula es obligatoria.");
            return;
        }
        
        String SQL = "SELECT p.id, pa.estadopago " +
             "FROM postulantes p " +
             "LEFT JOIN pago pa ON p.cedula = pa.comprobante " +  
             "WHERE p.cedula = ?";
        
        String estadoFinal = "Cédula de Postulante no encontrada.";

        try (Connection conn = ConexionDB.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            if (conn == null) {
                lblMensajeConsulta.setText("<html><font color='red'>❌ Error de conexión a la base de datos.</font></html>");
                return;
            }

            pstmt.setString(1, cedulaText); 

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                
                Boolean estadoPago = (Boolean) rs.getObject("estadopago"); 
                
                if (estadoPago == null) {
                    estadoFinal = "En revisión"; 
                } else if (estadoPago == true) {
                    estadoFinal = "Habilitado";
                } else { 
                    estadoFinal = "Pago pendiente"; 
                }
                
                mostrarPanelEstado(estadoFinal);
                return; 
                
            } else {
                lblMensajeConsulta.setText(estadoFinal);
            }

        } catch (SQLException ex) {
            lblMensajeConsulta.setText("<html><font color='red'>❌ Error de DB durante la consulta: " + ex.getMessage() + "</font></html>");
            ex.printStackTrace();
        } catch (Exception ex) {
            lblMensajeConsulta.setText("<html><font color='red'>❌ Error inesperado: " + ex.getMessage() + "</font></html>");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfazHabilitados().setVisible(true);
        });
    }
}
