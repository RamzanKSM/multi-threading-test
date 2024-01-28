package common;

import common.data_structure.CallableTask;
import common.data_structure.Result;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import static common.CommonUtils.soutTestName;
import static common.TaskUtils.startAllThreads;

public interface Testable {

    void start();
    String getClassName();

    default void executeTasks(List<CallableTask> tasks) {
        soutTestName(getClassName());
        startAllThreads(tasks);
    }

    default void executeTasks(ExecutorService executor, List<CallableTask> tasks) {
        List<Callable<Result>> callables = tasks.stream().map(CallableTask::task).toList();
            try {
                soutTestName(getClassName());
                executor.invokeAll(callables);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
    }

}
