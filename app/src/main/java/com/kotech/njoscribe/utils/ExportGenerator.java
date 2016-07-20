package com.kotech.njoscribe.utils;

import android.os.Environment;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ExportGenerator {

    public static class PdfGenerator {

        static public boolean newPDF(String title, String body) {
            String FileTitle;

            if (title.isEmpty() || title.charAt(0) == ' ') {
                FileTitle = "PDF" + Math.random();
            }else {
                FileTitle=title;
            }

            String FILE = Environment.getExternalStorageDirectory().toString()
                    + "/NJO/PDF/" + FileTitle + ".pdf";

            // Add Permission into Manifest.xml
            // <uses-permission
            // android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

            // Create New Blank Document
            Document document = new Document(PageSize.A4);

            // Create Directory in External Storage
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/NJO/PDF");
            myDir.mkdirs();

            // Create Pdf Writer for Writting into New Created Document
            try {
                PdfWriter.getInstance(document, new FileOutputStream(FILE));

                // Open Document for Writting into document
                document.open();

                // User Define Method
                addMetaData(document);
                addTitlePage(document, title, body);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (DocumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // Close Document after writting all content
            document.close();
            return true;
        }

        // Set PDF document Properties
        public static void addMetaData(Document document)

        {
            document.addTitle("RESUME");
            document.addSubject("Person Info");
            document.addKeywords("Personal,	Education, Skills");
            document.addAuthor("TAG");
            document.addCreator("TAG");
        }

        public static void addTitlePage(Document document, String title, String body) throws DocumentException {
            // Font Style for Document
            Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD
                    | Font.UNDERLINE, BaseColor.BLACK);
            Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

            // Start New Paragraph
            Paragraph prHead = new Paragraph();
            // Set Font in this Paragraph
            prHead.setFont(titleFont);
            // Add item into Paragraph
            prHead.add(title + "\n");

            // Create Table into Document with 1 Row
            // 100.0f mean width of table is same as Document size

            // Create New Cell into Table
            PdfPCell myCell = new PdfPCell(new Paragraph(""));
            myCell.setBorder(Rectangle.BOTTOM);

            // Add Cell into Table

            prHead.setFont(catFont);

            prHead.setAlignment(Element.ALIGN_CENTER);

            // Add all above details into Document
            document.add(prHead);


            Paragraph prProfile = new Paragraph();
            prProfile.setFont(normal);
            prProfile
                    .add("\n" + body);

            prProfile.setFont(smallBold);
            document.add(prProfile);

            // Create new Page in PDF
            document.newPage();
        }

    }

    public static class WordGenerator {
        public static void newWordDoc(String title, String body) {

            if (title.isEmpty() || title.charAt(0) == ' ') {
                title = "Doc_FILE_" + Math.random();
            }

            String FILE = Environment.getExternalStorageDirectory().toString()
                    + "/NJO/Word/" + title + ".doc";


            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/NJO/Word");
            myDir.mkdirs();

            try {
   /*                 WordDocument doc = new WordDocument();

                    Run run = new Run();
                    run.addText("Hello Word!");

                    com.independentsoft.office.word.Paragraph paragraph = new com.independentsoft.office.word.Paragraph();
                    paragraph.add(run);

                    doc.getBody().add(paragraph);

                    doc.save(myDir+"\\test.docx");*/
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

    }


}
