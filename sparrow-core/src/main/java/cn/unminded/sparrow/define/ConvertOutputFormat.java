package cn.unminded.sparrow.define;

import cn.unminded.sparrow.info.ChangeInfo;
import cn.unminded.sparrow.util.*;

public class ConvertOutputFormat {

    private PageSizeEnum sizeEnum;

    private MarginWidthEnum marginWidthEnum;

    private PageOrientationEnum orientationEnum;

    private OutputModeEnum outputModeEnum;

    private ConvertFormatEnum convertFormatEnum;

    private ChangeInfo pdfChangeInfo;

    private String source;

    private String target;

    public PageSizeEnum getSizeEnum() {
        return sizeEnum;
    }

    public ConvertOutputFormat setSizeEnum(PageSizeEnum sizeEnum) {
        this.sizeEnum = sizeEnum;
        return this;
    }

    public MarginWidthEnum getMarginWidthEnum() {
        return marginWidthEnum;
    }

    public ConvertOutputFormat setMarginWidthEnum(MarginWidthEnum marginWidthEnum) {
        this.marginWidthEnum = marginWidthEnum;
        return this;
    }

    public PageOrientationEnum getOrientationEnum() {
        return orientationEnum;
    }

    public ConvertOutputFormat setOrientationEnum(PageOrientationEnum orientationEnum) {
        this.orientationEnum = orientationEnum;
        return this;
    }

    public OutputModeEnum getOutputModeEnum() {
        return outputModeEnum;
    }

    public ConvertOutputFormat setOutputModeEnum(OutputModeEnum outputModeEnum) {
        this.outputModeEnum = outputModeEnum;
        return this;
    }

    public ConvertFormatEnum getConvertFormatEnum() {
        return convertFormatEnum;
    }

    public ConvertOutputFormat setConvertFormatEnum(ConvertFormatEnum convertFormatEnum) {
        this.convertFormatEnum = convertFormatEnum;
        return this;
    }

    public ChangeInfo getPdfChangeInfo() {
        return pdfChangeInfo;
    }

    public ConvertOutputFormat setPdfChangeInfo(ChangeInfo pdfChangeInfo) {
        this.pdfChangeInfo = pdfChangeInfo;
        return this;
    }

    public String getSource() {
        return source;
    }

    public ConvertOutputFormat setSource(String source) {
        this.source = source;
        return this;
    }

    public String getTarget() {
        return target;
    }

    public ConvertOutputFormat setTarget(String target) {
        this.target = target;
        return this;
    }

    public static ConvertOutputFormat defaultOutputFormat() {
        return new ConvertOutputFormat()
                .setSizeEnum(PageSizeEnum.ORIGINAL)
                .setMarginWidthEnum(MarginWidthEnum.NORMAL)
                .setOrientationEnum(PageOrientationEnum.VERTICAL)
                .setOutputModeEnum(OutputModeEnum.ONE_BY_ONE)
                .setConvertFormatEnum(ConvertFormatEnum.IMAGE_TO_PDF)
                ;
    }

    @Override
    public String toString() {
        return "ConvertOutputFormat{" +
                "sizeEnum=" + sizeEnum +
                ", marginWidthEnum=" + marginWidthEnum +
                ", orientationEnum=" + orientationEnum +
                ", outputModeEnum=" + outputModeEnum +
                ", convertFormatEnum=" + convertFormatEnum +
                ", pdfChangeInfo=" + pdfChangeInfo +
                ", source=" + source +
                ", target=" + target +
                '}';
    }
}
