package cn.unminded.sparrow.define;

/**
 * 格式转换器
 */
public interface SparrowConverter {

    void prepare(SparrowContext context);

    void doConvert(SparrowContext context);

    void save(SparrowContext context);

    default void convert(SparrowContext context) {
        prepare(context);
        doConvert(context);
        save(context);
    }

}
