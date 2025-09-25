/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admisionuniversitariaacademica;

/**
 *
 * @author Jhon
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistroPostulantes extends JFrame {

    private JTextField txtNombre, txtCI, txtCorreo, txtFechaNac, txtTelefono, txtColegio, txtDireccion, txtCarrera;
    private JButton btnGuardar, btnImprimir;
    private Postulante postulante; // Objeto donde se guardan los datos

    public RegistroPostulantes() {
        setTitle("Formulario de Registro de Postulantes");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2, 5, 5));

        // Campos
        panel.add(new JLabel("Nombre y Apellido:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("CI:"));
        txtCI = new JTextField();
        panel.add(txtCI);

        panel.add(new JLabel("Correo electrónico:"));
        txtCorreo = new JTextField();
        panel.add(txtCorreo);

        panel.add(new JLabel("Fecha de Nacimiento:"));
        txtFechaNac = new JTextField();
        panel.add(txtFechaNac);

        panel.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panel.add(txtTelefono);

        panel.add(new JLabel("Colegio:"));
        txtColegio = new JTextField();
        panel.add(txtColegio);

        panel.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panel.add(txtDireccion);

        panel.add(new JLabel("Carrera:"));
        txtCarrera = new JTextField();
        panel.add(txtCarrera);

        // Botones
        btnGuardar = new JButton("Guardar");
        btnImprimir = new JButton("Imprimir");
        btnImprimir.setEnabled(false);

        panel.add(btnGuardar);
        panel.add(btnImprimir);

        // Acción Guardar
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postulante = new Postulante(
                        txtNombre.getText(),
                        txtCI.getText(),
                        txtCorreo.getText(),
                        txtFechaNac.getText(),
                        txtTelefono.getText(),
                        txtColegio.getText(),
                        txtDireccion.getText(),
                        txtCarrera.getText()
                );

                JOptionPane.showMessageDialog(null, "Datos guardados correctamente.");
                btnImprimir.setEnabled(true);
            }
        });

        // Acción Imprimir (solo muestra en consola los datos guardados)
        btnImprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (postulante != null) {
                    System.out.println("=== DATOS DEL POSTULANTE ===");
                    System.out.println("Nombre: " + postulante.getNombre());
                    System.out.println("CI: " + postulante.getCi());
                    System.out.println("Correo: " + postulante.getCorreo());
                    System.out.println("Fecha Nac: " + postulante.getFechaNac());
                    System.out.println("Teléfono: " + postulante.getTelefono());
                    System.out.println("Colegio: " + postulante.getColegio());
                    System.out.println("Dirección: " + postulante.getDireccion());
                    System.out.println("Carrera: " + postulante.getCarrera());
                }
            }
        });

        add(panel);
        setVisible(true);
    }

    // Método para obtener el objeto desde otra clase
    public Postulante getPostulante() {
        return postulante;
    }

    public static void main(String[] args) {
        new RegistroPostulantes();
    }
}