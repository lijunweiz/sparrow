package cn.unminded.sparrow.pdf;

import cn.unminded.sparrow.define.SparrowContext;
import cn.unminded.sparrow.define.SparrowConverter;
import cn.unminded.sparrow.define.SparrowConverterException;
import cn.unminded.sparrow.metric.SparrowMetric;

public interface PDFSparrowConverter extends SparrowConverter, SparrowMetric {

    void imageToPdf(SparrowContext context) throws SparrowConverterException;

    void pdfToWord(SparrowContext context) throws SparrowConverterException;
}
