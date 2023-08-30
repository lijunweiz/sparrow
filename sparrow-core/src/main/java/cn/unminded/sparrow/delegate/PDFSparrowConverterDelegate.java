package cn.unminded.sparrow.delegate;

import cn.unminded.sparrow.define.ConvertOutputFormat;
import cn.unminded.sparrow.define.SparrowContext;
import cn.unminded.sparrow.metric.ConvertMetric;
import cn.unminded.sparrow.pdf.ImageToPDFSparrowConverter;
import cn.unminded.sparrow.pdf.PDFSparrowConverter;
import cn.unminded.sparrow.util.ConvertFormatEnum;

import java.util.EnumMap;
import java.util.Map;

public class PDFSparrowConverterDelegate {

    private final Map<ConvertFormatEnum, PDFSparrowConverter> pDFSparrowConverter = new EnumMap<>(ConvertFormatEnum.class);

    public PDFSparrowConverterDelegate() {
        pDFSparrowConverter.put(ConvertFormatEnum.IMAGE_TO_PDF, new ImageToPDFSparrowConverter());
    }

    public ConvertMetric convert(ConvertOutputFormat format) {
        ConvertMetric convertMetric = ConvertMetric.defaultConvertMetric();
        SparrowContext context = new SparrowContext()
                .setConvertFormatEnum(ConvertFormatEnum.IMAGE_TO_PDF)
                .setOutputModeEnum(format.getOutputModeEnum())
                .setPageSizeEnum(format.getSizeEnum())
                .setSourcePath(format.getSource())
                .setSavePath(format.getTarget())
                .setConvertResult(Boolean.FALSE);
        pDFSparrowConverter.get(context.getConvertFormatEnum()).convert(context);
        return convertMetric;
    }

}
