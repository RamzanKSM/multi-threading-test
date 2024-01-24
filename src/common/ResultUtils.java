package common;

import common.data_structure.CallableTask;
import common.data_structure.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;

import static common.CommonUtils.soutTestsNumber;
import static common.MultiThreadingTestSettings.QUANTITY_EXECUTIONS;
import static common.TaskUtils.startAllThreads;

public class ResultUtils {

    public static Map<String, Long> getResultOfInvokes(List<CallableTask> tasks) {
        Map<String, Long> averageResults = new HashMap<>(tasks.size());
        for (int i = 0; i < QUANTITY_EXECUTIONS; i++) {
            soutTestsNumber(i);
            List<RunnableFuture<Result>> futures = startAllThreads(tasks);
            associateInvokeTimeWithThread(averageResults, futures);
        }
        return averageResults;
    }

    public static Map<String, Long> getResultOfInvokes(ExecutorService executor, List<CallableTask> tasks) {
        Map<String, Long> averageResults = new HashMap<>(tasks.size());
        List<Callable<Result>> callables = tasks.stream().map(CallableTask::task).toList();
        for (int i = 0; i < QUANTITY_EXECUTIONS; i++) {
            try {
                soutTestsNumber(i);
                List<Future<Result>> tasksResult = executor.invokeAll(callables);
                associateInvokeTimeWithThread(averageResults, tasksResult);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return averageResults;
    }

    private static void associateInvokeTimeWithThread(Map<String, Long> averageResult, List<? extends Future<Result>> futures) {
        futures.stream().map(resultFuture -> {
            try {
                return resultFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).forEach(
                result -> averageResult.compute(
                        result.threadName(),
                        (key, value) -> value == null
                                ? result.invokeTime()
                                : Long.sum(value, result.invokeTime())
                )
        );
    }
}
