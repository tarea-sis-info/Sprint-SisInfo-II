/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admisionuniversitariaacademica;

/**
 *
 * @author Jhon
 */
public class Postulante {
    private String nombre;
    private String ci;
    private String correo;
    private String fechaNac;
    private String telefono;
    private String colegio;
    private String direccion;
    private String carrera;

    // Constructor
    public Postulante(String nombre, String ci, String correo, String fechaNac, 
                      String telefono, String colegio, String direccion, String carrera) {
        this.nombre = nombre;
        this.ci = ci;
        this.correo = correo;
        this.fechaNac = fechaNac;
        this.telefono = telefono;
        this.colegio = colegio;
        this.direccion = direccion;
        this.carrera = carrera;
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getCi() { return ci; }
    public String getCorreo() { return correo; }
    public String getFechaNac() { return fechaNac; }
    public String getTelefono() { return telefono; }
    public String getColegio() { return colegio; }
    public String getDireccion() { return direccion; }
    public String getCarrera() { return carrera; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCi(String ci) { this.ci = ci; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setFechaNac(String fechaNac) { this.fechaNac = fechaNac; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setColegio(String colegio) { this.colegio = colegio; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setCarrera(String carrera) { this.carrera = carrera; }
}

