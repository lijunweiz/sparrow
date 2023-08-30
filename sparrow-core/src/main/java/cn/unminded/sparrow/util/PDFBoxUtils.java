package cn.unminded.sparrow.util;

import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

import java.util.Calendar;

public class PDFBoxUtils {

    public static final float PD_DOCUMENT_VERSION = 1.4f;

    private static final float POINTS_PER_MM = 1 / (10 * 2.54f) * 72;

    private static final float A4_X = 21.8f * POINTS_PER_MM;
    private static final float A4_Y = 25.4f * POINTS_PER_MM;

    public static PDDocumentInformation getPDDocumentInformation() {
        PDDocumentInformation information = new PDDocumentInformation();
        information.setAuthor("sparrow");
        information.setCreationDate(Calendar.getInstance());
        information.setCreator("sparrowCreator");
        information.setKeywords("toPdf");
        information.setProducer("sparrowProducer");
        information.setModificationDate(Calendar.getInstance());
        information.setSubject("sparrow convert img to pdf");
        information.setTitle("pdf");
        information.setTrapped("True");
        return information;
    }

    public static Matrix getBefittingMatrix(PDPage pdPage, PDImageXObject image) {
        Matrix matrix = Matrix.getTranslateInstance(A4_X, pdPage.getMediaBox().getHeight() - image.getHeight() - A4_Y);
        matrix.scale(image.getWidth(), image.getHeight());
        return matrix;
    }

    /**
     * 指定pd页面大小PDRectangle.A4，自适应页面
     * @param pdPage
     * @param image
     * @return
     */
    public static Matrix getA4BefittingMatrix(PDPage pdPage, PDImageXObject image) {
        Matrix matrix = Matrix.getTranslateInstance(A4_X, pdPage.getMediaBox().getHeight() - image.getHeight() - A4_Y);
        matrix.scale(image.getWidth(), image.getHeight());
        return matrix;
    }

}
