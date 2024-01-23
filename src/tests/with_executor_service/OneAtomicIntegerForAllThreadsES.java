package tests.with_executor_service;

import common.CommonUtils;
import common.data_structure.CallableTask;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static common.MultiThreadingTestSettings.EXECUTOR;
import static common.ResultUtils.getResultOfInvokes;
import static common.TaskUtils.getTasks;

public class OneAtomicIntegerForAllThreadsES {

    public static void main(String[] args) {

        AtomicInteger[] atomicIntegers = CommonUtils.getFullAtomicIntegerArray(1);

        List<CallableTask> tasks = getTasks(atomicIntegers);

        Map<String, Long> finalResult = getResultOfInvokes(EXECUTOR, tasks);

        EXECUTOR.shutdown();

        CommonUtils.soutAverageTime(finalResult);
    }
}