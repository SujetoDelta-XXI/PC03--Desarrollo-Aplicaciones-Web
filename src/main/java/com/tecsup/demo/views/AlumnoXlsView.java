package com.tecsup.demo.views;

import com.tecsup.demo.domain.entities.Alumno;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component("alumno/ver.xlsx")
public class AlumnoXlsView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment; filename=\"alumno_view.xlsx\"");

        List<Alumno> alumnos = (List<Alumno>) model.get("alumnos");
        Sheet sheet = workbook.createSheet("Lista de Alumnos");

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerStyle.setBorderTop(BorderStyle.MEDIUM);
        headerStyle.setBorderLeft(BorderStyle.MEDIUM);
        headerStyle.setBorderRight(BorderStyle.MEDIUM);

        CellStyle bodyStyle = workbook.createCellStyle();
        bodyStyle.setBorderBottom(BorderStyle.THIN);
        bodyStyle.setBorderTop(BorderStyle.THIN);
        bodyStyle.setBorderLeft(BorderStyle.THIN);
        bodyStyle.setBorderRight(BorderStyle.THIN);

        Row header = sheet.createRow(0);
        String[] columnas = {"Código", "Nombres", "Apellidos", "Fecha Nac.", "Sexo"};

        for (int i = 0; i < columnas.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (Alumno a : alumnos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(a.getCodigo());
            row.createCell(1).setCellValue(a.getNombres());
            row.createCell(2).setCellValue(a.getApellidos());
            row.createCell(3).setCellValue(a.getFechaNac() != null ? a.getFechaNac().toString() : "");
            row.createCell(4).setCellValue(a.getSexo());

            for (int i = 0; i < 5; i++) {
                row.getCell(i).setCellStyle(bodyStyle);
            }
        }
    }
}
