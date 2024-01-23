package common;

import common.data_structure.CallableTask;
import common.data_structure.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import static common.CommonUtils.getAtomicIntegerFromArray;
import static common.CommonUtils.soutThreadExecutionResult;
import static common.MultiThreadingTestSettings.TARGET;
import static common.MultiThreadingTestSettings.THREADS_COUNT;

public class TaskUtils {

    private static final double INCREASE_DOUBLE = 0.6;
    public static List<CallableTask> getTasks(AtomicInteger... atomicIntegers) {
        return getTasks(null, atomicIntegers);
    }
    public static List<CallableTask> getTasks(Double[] doubles, AtomicInteger... atomicIntegers) {
        List<CallableTask> tasks = new ArrayList<>();

        for (int counter = 0; counter < THREADS_COUNT; counter++) {

            AtomicInteger atomicInteger = getAtomicIntegerFromArray(atomicIntegers, counter);

            CallableTask task = getTask(atomicInteger, doubles, counter);
            tasks.add(task);
        }
        return tasks;
    }
    public static List<RunnableFuture<Result>> getFuturesFromCallables(List<CallableTask> tasks) {
        List<RunnableFuture<Result>> futures = new ArrayList<>(tasks.size());
        for (CallableTask task : tasks) {
            futures.add(new FutureTask<>(task.task()));
        }
        return futures;
    }

    public static List<Thread> getThreadsList(List<RunnableFuture<Result>> tasks) {
        List<Thread> threads = new ArrayList<>(tasks.size());
        for (RunnableFuture<Result> task : tasks) {
            threads.add(new Thread(task));
        }
        return threads;
    }
    public static void startAllThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    private static CallableTask getTask(AtomicInteger atomicInteger, Double[] doubles, int counter) {
        if (doubles != null && doubles.length > 0) {
            return new CallableTask(
                    () -> {
                        double dbl = doubles[counter];

                        long startTime = System.currentTimeMillis();

                        for (int j = 0; j < TARGET; j++) {
                            atomicInteger.incrementAndGet();
                            dbl += INCREASE_DOUBLE;
                        }

                        long invokeTime = System.currentTimeMillis() - startTime;

                        String threadName = Thread.currentThread().getName();
                        Result result = new Result(threadName, invokeTime, atomicInteger.get(), dbl);

                        soutThreadExecutionResult(result);

                        return result;
                    }
            );
        } else {
            return new CallableTask(
                    () -> {
                        long startTime = System.currentTimeMillis();

                        for (int j = 0; j < TARGET; j++) {
                            atomicInteger.incrementAndGet();
                        }

                        long invokeTime = System.currentTimeMillis() - startTime;

                        String threadName = Thread.currentThread().getName();
                        Result result = new Result(
                                threadName,
                                invokeTime,
                                atomicInteger.get()
                        );

                        soutThreadExecutionResult(result);

                        return result;
                    }
            );
        }
    }

}
