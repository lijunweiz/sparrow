package cn.unminded.sparrow.pdf;

import cn.unminded.sparrow.config.SparrowConstants;
import cn.unminded.sparrow.define.SparrowContext;
import cn.unminded.sparrow.define.SparrowConverterException;
import cn.unminded.sparrow.info.ChangeInfo;
import cn.unminded.sparrow.info.PDDocumentInfo;
import cn.unminded.sparrow.util.ConvertFormatEnum;
import cn.unminded.sparrow.util.PDFBoxUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.PageExtractor;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class PDFGeometryConverter extends AbstractPDFSparrowConverter {

    private Logger logger = LoggerFactory.getLogger(PDFGeometryConverter.class);

    @Override
    protected void pdfSplit(SparrowContext context) throws SparrowConverterException {
        List<PDDocument> pdDocumentList = null;
        try {
            pdDocumentList = this.getPdDocumentList(context.getChangeInfo(), context.getConvertFormatEnum(), context.getSourcePath());
        } catch (Exception e) {
            throw new SparrowConverterException(e);
        }

        if (pdDocumentList.isEmpty()) {
            throw new SparrowConverterException(context.getSourcePath() + "未获取到有效的PDF信息");
        }

        List<PDDocumentInfo> infoList = pdDocumentList.stream().map(pdDocument -> {
            pdDocument.setDocumentInformation(PDFBoxUtils.getPDDocumentInformation());
            return new PDDocumentInfo()
                    .setPdDocument(pdDocument)
                    .setSaveFileName(context.getSavePath() + File.separator + UUID.randomUUID().toString().replace("-", "") + ".pdf");
        }).collect(Collectors.toList());

        context.setPdList(infoList);
    }

    /**
     * 根据源文件地址，拆分或抽取指定页面的PDF文件
     * @param changeInfo 抽取信息
     * @param convertFormatEnum 拆分还是抽取
     * @param sourcePath 源文件路径
     * @return 处理完成pdfList，当非拆分非抽取行为时返回null
     * @throws IOException 加载PDF文件失败时或PDF拆分失败或者PDF抽取失败
     */
    private List<PDDocument> getPdDocumentList(ChangeInfo changeInfo, ConvertFormatEnum convertFormatEnum, String sourcePath) throws IOException {
        if (!Objects.equals(convertFormatEnum, ConvertFormatEnum.PDF_SPLIT)) {
            return Collections.emptyList();
        }

        File file = new File(sourcePath);
        if (!file.isFile() || !file.canRead()) {
            return Collections.emptyList();
        }

        try (PDDocument pdDocument = PDDocument.load(file)) {
            if (Objects.isNull(pdDocument)) {
                return Collections.emptyList();
            }

            if (Objects.equals(changeInfo.getSplitLength(), SparrowConstants.INT_0)) {
                PageExtractor pageExtractor = new PageExtractor(pdDocument);
                pageExtractor.setStartPage(changeInfo.getStartPage());
                pageExtractor.setEndPage(changeInfo.getEndPage());

                return Collections.singletonList(pageExtractor.extract());
            } else if (changeInfo.getSplitLength() > 0) {
                Splitter splitter = new Splitter();
                splitter.setStartPage(changeInfo.getStartPage());
                splitter.setEndPage(changeInfo.getEndPage());
                splitter.setSplitAtPage(changeInfo.getSplitLength());

                return splitter.split(pdDocument);
            }
        } catch (IOException e) {
            throw new SparrowConverterException(e);
        }

        return Collections.emptyList();
    }

    /**
     *
     * @param context context.getSourceFileList() 文件列表，context.getSavePath()合并后的pdf目录，必须是目录
     */
    @Override
    protected void pdfMerge(SparrowContext context) {
        PDFMergerUtility pdfMerger = new PDFMergerUtility();
        pdfMerger.addSources(this.getPdDocumentList(context.getSourceFileList()));
        pdfMerger.setDestinationDocumentInformation(PDFBoxUtils.getPDDocumentInformation());
        pdfMerger.setDestinationFileName(context.getSavePath() + File.separator + UUID.randomUUID().toString().replace("-", "" + ".pdf"));
        try {
            pdfMerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        } catch (IOException e) {
            throw new SparrowConverterException(e);
        }
    }

    /**
     * 获取文件流列表
     * @param fileList 文件列表
     * @return
     */
    private List<InputStream> getPdDocumentList(List<File> fileList) {
        List<InputStream> list = new ArrayList<>();
        try {
            for (File file : fileList) {
                list.add(new FileInputStream(file));
            }
        } catch (FileNotFoundException e) {
            throw new SparrowConverterException(e);
        }
        return list;
    }

}
