package cn.unminded.sparrow.metric;

import java.util.concurrent.atomic.AtomicInteger;

public class ConvertMetric {

    private AtomicInteger total;

    private AtomicInteger success;

    private AtomicInteger fail;

    public AtomicInteger getTotal() {
        return total;
    }

    public ConvertMetric setTotal(AtomicInteger total) {
        this.total = total;
        return this;
    }

    public AtomicInteger getSuccess() {
        return success;
    }

    public ConvertMetric setSuccess(AtomicInteger success) {
        this.success = success;
        return this;
    }

    public AtomicInteger getFail() {
        return fail;
    }

    public ConvertMetric setFail(AtomicInteger fail) {
        this.fail = fail;
        return this;
    }

    public static ConvertMetric defaultConvertMetric() {
        return new ConvertMetric()
                .setFail(new AtomicInteger())
                .setSuccess(new AtomicInteger())
                .setTotal(new AtomicInteger());
    }

}
