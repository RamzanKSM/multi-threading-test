package tests.without_executor_service;

import common.CommonUtils;
import common.data_structure.CallableTask;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static common.CommonUtils.getFullAtomicIntegerArray;
import static common.ResultUtils.getResultOfInvokes;
import static common.TaskUtils.getTasks;

public class OneAtomicIntegerForAllThreads {
    public static void main(String[] args) {
        AtomicInteger[] atomicIntegers = getFullAtomicIntegerArray(1);

        List<CallableTask> tasks = getTasks(atomicIntegers);

        Map<String, Number> finalResult = getResultOfInvokes(tasks);

        CommonUtils.soutAverageTime(finalResult);
    }


}
