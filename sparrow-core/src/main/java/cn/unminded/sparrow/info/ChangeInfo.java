package cn.unminded.sparrow.info;

import cn.unminded.sparrow.util.ConvertFormatEnum;

public class ChangeInfo {

    /**
     * 开始页
     */
    private Integer startPage;

    /**
     * 结束页
     */
    private Integer endPage;

    /**
     * 按页拆分pdf时{@link ConvertFormatEnum#PDF_SPLIT}，一个pdf文件有几页
     */
    private Integer splitLength;

    public Integer getStartPage() {
        return startPage;
    }

    public ChangeInfo setStartPage(Integer startPage) {
        this.startPage = startPage;
        return this;
    }

    public Integer getEndPage() {
        return endPage;
    }

    public ChangeInfo setEndPage(Integer endPage) {
        this.endPage = endPage;
        return this;
    }

    public Integer getSplitLength() {
        return splitLength;
    }

    public ChangeInfo setSplitLength(Integer splitLength) {
        this.splitLength = splitLength;
        return this;
    }

    @Override
    public String toString() {
        return "ChangeInfo{" +
                "startPage=" + startPage +
                ", endPage=" + endPage +
                ", splitLength=" + splitLength +
                '}';
    }
}
