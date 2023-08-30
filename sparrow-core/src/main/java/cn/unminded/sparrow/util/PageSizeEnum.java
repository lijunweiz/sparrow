package cn.unminded.sparrow.util;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

public enum PageSizeEnum {

    ORIGINAL(null),

    A3(PDRectangle.A3),

    A4(PDRectangle.A4),

    ;

    private PDRectangle pdRectangle;

    PageSizeEnum(PDRectangle pdRectangle) {
        this.pdRectangle = pdRectangle;
    }

    public PDRectangle getPdRectangle() {
        return pdRectangle;
    }
}
