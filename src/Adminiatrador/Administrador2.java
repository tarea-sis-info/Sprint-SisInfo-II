/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Adminiatrador;


import static Adminiatrador.CompraValoradoAD.registrarPagoValorado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import Postulante.logicaPostulante;

/**
 *
 * @author User
 */
public class Administrador2 extends javax.swing.JFrame {
    private boolean modoHabilitar = false;
    private String c=null;
    public Administrador2() {
        initComponents();
        jButton1.setText("VERIFICAR PAGO");  
        jButton1.setEnabled(true);
    }

private void Verificar() {
    String nComprobante = txtComprobante.getText().trim();
    if (nComprobante.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese Numero de Comprobante.");
        return;
    }

    try  {
        boolean existe=consultarEstadoPago( nComprobante) ;

        if (existe) {
            c = nComprobante;
            modoHabilitar = true;
            jButton1.setText("HABILITAR"); 
            jButton1.setEnabled(true);
            JOptionPane.showMessageDialog(this, "ESTE COMPROBANTE SI EXISTE ");
           
        } else {
            c=null;
            modoHabilitar = false;
            jButton1.setText("VERIFICAR PAGO");
            jButton1.setEnabled(true);
            JOptionPane.showMessageDialog(this, "ESTE COMPROBANTE NO EXISTE ");
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al consultar: " + ex.getMessage());
    }
    }
   public static boolean consultarEstadoPago(String nComprobante) {
    final String sql = "SELECT 1 FROM pago WHERE comprobante = ?"; 
    try (Connection con = conexionPagoBD.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, nComprobante.trim());
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
public static boolean habilitar(String nComprobante) {
    String sqlSelect = "SELECT po.id, po.apellido FROM public.postulantes po " +
                      "JOIN public.pago pa ON TRIM(po.cedula) = TRIM(pa.comprobante) " +
                      "WHERE TRIM(pa.comprobante) = ?";
    
    final String sqlUpdate = 
        "UPDATE public.postulantes po " + 
        "SET estadoexamen = 'HABILITADO' " +  
        "FROM public.pago pa " + 
        "WHERE TRIM(pa.comprobante) = TRIM(?) " +
        "  AND TRIM(po.cedula) = TRIM(pa.comprobante) " +
        "RETURNING po.cedula";

    try (Connection con = conexionPagoBD.getConnection()) {
        
        int idPostulante = -1;
        String apellido = "";
        
        try (PreparedStatement psSelect = con.prepareStatement(sqlSelect)) {
            psSelect.setString(1, nComprobante.trim());
            try (ResultSet rs = psSelect.executeQuery()) {
                if (rs.next()) {
                    idPostulante = rs.getInt("id");
                    apellido = rs.getString("apellido");
                }
            }
        }
        
        try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {
            psUpdate.setString(1, nComprobante.trim());
            try (ResultSet rs = psUpdate.executeQuery()) {
                if (rs.next()) {
                    if (idPostulante != -1 && !apellido.isEmpty()) {
                        Postulante.logicaPostulante.asignacionAula(
                            idPostulante, apellido, 
                            "2024-01-20", "08:00:00", "Edificio Principal"
                        );
                    }
                    return true;
                }
            }
        }
        return false;
        
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtComprobante = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("COMPROBANTE DE PAGO ");

        jLabel2.setText("NUMERO COMPROBANTE:");

        txtComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComprobanteActionPerformed(evt);
            }
        });

        jButton1.setText("VERIFICAR PAGO");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(145, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(21, 21, 21)))))
                .addGap(113, 113, 113))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel1)
                .addGap(44, 44, 44)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jButton1)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComprobanteActionPerformed

    }//GEN-LAST:event_txtComprobanteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    if (!modoHabilitar) {
        Verificar(); 
    } else {
       boolean sepudo = habilitar(c); 
        JOptionPane.showMessageDialog(this, "Habilitado correctamente.");
        
        modoHabilitar = false;
        jButton1.setText("VERIFICAR PAGO");
        txtComprobante.setText("");
    }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txtComprobante;
    // End of variables declaration//GEN-END:variables

}
