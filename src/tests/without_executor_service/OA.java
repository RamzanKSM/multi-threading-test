package tests.without_executor_service;

import common.Testable;
import common.data_structure.CallableTask;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static common.CommonUtils.getFullAtomicLongArray;
import static common.TaskUtils.getTasks;

public class OA implements Testable {
    public static void main(String[] args) {
        var test = new OA();
        test.start();
    }

    @Override
    public void start() {
        AtomicLong[] atomicLongs = getFullAtomicLongArray(1);
        List<CallableTask> tasks = getTasks(atomicLongs);

        executeTasks(tasks);
    }

    @Override
    public String getClassName() {
        return "OA";
    }
}
