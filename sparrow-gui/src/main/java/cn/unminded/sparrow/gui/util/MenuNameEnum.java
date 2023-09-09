package cn.unminded.sparrow.gui.util;

public enum MenuNameEnum {

    /** 工具栏按钮 */
    TRANS_MODE_MENU("模式", 'M'),

    IMAGE_TO_PDF_MENU("图片转PDF", 'I'),

    PDF_TO_WORD_MENU("PDF转WORD", 'W'),

    PDF_SPLIT("PDF分割", 'J'),

    PDF_MERGE("PDF合并", 'M'),

    HELP_MENU("帮助", 'H'),

    ABOUT_MENU("关于", 'A'),

    FEEDBACK_MENU("快速反馈", 'F'),

    DONATE_MENU("支持作者", null),

    /** 控制按钮 */
    START_MENU("开始", 'S'),


    ;


    private String name;

    private Character mnemonic;

    MenuNameEnum(String name, Character mnemonic) {
        this.name = name;
        this.mnemonic = mnemonic;
    }

    public String getName() {
        return name;
    }

    public Character getMnemonic() {
        return mnemonic;
    }
}
