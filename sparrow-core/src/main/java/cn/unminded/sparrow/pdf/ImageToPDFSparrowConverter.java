package cn.unminded.sparrow.pdf;

import cn.unminded.sparrow.define.SparrowContext;
import cn.unminded.sparrow.define.SparrowConverterException;
import cn.unminded.sparrow.info.PDDocumentInfo;
import cn.unminded.sparrow.util.OutputModeEnum;
import cn.unminded.sparrow.util.PageSizeEnum;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ImageToPDFSparrowConverter extends AbstractPDFSparrowConverter {

    @Override
    public void imageToPdf(SparrowContext context) throws SparrowConverterException {
        try {
            String[] sourceFileList = super.getFileList(context.getSourcePath());
            if (sourceFileList.length > 0) {
                File file = new File(context.getSourcePath());
                String sourcePath = file.isFile() ? file.getParent() : context.getSourcePath();
                if (Objects.equals(context.getOutputModeEnum(), OutputModeEnum.ONE_BY_ONE)) {
                    for (int i = 0; i < sourceFileList.length; i++) {
                        handler(context.getPdList().get(i), sourceFileList[i], sourcePath, context.getSavePath(), context.getPageSizeEnum());
                    }
                } else {
                    for (String f : sourceFileList) {
                        handler(context.getPdList().get(0), f, sourcePath, context.getSavePath(), context.getPageSizeEnum());
                    }
                }
            }
        } catch (IOException e) {
            throw new SparrowConverterException(e);
        }
    }

    private void handler(PDDocumentInfo pdDocumentInfo, String fileName, String sourcePath, String savePath, PageSizeEnum pageSizeEnum) throws IOException {
        pdDocumentInfo.setSaveFileName(super.getSavePath(fileName, savePath));
        PDImageXObject image = PDImageXObject.createFromFile(sourcePath + File.separator + fileName, pdDocumentInfo.getPdDocument());
        PDPage pdPage = super.getPage(pageSizeEnum, image);
        try (PDPageContentStream pdPageContentStream = new PDPageContentStream(pdDocumentInfo.getPdDocument(), pdPage)) {
            pdPageContentStream.drawImage(image, 0, 0);
        }
        pdDocumentInfo.getPdDocument().addPage(pdPage);
    }

}
