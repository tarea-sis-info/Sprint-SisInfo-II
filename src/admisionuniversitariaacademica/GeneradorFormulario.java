package admisionuniversitariaacademica;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.awt.Desktop;
import java.io.File;

public class GeneradorFormulario {

    public static boolean generarPDF(String nombreCompleto, String ci, String email, String fechaNacimiento, String telefono, String colegio, String direccion, String carrera) {
        Document document = new Document();
        try {
            String nombreArchivo = "Formulario_Inscripcion_" + ci + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
            document.open();

            
            agregarTextoIzquierda(document, "Universidad Mayor de San Simon");
            agregarTextoIzquierda(document, "Facultad de ciencias y tecnologia");
            agregarTitulo(document, "FORMULARIO DE INSCRIPCIÓN DE POSTULANTES");
            
            agregarSeccion(document, "1. DATOS PERSONALES");
            PdfPTable tablaPersonal = new PdfPTable(2);
            tablaPersonal.setWidthPercentage(100);
            
            agregarFila(tablaPersonal, "Nombre completo:", nombreCompleto);
            agregarFila(tablaPersonal, "Cédula de Identidad:", ci);
            agregarFila(tablaPersonal, "Fecha de nacimiento:", fechaNacimiento);
            document.add(tablaPersonal);
            
            agregarSeccion(document, "2. DATOS DE CONTACTO");
            PdfPTable tablaContacto = new PdfPTable(2);
            tablaContacto.setWidthPercentage(100);
            
            agregarFila(tablaContacto, "Correo electrónico:", email);
            agregarFila(tablaContacto, "Teléfono:", telefono);
            agregarFila(tablaContacto, "Dirección:", direccion);
            document.add(tablaContacto);
            
            agregarSeccion(document, "3. DATOS ACADÉMICOS");
            PdfPTable tablaAcademica = new PdfPTable(2);
            tablaAcademica.setWidthPercentage(100);
            
            agregarFila(tablaAcademica, "Colegio:", colegio);
            agregarFila(tablaAcademica, "Plan de estudios:", carrera);
            document.add(tablaAcademica);
            
            agregarSeccion(document, "4.DOCUMENTOS QUE DEBE PRESENTAR EL POSTULANTE");
            agregarTexto(document, "Debe adjuntar a este formulario los siguientes documentos");
            agregarTextoCentralizado(document, "1. Fotocopia de cedula de identidad");
            agregarTextoCentralizado(document, "2. Fotocopia de Boletin de calificaciones o Diploma de bachiller");
            agregarTextoCentralizado(document, "3. Fotocopia de cedula de identidad del apoderado");
            
            
            agregarFirma(document);
            
            document.close();
            abrirPDF(nombreArchivo);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private static void agregarTitulo(Document document, String tituloTexto) throws DocumentException {
        Font fontTitulo = FontFactory.getFont(FontFactory.TIMES, 15,Font.BOLD, BaseColor.BLACK);
        Paragraph titulo = new Paragraph(tituloTexto, fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingBefore(10);
        titulo.setSpacingAfter(10);
        document.add(titulo);
    }
    
    private static void agregarSeccion(Document document, String tituloSeccion) throws DocumentException {
        Font fontSeccion = FontFactory.getFont(FontFactory.TIMES, 14,Font.BOLD, BaseColor.DARK_GRAY);
        Paragraph seccion = new Paragraph(tituloSeccion, fontSeccion);
        seccion.setSpacingBefore(15);
        seccion.setSpacingAfter(10);
        document.add(seccion);
    }
    
    private static void agregarTexto(Document document, String tituloSeccion) throws DocumentException {
        Font fontSeccion = FontFactory.getFont(FontFactory.TIMES, 14, BaseColor.DARK_GRAY);
        Paragraph seccion = new Paragraph(tituloSeccion, fontSeccion);
        seccion.setIndentationLeft(40f);
        seccion.setSpacingBefore(15);
        seccion.setSpacingAfter(10);
        document.add(seccion);
    }
    private static void agregarTextoIzquierda(Document document, String tituloSeccion) throws DocumentException {
        Font fontSeccion = FontFactory.getFont(FontFactory.TIMES, 14, BaseColor.DARK_GRAY);
        Paragraph seccion = new Paragraph(tituloSeccion, fontSeccion);
        seccion.setIndentationLeft(0f);
        seccion.setSpacingBefore(0);
        seccion.setSpacingAfter(0);
        document.add(seccion);
    }
    
    private static void agregarTextoCentralizado(Document document, String tituloSeccion) throws DocumentException {
        Font fontSeccion = FontFactory.getFont(FontFactory.TIMES, 14, BaseColor.DARK_GRAY);
        Paragraph seccion = new Paragraph(tituloSeccion, fontSeccion);
        seccion.setIndentationLeft(85f);
        seccion.setSpacingBefore(0);
        seccion.setSpacingAfter(0);
        document.add(seccion);
    }
    
    private static void agregarFila(PdfPTable tabla, String campo, String valor) {
        Font fontCampo = FontFactory.getFont(FontFactory.TIMES, 12);
        Font fontValor = FontFactory.getFont(FontFactory.TIMES, 12);
        
        PdfPCell celdaCampo = new PdfPCell(new Phrase(campo, fontCampo));
        PdfPCell celdaValor = new PdfPCell(new Phrase(valor, fontValor));
        
        celdaCampo.setPadding(5);
        celdaValor.setPadding(5);
        celdaCampo.setBackgroundColor(new BaseColor(240, 240, 240));
        
        tabla.addCell(celdaCampo);
        tabla.addCell(celdaValor);
    }
    
    private static void agregarFirma(Document document) throws DocumentException {
        document.add(new Paragraph(" "));
        Paragraph linea = new Paragraph("_________________________");
        linea.setSpacingBefore(40);
        linea.setSpacingAfter(5);
        linea.setIndentationLeft(85f); 
        document.add(linea);
        
        Font fontFirma = FontFactory.getFont(FontFactory.TIMES, 14);
        Paragraph firma = new Paragraph("Firma del postulante", fontFirma);
    
        firma.setSpacingBefore(0);
        firma.setSpacingAfter(10);
        firma.setIndentationLeft(110f);
        document.add(firma);
    }
    private static void abrirPDF(String nombreArchivo) {
        try {
            File archivoPDF = new File(nombreArchivo);
            if (archivoPDF.exists()) {
            Desktop.getDesktop().open(archivoPDF);
            System.out.println(" El archivo se abrio automáticamente");
            } else {
            System.out.println("El archivo PDF no se encontró");
        }
    } catch (Exception e) {
        System.out.println(" No se pudo abrir el Archivo: " + e.getMessage());
    }
}
    
    
    
    
    
    // Una prueba main por que debo conectar con resgistro y la base de datos pero la generacion funciona
    public static void main(String[] args) {
        generarPDF("María José García López", "9876543", "maria.garcia@email.com", "20/08/1999", "6987452", "Colegio San Agustín", "Calle Principal 456, Urb. Las Flores", "Medicina");
    }
}