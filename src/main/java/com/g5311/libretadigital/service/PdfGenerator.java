package com.g5311.libretadigital.service;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

public class PdfGenerator {
    public static void generarPdfNota(String rutaPdf, String fecha, int legajo, String materia, double nota)
            throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(rutaPdf));
        document.open();

        String contenido = String.format(
                "Fecha: %s\nLegajo Alumno: %d\nMateria: %s\nNota Final: %.2f",
                fecha, legajo, materia, nota);

        document.add(new Paragraph(contenido));
        document.close();

        System.out.println(new File(rutaPdf).getAbsolutePath());

    }

    public static void main(String[] args) throws Exception {
        String rutaPdf = "nota_final.pdf";
        // generarPdfNota(rutaPdf, "2025-11-02T18:29:26.365Z", 1673154, "Sistemas de
        // Información II", 10);
        System.out.println("PDF generado: " + rutaPdf);
    }
}
