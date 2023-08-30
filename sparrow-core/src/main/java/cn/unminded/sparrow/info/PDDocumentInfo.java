package cn.unminded.sparrow.info;

import org.apache.pdfbox.pdmodel.PDDocument;

public class PDDocumentInfo {

    /**
     * 转换目标文档
     */
    private PDDocument pdDocument;

    /**
     * 总页数
     */
    private Integer pageCount = 1;

    /**
     * 目标文件全路径
     */
    private String saveFileName;

    /**
     * 转换结果
     */
    private Boolean convertResult;

    public PDDocument getPdDocument() {
        return pdDocument;
    }

    public PDDocumentInfo setPdDocument(PDDocument pdDocument) {
        this.pdDocument = pdDocument;
        return this;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public PDDocumentInfo setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
        return this;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public PDDocumentInfo setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
        return this;
    }

    public Boolean getConvertResult() {
        return convertResult;
    }

    public PDDocumentInfo setConvertResult(Boolean convertResult) {
        this.convertResult = convertResult;
        return this;
    }
}
