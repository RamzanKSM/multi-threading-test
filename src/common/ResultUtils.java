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

import static common.CommonUtils.getThreadKey;
import static common.CommonUtils.soutTestsNumber;
import static common.MultiThreadingTestSettings.QUANTITY_EXECUTIONS;
import static common.TaskUtils.getFuturesFromCallables;
import static common.TaskUtils.getThreadsList;
import static common.TaskUtils.startAllThreads;

public class ResultUtils {

    private static final String TOTAL_TIME_KEY = "Total time";
    public static Map<String, Number> getResultOfInvokes(List<CallableTask> tasks) {
        Map<String, Number> resultOfAllExecutions = new HashMap<>(tasks.size());
        List<RunnableFuture<Result>> futures = getFuturesFromCallables(tasks);
        List<Thread> threads = getThreadsList(futures);
        for (int i = 0; i < QUANTITY_EXECUTIONS; i++) {
            soutTestsNumber(i);
            startAllThreads(threads);
            for (int j = 0; j < futures.size(); j++) {
                Number time = getTotalTimeExecutionResult(futures.get(j), i);
                associateInvokeTimeWithThread(resultOfAllExecutions, getThreadKey(j), time);
            }
        }
        return resultOfAllExecutions;
    }
    public static Map<String, Number> getResultOfInvokes(ExecutorService executor, List<CallableTask> tasks) {
        Map<String, Number> resultOfAllExecutions = new HashMap<>(tasks.size());
        List<Callable<Result>> callables = tasks.stream().map(CallableTask::getTask).toList();
        for (int i = 0; i < QUANTITY_EXECUTIONS; i++) {
            try {
                soutTestsNumber(i);
                List<Future<Result>> tasksResult = executor.invokeAll(callables);
                for (int j = 0; j < tasksResult.size(); j++) {
                    Number time = getTotalTimeExecutionResult(tasksResult, j);
                    associateInvokeTimeWithThread(resultOfAllExecutions, getThreadKey(j), time);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return resultOfAllExecutions;
    }
    private static Number getTotalTimeExecutionResult(Future<Result> taskResult, int counter) {
        try {
            return taskResult.get().getResultMap().get(getThreadKey(counter)).get(TOTAL_TIME_KEY);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    private static Number getTotalTimeExecutionResult(List<Future<Result>> taskResults, int counter) {
        try {
            return taskResults.get(counter).get().getResultMap().get(getThreadKey(counter)).get(TOTAL_TIME_KEY);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    private static void associateInvokeTimeWithThread(Map<String, Number> resultOfAllExecutions, String threadKey, Number invokeTime) {
        Number currentInvokeTime = resultOfAllExecutions.get(threadKey);
        if (currentInvokeTime == null) {
            resultOfAllExecutions.put(threadKey, invokeTime);
        } else {
            resultOfAllExecutions.put(threadKey, currentInvokeTime.longValue() + invokeTime.longValue());
        }
    }
}
