package common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractMultiThreadingTest {
    protected static final int TARGET = 100_000_000;
    protected static final int QUANTITY_EXECUTIONS = 1;
    protected static final int PROCESSORS_COUNT = Runtime.getRuntime().availableProcessors();
    protected static final int THREADS_COUNT = PROCESSORS_COUNT;
    protected static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(THREADS_COUNT);

}
