package tests;

import common.AbstractMultiThreadingTest;
import common.CommonUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class OneAtominIntegerForAllThreads extends AbstractMultiThreadingTest {

    public static void main(String[] args) {

        AtomicInteger[] atomicIntegers = CommonUtils.getFullAtomicIntegerArray(1);

        List<Callable<Map<String, Map<String, Number>>>> tasks = CommonUtils.getTasks(THREADS_COUNT, TARGET, atomicIntegers);

        Map<String, Number> finalResult = CommonUtils.getResultOfInvokes(EXECUTOR, tasks, QUANTITY_EXECUTIONS);

        EXECUTOR.shutdown();

        CommonUtils.soutAverageTime(finalResult, QUANTITY_EXECUTIONS);
    }
}