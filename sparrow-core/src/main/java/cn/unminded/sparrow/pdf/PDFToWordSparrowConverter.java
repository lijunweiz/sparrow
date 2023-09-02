package cn.unminded.sparrow.pdf;

import cn.unminded.sparrow.define.SparrowContext;
import cn.unminded.sparrow.define.SparrowConverterException;

import java.io.IOException;

public class PDFToWordSparrowConverter extends AbstractPDFSparrowConverter implements PDFSparrowConverter {

    @Override
    public void pdfToWord(SparrowContext context) throws SparrowConverterException {
        try {
            PDFLayoutTextStripper pdfLayoutTextStripper = new PDFLayoutTextStripper();
            pdfLayoutTextStripper.setAddMoreFormatting(true);
            pdfLayoutTextStripper.setSortByPosition(true);
            String text = pdfLayoutTextStripper.getText(context.getPdList().get(0).getPdDocument());
            context.setPlanText(text);
        } catch (IOException e) {
           throw new SparrowConverterException(e);
        }
    }

}
