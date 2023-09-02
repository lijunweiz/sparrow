package cn.unminded.sparrow.pdf;

import cn.unminded.sparrow.define.SparrowContext;
import cn.unminded.sparrow.define.SparrowConverterException;
import cn.unminded.sparrow.config.SparrowConstants;
import cn.unminded.sparrow.info.PDDocumentInfo;
import cn.unminded.sparrow.metric.ConvertMetric;
import cn.unminded.sparrow.util.OutputModeEnum;
import cn.unminded.sparrow.util.PDFBoxUtils;
import cn.unminded.sparrow.util.PageSizeEnum;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static cn.unminded.sparrow.util.ConvertFormatEnum.IMAGE_TO_PDF;
import static cn.unminded.sparrow.util.ConvertFormatEnum.PDF_TO_WORD;

public abstract class AbstractPDFSparrowConverter implements PDFSparrowConverter {

    private final Logger logger = LoggerFactory.getLogger(AbstractPDFSparrowConverter.class);

    @Override
    public void prepare(SparrowContext context) {
        if (Objects.equals(context.getConvertFormatEnum(), PDF_TO_WORD)) {
            try {
                context.setPdList(new ArrayList<>(1));
                context.getPdList().add(new PDDocumentInfo().setPdDocument(PDDocument.load(new File(context.getSourcePath()))));
            } catch (IOException e) {
                throw new SparrowConverterException(e);
            }
            return;
        }

        if (Objects.equals(context.getOutputModeEnum(), OutputModeEnum.ONE_BY_ONE)) {
            String[] fileList = getFileList(context.getSourcePath());
            if (Objects.isNull(fileList) || fileList.length == 0) {
                context.setPdList(null);
            } else {
                context.setPdList(new ArrayList<>(fileList.length));
                for (int i = 0; i < fileList.length; i++) {
                    context.getPdList().add(new PDDocumentInfo().setPdDocument(new PDDocument()));
                }
            }
        } else {
            context.setPdList(Arrays.asList(new PDDocumentInfo().setPdDocument(new PDDocument())));
            context.getPdList().get(0).setPageCount(this.pageCount(context.getSourcePath()));
        }

        context.getPdList().forEach(pdInfo -> {
            pdInfo.getPdDocument().setDocumentInformation(PDFBoxUtils.getPDDocumentInformation());
            pdInfo.getPdDocument().setVersion(PDFBoxUtils.PD_DOCUMENT_VERSION);
        });

        File target = new File(context.getSavePath());
        if (!context.getSavePath().contains("pdf") && !target.exists()) {
            target.mkdirs();
        }
    }

    @Override
    public void doConvert(SparrowContext context) {
        try {
            if (Objects.equals(context.getConvertFormatEnum(), IMAGE_TO_PDF)) {
                imageToPdf(context);
            } else if (Objects.equals(context.getConvertFormatEnum(), PDF_TO_WORD)) {
                pdfToWord(context);
            } else {
                throw new SparrowConverterException("不支持的文件格式");
            }
        } catch (SparrowConverterException e) {
            context.setConvertResult(Boolean.FALSE);
            logger.error("{}转换失败, 详细原因: ", context.getConvertFormatEnum(), e);
        }
    }

    public void imageToPdf(SparrowContext context) throws SparrowConverterException {
        // 对应子类必须实现
    }

    public void pdfToWord(SparrowContext context) throws SparrowConverterException {
        // 对应子类必须实现
    }

    @Override
    public void save(SparrowContext context) {
        if (Objects.equals(context.getConvertFormatEnum(), PDF_TO_WORD)) {
            Writer writer = null;
            try {
                writer = new FileWriter(context.getSavePath());
                writer.write(context.getPlanText());
                writer.flush();
            } catch (IOException e) {
                logger.error("WORD文件保存失败, 错误原因: ", e);
            } finally {
                IOUtils.closeQuietly(writer);
            }

            return;
        }

        for (PDDocumentInfo pdDocumentInfo : context.getPdList()) {
            try {
                pdDocumentInfo.getPdDocument().save(pdDocumentInfo.getSaveFileName());
                pdDocumentInfo.getPdDocument().close();
            } catch (Exception e) {
                logger.error("文件保存失败, 错误原因: ", e);
            }
        }
    }

    @Override
    public ConvertMetric convertMetric() {
        return null;
    }

    /**
     * 获取pdf 总页数
     * @param fileSource 源文件
     * @return
     */
    private Integer pageCount(String fileSource) {
        String[] fileList = getFileList(fileSource);
        if (Objects.nonNull(fileList) && fileList.length > 0) {
            return fileList.length;
        }

        return 1;
    }

    /**
     * 获取文件名列表
     * @param fileSource 源文件目录
     * @return
     */
    protected String[] getFileList(String fileSource) {
        File file = new File(fileSource);
        if (file.isDirectory()) {
            return file.list((dir, name) -> this.supportImageType(name));
        } else if (file.isFile()) {
            return new String[]{file.getName()};
        }

        return new String[0];
    }

    /**
     * 获取生成pdf文件的路径
     * @param source 源文件
     * @param target 目标文件
     * @return
     */
    protected String getSavePath(String source, String target) {
        File sourceF = new File(source);
        File targetF = new File(target);
        if (targetF.isDirectory()) {
            return targetF + "\\" + sourceF.getName().substring(0, sourceF.getName().lastIndexOf('.') + 1) + "pdf";
        }

        return target.substring(0, target.lastIndexOf('.') + 1) + "pdf";
    }

    private boolean supportImageType(String file) {
        return SparrowConstants.SUPPORT_IMAGE_TYPE.contains(file.substring(file.lastIndexOf(".") + 1));
    }

    /**
     * 获取指定大小的PDPage
     * @param pageSizeEnum
     * @param pdxObject
     * @return
     */
    protected PDPage getPage(PageSizeEnum pageSizeEnum, PDXObject pdxObject) {
        int width = 0;
        int height = 0;
        if (pdxObject instanceof PDImageXObject) {
            width = ((PDImageXObject) pdxObject).getWidth();
            height = ((PDImageXObject) pdxObject).getHeight();
        }
        if (Objects.equals(pageSizeEnum, PageSizeEnum.ORIGINAL)) {
            return new PDPage(new PDRectangle(width, height));
        } else {
            return new PDPage(pageSizeEnum.getPdRectangle());
        }
    }
}
