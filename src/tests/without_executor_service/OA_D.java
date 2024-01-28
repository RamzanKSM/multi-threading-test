package tests.without_executor_service;

import common.CommonUtils;
import common.Testable;
import common.data_structure.CallableTask;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static common.MultiThreadingTestSettings.THREADS_COUNT;
import static common.TaskUtils.getTasks;

public class OA_D implements Testable {
    public static void main(String[] args) {
        var test = new OA_D();
        test.start();
    }
    @Override
    public void start() {
        AtomicInteger[] atomicIntegers = CommonUtils.getFullAtomicIntegerArray(1);
        Double[] doubles = CommonUtils.getFullDoubleArray(THREADS_COUNT);
        List<CallableTask> tasks = getTasks(doubles, atomicIntegers);

        executeTasks(tasks);
    }

    @Override
    public String getClassName() {
        return "OAD";
    }
}
