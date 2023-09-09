package cn.unminded.sparrow.define;

import cn.unminded.sparrow.info.ChangeInfo;
import cn.unminded.sparrow.info.PDDocumentInfo;
import cn.unminded.sparrow.util.ConvertFormatEnum;
import cn.unminded.sparrow.util.OutputModeEnum;
import cn.unminded.sparrow.util.PageSizeEnum;

import java.io.File;
import java.util.List;

public class SparrowContext {

    /**
     * 转换目标文档
     */
    private List<PDDocumentInfo> pdList;

    /**
     * 转换格式
     */
    private ConvertFormatEnum convertFormatEnum;

    /**
     * 输出模式
     */
    private OutputModeEnum outputModeEnum;

    /**
     * 页面大小
     */
    private PageSizeEnum pageSizeEnum;

    /**
     * 源文件路径
     */
    private String sourcePath;

    /**
     * 文件列表
     */
    private List<File> sourceFileList;

    /**
     * 目标文件路径
     */
    private String savePath;

    /**
     * pdf转word时{@link ConvertFormatEnum#PDF_TO_WORD}，转换后的文本
     */
    private String planText;

    private ChangeInfo changeInfo;

    private Boolean convertResult;


    public List<PDDocumentInfo> getPdList() {
        return pdList;
    }

    public SparrowContext setPdList(List<PDDocumentInfo> pdList) {
        this.pdList = pdList;
        return this;
    }

    public ConvertFormatEnum getConvertFormatEnum() {
        return convertFormatEnum;
    }

    public SparrowContext setConvertFormatEnum(ConvertFormatEnum convertFormatEnum) {
        this.convertFormatEnum = convertFormatEnum;
        return this;
    }

    public OutputModeEnum getOutputModeEnum() {
        return outputModeEnum;
    }

    public SparrowContext setOutputModeEnum(OutputModeEnum outputModeEnum) {
        this.outputModeEnum = outputModeEnum;
        return this;
    }

    public PageSizeEnum getPageSizeEnum() {
        return pageSizeEnum;
    }

    public SparrowContext setPageSizeEnum(PageSizeEnum pageSizeEnum) {
        this.pageSizeEnum = pageSizeEnum;
        return this;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public SparrowContext setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
        return this;
    }

    public List<File> getSourceFileList() {
        return sourceFileList;
    }

    public SparrowContext setSourceFileList(List<File> sourceFileList) {
        this.sourceFileList = sourceFileList;
        return this;
    }

    public String getSavePath() {
        return savePath;
    }

    public SparrowContext setSavePath(String savePath) {
        this.savePath = savePath;
        return this;
    }

    public String getPlanText() {
        return planText;
    }

    public SparrowContext setPlanText(String planText) {
        this.planText = planText;
        return this;
    }

    public ChangeInfo getChangeInfo() {
        return changeInfo;
    }

    public SparrowContext setChangeInfo(ChangeInfo changeInfo) {
        this.changeInfo = changeInfo;
        return this;
    }

    public Boolean getConvertResult() {
        return convertResult;
    }

    public SparrowContext setConvertResult(Boolean convertResult) {
        this.convertResult = convertResult;
        return this;
    }
}
