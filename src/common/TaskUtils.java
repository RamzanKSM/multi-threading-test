package common;

import common.data_structure.AllTestResults;
import common.data_structure.CallableTask;
import common.data_structure.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.atomic.AtomicLong;

import static common.CommonUtils.getAtomicLongFromArray;
import static common.CommonUtils.soutThreadExecutionResult;
import static common.MultiThreadingTestSettings.QUANTITY_EXECUTIONS;
import static common.MultiThreadingTestSettings.TARGET;
import static common.MultiThreadingTestSettings.THREADS_COUNT;

public class TaskUtils {
    private static final Object lock = new Object();

    private static final double INCREASE_DOUBLE = 0.6;
    private static final long TARGET_FOR_THREAD = TARGET / THREADS_COUNT;
    public static List<CallableTask> getTasks(AtomicLong... atomicLongs) {
        return getTasks(null, atomicLongs);
    }
    public static List<CallableTask> getTasks(Double[] doubles, AtomicLong... atomicLongs) {
        List<CallableTask> tasks = new ArrayList<>();

        for (int counter = 0; counter < THREADS_COUNT; counter++) {

            AtomicLong atomicLong = getAtomicLongFromArray(atomicLongs, counter);

            CallableTask task = getTask(atomicLong, doubles, counter);
            tasks.add(task);
        }
        return tasks;
    }
    public static long getTimeExecutionByService(List<Future<Result>> futures) {
        try {
            boolean isDone;
            do {
                isDone = futures.get(0).isDone();
                synchronized (lock) {
                    lock.wait(5);
                }
            } while (!isDone);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return System.currentTimeMillis();
    }
    public static AllTestResults startAllThreads(List<CallableTask> tasks) {
        AllTestResults allTestResults = new AllTestResults();

        List<RunnableFuture<Result>> futures = getFuturesFromCallables(tasks);
        List<Thread> threads = getThreadsList(futures);

        long startTime = System.currentTimeMillis();
        try {
            for (Thread thread : threads) {
                thread.start();
            }
            for (Thread thread : threads) {
                thread.join();
            }
            long executionTime = System.currentTimeMillis() - startTime;

            allTestResults.threadResults = getResultsFromFuture(futures);
            allTestResults.totalTestExecutionTime = executionTime;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return allTestResults;
    }

    private static List<RunnableFuture<Result>> getFuturesFromCallables(List<CallableTask> tasks) {
        List<RunnableFuture<Result>> futures = new ArrayList<>(tasks.size());
        for (CallableTask task : tasks) {
            futures.add(new FutureTask<>(task.task()));
        }
        return futures;
    }
    private static List<Thread> getThreadsList(List<RunnableFuture<Result>> tasks) {
        List<Thread> threads = new ArrayList<>(tasks.size());
        for (int i = 0; i < tasks.size(); i++) {
            threads.add(new Thread(tasks.get(i), "Thread-" + i));
        }
        return threads;
    }
    public static List<Result> getResultsFromFuture(List<? extends Future<Result>> futures)
            throws ExecutionException, InterruptedException
    {
        List<Result> resultList = new ArrayList<>(futures.size());
        for (Future<Result> future : futures) {
            Result result = future.get();
            resultList.add(result);
        }
        return resultList;
    }
    private static CallableTask getTask(AtomicLong atomicLong, Double[] doubles, int counter) {
        if (doubles != null && doubles.length > 0) {
            return new CallableTask(
                    () -> {
                        String threadName = Thread.currentThread().getName();
                        Result result = new Result(threadName);

                        for (int i = 0; i < QUANTITY_EXECUTIONS; i++) {

                            double dbl = doubles[counter];

                            long startTime = System.currentTimeMillis();

                            for (int j = 0; j < TARGET_FOR_THREAD; j++) {
                                atomicLong.incrementAndGet();
                                dbl += INCREASE_DOUBLE;
                            }

                            long invokeTime = System.currentTimeMillis() - startTime;

                            result.setOrAddInvokeTime(invokeTime);
                            result.setOrAddAtomicLongValue(atomicLong);
                            result.setOrAddDoubleValue(dbl);
                            result.setAtomicLongID(atomicLong);
                        }
                        soutThreadExecutionResult(result);
                        return result;
                    }
            );
        } else {
            return new CallableTask(
                    () -> {
                        String threadName = Thread.currentThread().getName();
                        Result result = new Result(threadName);

                        for (int i = 0; i < QUANTITY_EXECUTIONS; i++) {

                            long startTime = System.currentTimeMillis();

                            for (int j = 0; j < TARGET_FOR_THREAD; j++) {
                                atomicLong.incrementAndGet();
                            }

                            long invokeTime = System.currentTimeMillis() - startTime;
                            result.setOrAddInvokeTime(invokeTime);
                            result.setOrAddAtomicLongValue(atomicLong);
                            result.setAtomicLongID(atomicLong);
                        }
                        soutThreadExecutionResult(result);
                        return result;
                    }
            );
        }
    }

}
