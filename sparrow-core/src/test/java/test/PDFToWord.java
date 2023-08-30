package test;

import cn.unminded.sparrow.pdf.PDFLayoutTextStripper;
import org.apache.pdfbox.contentstream.operator.color.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;

public class PDFToWord {
    public static void main(String[] args) throws IOException {
//        String pdfFile = "求职笔试大全.pdf";
//        PDDocument pdDocument = PDDocument.load(new File(pdfFile));
//        PDFTextStripper pdfTextStripper = new LosslessPDFTextStripper();
//        PDPage page = pdDocument.getPage(0);
//        pdfTextStripper.processPage(page);


        writeText();
    }

    public static void writeText() {
        try {
            //输入pdf的路径
            String pdfFile = "求职笔试大全.pdf";
            //将pdf加载到对象中去
            PDDocument doc = PDDocument.load(new File(pdfFile));
            //得到pdf的页数
            int pagenumber = doc.getNumberOfPages();
            //设置转换后的名字
//            pdfFile = pdfFile.substring(0, pdfFile.lastIndexOf("."));
//            String fileName = pdfFile + ".doc";
            String fileName="word.doc";
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(fileName);
            //设置输出字符集为UTF-8 因此该word应该使用UTF-8格式打开 如果你出现乱码那么你可以自己修改一下这里的格式
            Writer writer = new OutputStreamWriter(fos, "GB2312");
            PDFTextStripper stripper = new PDFLayoutTextStripper();
            stripper.setSortByPosition(true);// 排序
            stripper.setStartPage(0);// 设置转换的开始页
            stripper.setEndPage(1);// 设置转换的结束页
            stripper.writeText(doc, writer);
            writer.close();
            doc.close();
            System.out.println("pdf转换word成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void prepare() {
        PDFTextStripper pdfTextStripper = null;
        try {
            pdfTextStripper = new PDFTextStripper();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfTextStripper.addOperator(new SetStrokingColorSpace());
        pdfTextStripper.addOperator(new SetNonStrokingColorSpace());
        pdfTextStripper.addOperator(new SetStrokingDeviceCMYKColor());
        pdfTextStripper.addOperator(new SetNonStrokingDeviceCMYKColor());
        pdfTextStripper.addOperator(new SetNonStrokingDeviceRGBColor());
        pdfTextStripper.addOperator(new SetStrokingDeviceRGBColor());
        pdfTextStripper.addOperator(new SetNonStrokingDeviceGrayColor());
        pdfTextStripper.addOperator(new SetStrokingDeviceGrayColor());
        pdfTextStripper.addOperator(new SetStrokingColor());
        pdfTextStripper.addOperator(new SetStrokingColorN());
        pdfTextStripper.addOperator(new SetNonStrokingColor());
        pdfTextStripper.addOperator(new SetNonStrokingColorN());
    }
}
