package cn.unminded.sparrow.gui.util;

import cn.unminded.sparrow.gui.Sparrow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

    private static final Logger logger = LoggerFactory.getLogger(Sparrow.class);

    public static Logger getLogger() {
        return logger;
    }
}
