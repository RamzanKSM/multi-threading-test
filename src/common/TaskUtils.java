package common;

import common.data_structure.CallableTask;
import common.data_structure.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import static common.CommonUtils.getAtomicIntegerFromArray;
import static common.CommonUtils.getThreadKey;
import static common.CommonUtils.soutThreadExecutionResult;
import static common.MultiThreadingTestSettings.TARGET;
import static common.MultiThreadingTestSettings.THREADS_COUNT;

public class TaskUtils {

    private static final double INCREASE_DOUBLE = 0.6;
    private static final String TOTAL_TIME_KEY = "Total time";
    private static final String ATOMIC_INTEGER_KEY = "Atomic integer";
    private static final String DOUBLE_KEY = "Double";
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
            futures.add(new FutureTask<>(task.getTask()));
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
    synchronized public static void startAllThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            thread.start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private static CallableTask getTask(AtomicInteger atomicInteger, Double[] doubles, int counter) {
        if (doubles != null && doubles.length > 0) {
            return new CallableTask(
                    () -> {
                        double dbl = doubles[counter];
                        Map<String, Map<String, Number>> map = new HashMap<>(1);

                        long startTime = System.currentTimeMillis();

                        for (int j = 0; j < TARGET; j++) {
                            atomicInteger.incrementAndGet();
                            dbl += INCREASE_DOUBLE;
                        }

                        long invokeTime = System.currentTimeMillis() - startTime;

                        Map<String, Number> data = new HashMap<>();
                        data.put(TOTAL_TIME_KEY, invokeTime);
                        data.put(ATOMIC_INTEGER_KEY, atomicInteger.get());
                        data.put(DOUBLE_KEY, dbl);

                        String threadKey = getThreadKey(counter);
                        map.put(threadKey, data);

                        soutThreadExecutionResult(threadKey, invokeTime, atomicInteger, dbl);

                        return new Result(map);
                    }
            );
        } else {
            return new CallableTask(
                    () -> {
                        Map<String, Map<String, Number>> map = new HashMap<>(1);

                        long startTime = System.currentTimeMillis();

                        for (int j = 0; j < TARGET; j++) {
                            atomicInteger.incrementAndGet();
                        }

                        long invokeTime = System.currentTimeMillis() - startTime;

                        Map<String, Number> data = new HashMap<>();
                        data.put(TOTAL_TIME_KEY, invokeTime);
                        data.put(ATOMIC_INTEGER_KEY, atomicInteger.get());

                        String threadKey = getThreadKey(counter);
                        map.put(threadKey, data);

                        soutThreadExecutionResult(threadKey, invokeTime, atomicInteger, null);

                        return new Result(map);
                    }
            );
        }
    }

}
