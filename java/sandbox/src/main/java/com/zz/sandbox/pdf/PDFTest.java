package com.zz.sandbox.pdf;

/**
 * Created by lugan on 1/11/2017.
 */
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.text.DecimalFormat;

public class PDFTest {

    public static void main(String[] args) {

        String pdfFilename = "C:\\Users\\lugan\\test\\test2.pdf";
        PDFTest printReport = new PDFTest();
        /*if (args.length < 1)
        {
            System.err.println("Usage: java "+ printReport.getClass().getName()+
                    " PDF_Filename");
            System.exit(1);
        }

        pdfFilename = args[0].trim();*/
        printReport.createPDF(pdfFilename);

    }

    private void createPDF (String pdfFilename){

        Document doc = new Document();
        PdfWriter docWriter = null;

        DecimalFormat df = new DecimalFormat("0.00");

        try {

            //special font sizes
            //Font bfBold12 = new Font(BaseFont.TIMES_ROMAN, 12.0, Font.BOLD, new Color(0, 0, 0));
            Font bfBold12 = new Font(com.lowagie.text.Font.COURIER, 12, com.lowagie.text.Font.BOLD);
            Font bf12 = new Font(Font.TIMES_ROMAN, 12);

            //file path
            String path = pdfFilename;
            docWriter = PdfWriter.getInstance(doc , new FileOutputStream(path));

            //document header attributes
            doc.addAuthor("betterThanZero");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("MySampleCode.com");
            doc.addTitle("Report with Column Headings");
            doc.setPageSize(PageSize.LETTER);

            //open document
            doc.open();

            //create a paragraph
            Paragraph paragraph = new Paragraph("iText ® is a library that allows you to create and " +
                    "manipulate PDF documents. It enables developers looking to enhance web and other " +
                    "applications with dynamic PDF document generation and/or manipulation.");


            //specify column widths
            float[] columnWidths = {1.5f, 2f, 5f, 2f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setWidthPercentage(90f);

            //insert column headings
            insertCell(table, "Order No", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Account No", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Account Name", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Order Total", Element.ALIGN_RIGHT, 1, bfBold12);
            table.setHeaderRows(1);

            //insert an empty row
            insertCell(table, "", Element.ALIGN_LEFT, 4, bfBold12);
            //create section heading by cell merging
            insertCell(table, "New York Orders ...", Element.ALIGN_LEFT, 4, bfBold12);
            double orderTotal, total = 0;

            //just some random data to fill
            for(int x=1; x<5; x++){

                insertCell(table, "10010" + x, Element.ALIGN_RIGHT, 1, bf12);
                insertCell(table, "ABC00" + x, Element.ALIGN_LEFT, 1, bf12);
                insertCell(table, "This is Customer Number ABC00" + x, Element.ALIGN_LEFT, 1, bf12);

                orderTotal = Double.valueOf(df.format(Math.random() * 1000));
                total = total + orderTotal;
                insertCell(table, df.format(orderTotal), Element.ALIGN_RIGHT, 1, bf12);

            }
            //merge the cells to create a footer for that section
            insertCell(table, "New York Total...", Element.ALIGN_RIGHT, 3, bfBold12);
            insertCell(table, df.format(total), Element.ALIGN_RIGHT, 1, bfBold12);

            //repeat the same as above to display another location
            insertCell(table, "", Element.ALIGN_LEFT, 4, bfBold12);
            insertCell(table, "California Orders ...", Element.ALIGN_LEFT, 4, bfBold12);
            orderTotal = 0;

            for(int x=1; x<7; x++){

                insertCell(table, "20020" + x, Element.ALIGN_RIGHT, 1, bf12);
                insertCell(table, "XYZ00" + x, Element.ALIGN_LEFT, 1, bf12);
                insertCell(table, "This is Customer Number XYZ00" + x, Element.ALIGN_LEFT, 1, bf12);

                orderTotal = Double.valueOf(df.format(Math.random() * 1000));
                total = total + orderTotal;
                insertCell(table, df.format(orderTotal), Element.ALIGN_RIGHT, 1, bf12);

            }
            insertCell(table, "California Total...", Element.ALIGN_RIGHT, 3, bfBold12);
            insertCell(table, df.format(total), Element.ALIGN_RIGHT, 1, bfBold12);

            //add the PDF table to the paragraph
            paragraph.add(table);
            // add the paragraph to the document
            doc.add(paragraph);

        }
        catch (DocumentException dex)
        {
            dex.printStackTrace();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (doc != null){
                //close the document
                doc.close();
            }
            if (docWriter != null){
                //close the writer
                docWriter.close();
            }
        }
    }

    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font){

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        //in case there is no text and you wan to create an empty row
        if(text.trim().equalsIgnoreCase("")){
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);

    }

}