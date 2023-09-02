package cn.unminded.sparrow.delegate;

import cn.unminded.sparrow.define.ConvertOutputFormat;
import cn.unminded.sparrow.define.SparrowContext;
import cn.unminded.sparrow.define.SparrowConverterException;
import cn.unminded.sparrow.metric.ConvertMetric;
import cn.unminded.sparrow.pdf.ImageToPDFSparrowConverter;
import cn.unminded.sparrow.pdf.PDFSparrowConverter;
import cn.unminded.sparrow.pdf.PDFToWordSparrowConverter;
import cn.unminded.sparrow.util.ConvertFormatEnum;
import org.apache.pdfbox.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class PDFSparrowConverterDelegate {

    private final Logger logger = LoggerFactory.getLogger(PDFSparrowConverterDelegate.class);

    private final Map<ConvertFormatEnum, PDFSparrowConverter> pDFSparrowConverterMap = new EnumMap<>(ConvertFormatEnum.class);

    public PDFSparrowConverterDelegate() {
        pDFSparrowConverterMap.put(ConvertFormatEnum.IMAGE_TO_PDF, new ImageToPDFSparrowConverter());
        pDFSparrowConverterMap.put(ConvertFormatEnum.PDF_TO_WORD, new PDFToWordSparrowConverter());
    }

    public ConvertMetric convert(ConvertOutputFormat format) {
        ConvertMetric convertMetric = ConvertMetric.defaultConvertMetric();
        SparrowContext context = new SparrowContext()
                .setConvertFormatEnum(format.getConvertFormatEnum())
                .setOutputModeEnum(format.getOutputModeEnum())
                .setPageSizeEnum(format.getSizeEnum())
                .setSourcePath(format.getSource())
                .setSavePath(format.getTarget())
                .setConvertResult(Boolean.FALSE);
        try {
            logger.info("开始处理: {}", context.getConvertFormatEnum());
            pDFSparrowConverterMap.get(context.getConvertFormatEnum()).convert(context);
            logger.info("处理完成: {}", context.getConvertFormatEnum());
        } catch (Exception e) {
            throw new SparrowConverterException(e);
        } finally {
            if (Objects.nonNull(context.getPdList()) && !context.getPdList().isEmpty()) {
                context.getPdList().forEach(pdDocumentInfo -> IOUtils.closeQuietly(pdDocumentInfo.getPdDocument()));
            }
        }
        return convertMetric;
    }

}
