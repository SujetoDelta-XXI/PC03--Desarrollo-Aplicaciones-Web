package com.tecsup.demo.views;

import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.tecsup.demo.domain.entities.Alumno;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.util.List;
import java.util.Map;

@Component("alumno/ver")
public class AlumnoPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Alumno> alumnos = (List<Alumno>) model.get("alumnos");

        PdfPTable tablaTitulo = new PdfPTable(1);
        tablaTitulo.setSpacingAfter(20);
        PdfPCell cell = new PdfPCell(new Phrase("Lista de Alumnos"));
        cell.setBackgroundColor(new Color(184, 218, 255));
        cell.setPadding(8f);
        tablaTitulo.addCell(cell);

        PdfPTable tabla = new PdfPTable(5); // columnas: codigo, nombre, apellido, fecha, sexo
        tabla.addCell("Código");
        tabla.addCell("Nombres");
        tabla.addCell("Apellidos");
        tabla.addCell("Fecha Nac.");
        tabla.addCell("Sexo");

        for (Alumno a : alumnos) {
            tabla.addCell(a.getCodigo());
            tabla.addCell(a.getNombres());
            tabla.addCell(a.getApellidos());
            tabla.addCell(a.getFechaNac() != null ? a.getFechaNac().toString() : "");
            tabla.addCell(a.getSexo());
        }

        document.add(tablaTitulo);
        document.add(tabla);
    }
}
