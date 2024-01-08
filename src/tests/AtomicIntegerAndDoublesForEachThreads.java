package tests;

import common.AbstractMultiThreadingTest;
import common.CommonUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerAndDoublesForEachThreads extends AbstractMultiThreadingTest {
    public static void main(String[] args) {
        AtomicInteger[] atomicIntegers = CommonUtils.getFullAtomicIntegerArray(THREADS_COUNT);
        Double[] doubles = CommonUtils.getFullDoubleArray(THREADS_COUNT);

        List<Callable<Map<String, Map<String, Number>>>> tasks = CommonUtils.getTasks(doubles, atomicIntegers);

        Map<String, Number> finalResult = CommonUtils.getResultOfInvokes(EXECUTOR, tasks);

        EXECUTOR.shutdown();

        CommonUtils.soutAverageTime(finalResult);
    }
}
