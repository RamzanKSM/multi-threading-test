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
import java.util.stream.Collectors;

import static common.CommonUtils.soutTestsNumber;
import static common.MultiThreadingTestSettings.QUANTITY_EXECUTIONS;
import static common.TaskUtils.getFuturesFromCallables;
import static common.TaskUtils.getThreadsList;
import static common.TaskUtils.startAllThreads;

public class ResultUtils {

    public static Map<String, Long> getResultOfInvokes(List<CallableTask> tasks) {
        Map<String, Long> averageResults = new HashMap<>(tasks.size());
        List<RunnableFuture<Result>> futures = getFuturesFromCallables(tasks);
        List<Thread> threads = getThreadsList(futures);
        for (int i = 0; i < QUANTITY_EXECUTIONS; i++) {
            soutTestsNumber(i);
            startAllThreads(threads);
            averageResults.putAll(associateInvokeTimeWithThread(futures));
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
                averageResults.putAll(associateInvokeTimeWithThread(tasksResult));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return averageResults;
    }

    private static Map<String, Long> associateInvokeTimeWithThread(List<? extends Future<Result>> futures) {
        return futures.parallelStream().map(resultFuture -> {
            try {
                return resultFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toMap(Result::threadName, Result::invokeTime, Long::sum));
    }
}
