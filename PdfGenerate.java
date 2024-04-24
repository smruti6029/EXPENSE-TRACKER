package com.dynamicformulaparsher;

import java.io.FileNotFoundException;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

public class PdfGenerate {
    public static void main(String[] args) {
        
        String name = "John Doe";
        int age = 30;
        String occupation = "Software Developer";
        String outputPath = "output.pdf";

        
        try {
            PdfWriter writer = new PdfWriter(outputPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);

           
            for (int i = 0; i < 3; i++) {
               
            	pdf.addNewPage();

           
                Paragraph personalInfo = new Paragraph("Personal Information")
                                                .setTextAlignment(TextAlignment.CENTER)
                                                .setFontColor(Color.BLACK); // Set text color to black
                document.add(personalInfo);

                Paragraph nameParagraph = new Paragraph("Name: " + name)
                                                .setFontColor(Color.BLACK); // Set text color to black
                document.add(nameParagraph);

                Paragraph ageParagraph = new Paragraph("Age: " + age)
                                                .setFontColor(Color.BLACK); // Set text color to black
                document.add(ageParagraph);

                Paragraph occupationParagraph = new Paragraph("Occupation: " + occupation)
                                                .setFontColor(Color.BLACK); // Set text color to black
                document.add(occupationParagraph);
            }

           
            document.close();

            System.out.println("PDF created successfully!");
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }
}
