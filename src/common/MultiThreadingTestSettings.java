package common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class MultiThreadingTestSettings {
    public static final int TARGET = 1_000_000;
    public static final int QUANTITY_EXECUTIONS = 10;
    public static final int PROCESSORS_COUNT = Runtime.getRuntime().availableProcessors();
    public static final int THREADS_COUNT = PROCESSORS_COUNT - 1;
    public static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(THREADS_COUNT);

}
