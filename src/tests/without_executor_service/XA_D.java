package tests.without_executor_service;

import common.Testable;
import common.data_structure.CallableTask;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static common.CommonUtils.getFullAtomicLongArray;
import static common.CommonUtils.getFullDoubleArray;
import static common.MultiThreadingTestSettings.THREADS_COUNT;
import static common.TaskUtils.getTasks;

public class XA_D implements Testable {
    public static void main(String[] args) {
        var test = new XA_D();
        test.start();
    }

    @Override
    public void start() {
        AtomicLong[] atomicLongs = getFullAtomicLongArray(THREADS_COUNT);
        Double[] doubles = getFullDoubleArray(THREADS_COUNT);

        List<CallableTask> tasks = getTasks(doubles, atomicLongs);
        executeTasks(tasks);
    }

    @Override
    public String getClassName() {
        return "XAD";
    }
}
