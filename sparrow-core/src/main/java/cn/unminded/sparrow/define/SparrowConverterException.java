package cn.unminded.sparrow.define;

public class SparrowConverterException extends RuntimeException {

    public SparrowConverterException() {
    }

    public SparrowConverterException(String message) {
        super(message);
    }

    public SparrowConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    public SparrowConverterException(Throwable cause) {
        super(cause);
    }

    public SparrowConverterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
