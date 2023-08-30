package cn.unminded.sparrow.config;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class SparrowThreadPool {

    private static final ThreadPoolExecutor SPARROW_EXECUTOR;

    static {
        int processors = Runtime.getRuntime().availableProcessors();
        int coreSize = Math.min(3, processors * 10);
        int maxSize = Math.max(10, processors * 20);
        int keepAliveTime = 10;
        int capacity = 1000;
        SPARROW_EXECUTOR = new ThreadPoolExecutor(coreSize, maxSize, keepAliveTime, TimeUnit.MINUTES, new ArrayBlockingQueue<>(capacity), new ThreadFactory() {
            private final String namePrefix = "sparrow-pool";
            private final AtomicInteger threadNumber = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, namePrefix + threadNumber.getAndIncrement());
                if (t.isDaemon())
                    t.setDaemon(false);
                if (t.getPriority() != Thread.NORM_PRIORITY)
                    t.setPriority(Thread.NORM_PRIORITY);
                return t;
            }
        }, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static ThreadPoolExecutor getSparrowExecutor() {
        return SPARROW_EXECUTOR;
    }

    public static <U> CompletableFuture<U> execute(Supplier<U> supplier) {
        return CompletableFuture.supplyAsync(supplier, SPARROW_EXECUTOR);
    }

}
