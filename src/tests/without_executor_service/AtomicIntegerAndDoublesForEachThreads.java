package tests.without_executor_service;

import common.ResultUtils;
import common.data_structure.CallableTask;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static common.CommonUtils.getFullAtomicIntegerArray;
import static common.CommonUtils.getFullDoubleArray;
import static common.CommonUtils.soutAverageTime;
import static common.MultiThreadingTestSettings.THREADS_COUNT;
import static common.TaskUtils.getTasks;

public class AtomicIntegerAndDoublesForEachThreads {
    public static void main(String[] args) {
        AtomicInteger[] atomicIntegers = getFullAtomicIntegerArray(THREADS_COUNT);
        Double[] doubles = getFullDoubleArray(THREADS_COUNT);

        List<CallableTask> tasks = getTasks(doubles, atomicIntegers);
        Map<String, Long> finalResult = ResultUtils.getResultOfInvokes(tasks);

        soutAverageTime(finalResult);
    }
}
