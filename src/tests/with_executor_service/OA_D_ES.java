package tests.with_executor_service;

import common.Testable;
import common.data_structure.CallableTask;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static common.CommonUtils.getFullAtomicIntegerArray;
import static common.CommonUtils.getFullDoubleArray;
import static common.MultiThreadingTestSettings.EXECUTOR;
import static common.MultiThreadingTestSettings.THREADS_COUNT;
import static common.TaskUtils.getTasks;

public class OA_D_ES implements Testable {
    public static void main(String[] args) {
        var test = new OA_D_ES();
        test.start();
    }

    @Override
    public void start() {
        AtomicInteger[] atomicIntegers = getFullAtomicIntegerArray(1);
        Double[] doubles = getFullDoubleArray(THREADS_COUNT);
        List<CallableTask> tasks = getTasks(doubles, atomicIntegers);

        executeTasks(EXECUTOR, tasks);
    }

    @Override
    public String getClassName() {
        return "OADES";
    }
}
