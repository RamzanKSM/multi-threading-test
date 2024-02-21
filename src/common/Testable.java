package common;

import common.data_structure.AllTestResults;
import common.data_structure.CallableTask;
import common.data_structure.Result;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static common.CommonUtils.soutAllThreadExecutionResult;
import static common.CommonUtils.soutTestName;
import static common.TaskUtils.getTimeExecutionByService;
import static common.TaskUtils.getResultsFromFuture;
import static common.TaskUtils.startAllThreads;

public interface Testable {

    void start();
    String getClassName();

    default void executeTasks(List<CallableTask> tasks) {
        String className = getClassName();
        soutTestName(className);

        AllTestResults allTestResults = startAllThreads(tasks);

        allTestResults.testName = className;
        soutAllThreadExecutionResult(allTestResults);
    }

    default void executeTasks(ExecutorService executor, List<CallableTask> tasks) {
        try {
            List<Callable<Result>> callables = tasks.stream().map(CallableTask::task).toList();
            String className = getClassName();
            soutTestName(className);

            long startTime = System.currentTimeMillis();

            List<Future<Result>> futures = executor.invokeAll(callables);

            long executionTime = getTimeExecutionByService(futures) - startTime;
            List<Result> results = getResultsFromFuture(futures);
            AllTestResults allTestResults = new AllTestResults(className, executionTime, results);

            soutAllThreadExecutionResult(allTestResults);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
