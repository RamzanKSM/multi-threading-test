package tests.with_executor_service;

import common.CommonUtils;
import common.Testable;
import common.data_structure.CallableTask;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static common.MultiThreadingTestSettings.EXECUTOR;
import static common.TaskUtils.getTasks;

public class OA_ES implements Testable {
    public static void main(String[] args) {
        var test = new OA_ES();
        test.start();
        EXECUTOR.shutdown();
    }
    @Override
    public void start() {
        AtomicLong[] atomicLongs = CommonUtils.getFullAtomicLongArray(1);
        List<CallableTask> tasks = getTasks(atomicLongs);
        executeTasks(EXECUTOR, tasks);
    }

    @Override
    public String getClassName() {
        return "OAES";
    }
}