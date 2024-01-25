package tests.without_executor_service;

import common.CommonUtils;
import common.data_structure.CallableTask;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static common.CommonUtils.getFullAtomicIntegerArray;
import static common.MultiThreadingTestSettings.THREADS_COUNT;
import static common.ResultUtils.getResultOfInvokes;
import static common.TaskUtils.getTasks;

public class AtomicIntegersForEachThreads {
    public static void main(String[] args) {
        AtomicInteger[] atomicIntegers = getFullAtomicIntegerArray(THREADS_COUNT);
        List<CallableTask> tasks = getTasks(atomicIntegers);

        Map<String, Long> finalResult = getResultOfInvokes(tasks);

        CommonUtils.soutAverageTime(finalResult);
    }
}
