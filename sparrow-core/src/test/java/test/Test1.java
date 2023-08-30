package test;

import cn.unminded.sparrow.util.PDFBoxUtils;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.TextPosition;
import org.apache.pdfbox.util.Matrix;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

public class Test1 {


    public static void main(String[] args) throws Exception {


        PDDocument pdDocument = new PDDocument();
        pdDocument.setDocumentInformation(PDFBoxUtils.getPDDocumentInformation());

        PDImageXObject image = PDImageXObject.createFromFile("logo.png", pdDocument);
//        PDPage pdPage1 = new PDPage(new PDRectangle(image.getWidth(), image.getHeight()*2));
//
//        PDPageContentStream pdPageContentStream = new PDPageContentStream(pdDocument, pdPage1);
//
//        pdPageContentStream.drawImage(image, 0, 0);
//        pdPageContentStream.drawImage(image, 0, image.getHeight());

//        PDPage pdPage1 = new PDPage(new PDRectangle(image.getWidth(), image.getHeight()));
//        PDPageContentStream pdPageContentStream = new PDPageContentStream(pdDocument, pdPage1);
//        pdPageContentStream.drawImage(image, 0, 0);
//        pdPageContentStream.close();

        PDPage pdPage1 = new PDPage(new PDRectangle(image.getWidth(), image.getHeight()));
        PDPageContentStream pdPageContentStream = new PDPageContentStream(pdDocument, pdPage1);
        pdPageContentStream.drawImage(image, 0, 0);
        pdPageContentStream.close();

        pdDocument.addPage(pdPage1);
        pdDocument.save("t.pdf");
        pdDocument.close();

    }



}
