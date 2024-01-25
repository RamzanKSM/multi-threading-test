package tests.without_executor_service;

import common.CommonUtils;
import common.data_structure.CallableTask;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static common.MultiThreadingTestSettings.THREADS_COUNT;
import static common.ResultUtils.getResultOfInvokes;
import static common.TaskUtils.getTasks;

public class OneAtomicIntegerForThreadAndDoublesForEachThreads {
    public static void main(String[] args) {
        AtomicInteger[] atomicIntegers = CommonUtils.getFullAtomicIntegerArray(1);
        Double[] doubles = CommonUtils.getFullDoubleArray(THREADS_COUNT);
        List<CallableTask> tasks = getTasks(doubles, atomicIntegers);

        Map<String, Long> finalResult = getResultOfInvokes(tasks);

        CommonUtils.soutAverageTime(finalResult);
    }
}
