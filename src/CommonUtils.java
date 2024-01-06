import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class CommonUtils {
    private static final double INCREASE_DOUBLE = 0.6;
    private static final String TOTAL_TIME_KEY = "Total time";
    private static final String ATOMIC_INTEGER_KEY = "Atomic integer";
    private static final String DOUBLE_KEY = "Double";

    public static void soutAverageTime(Map<String, Number> results, int quantityExecutions) {
        for (String key : results.keySet()) {
            System.out.println(key + " average time [" + results.get(key).doubleValue() / quantityExecutions + "]");
        }
    }

    public static List<Callable<Map<String, Map<String, Number>>>> getTasks(int threadsCount, Double[] doubles, int target, AtomicInteger... atomicIntegers) {

        List<Callable<Map<String, Map<String, Number>>>> tasks = new ArrayList<>();

        for (int counter = 0; counter < threadsCount; counter++) {

            AtomicInteger atomicInteger = getAtomicIntegerFromArray(atomicIntegers, counter);

            Callable<Map<String, Map<String, Number>>> task = getTask(atomicInteger, doubles, counter, target);
            tasks.add(task);
        }
        return tasks;
    }

    private static AtomicInteger getAtomicIntegerFromArray(AtomicInteger[] atomicIntegers, int counter) {
        if (atomicIntegers.length != 1) {
            return atomicIntegers[counter];
        } else {
            return atomicIntegers[0];
        }
    }
    private static Callable<Map<String, Map<String, Number>>> getTask(AtomicInteger atomicInteger, Double[] doubles, int counter, int target) {
        if (doubles != null) {
            return () -> {
                double dbl = doubles[counter];
                Map<String, Map<String, Number>> map = new HashMap<>(1);

                long startTime = System.currentTimeMillis();

                for (int j = 0; j < target; j++) {
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

                return map;
            };
        } else {
            return () -> {
                Map<String, Map<String, Number>> map = new HashMap<>(1);

                long startTime = System.currentTimeMillis();

                for (int j = 0; j < target; j++) {
                    atomicInteger.incrementAndGet();
                }

                long invokeTime = System.currentTimeMillis() - startTime;

                Map<String, Number> data = new HashMap<>();
                data.put(TOTAL_TIME_KEY, invokeTime);
                data.put(ATOMIC_INTEGER_KEY, atomicInteger.get());

                String threadKey = getThreadKey(counter);
                map.put(threadKey, data);

                soutThreadExecutionResult(threadKey, invokeTime, atomicInteger, null);

                return map;
            };
        }
    }
    public static Map<String, Number> getResultOfInvokes(ExecutorService executor, List<Callable<Map<String, Map<String, Number>>>> tasks, int quantityExecution) {
        Map<String, Number> resultOfAllExecutions = new HashMap<>();
        for (int i = 0; i < quantityExecution; i++) {
            try {
                List<Future<Map<String, Map<String, Number>>>> tasksResult = executor.invokeAll(tasks);
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

    private static Number getTotalTimeExecutionResult(List<Future<Map<String, Map<String, Number>>>> taskResults, int counter) {
        try {
            return taskResults.get(counter).get().get(getThreadKey(counter)).get(TOTAL_TIME_KEY);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    private static String getThreadKey(int threadNumber) {
        return String.format("Thread %s", threadNumber);
    }

    private static void soutThreadExecutionResult(String threadKey, long invokeTime, AtomicInteger atomicInteger, Double dbl) {
        String outInfo = String.format("%s, Execution Time [%s], Atomic Integer [%s]", threadKey, invokeTime, atomicInteger);

        if (dbl != null) {
            outInfo += String.format(", Double [%s]", dbl);
        }

        System.out.println(outInfo);
    }
    private static void associateInvokeTimeWithThread(Map<String, Number> resultOfAllExecutions, String threadKey, Number invokeTime) {
        Number currentInvokeTime = resultOfAllExecutions.get(threadKey);
        if (currentInvokeTime == null) {
            resultOfAllExecutions.put(threadKey, invokeTime);
        } else {
            resultOfAllExecutions.put(threadKey, currentInvokeTime.longValue() + invokeTime.longValue());
        }
    }
    public static Double[] getFullDoubleArray(int length) {
        Double[] result = new Double[length];
        Arrays.fill(result, 0.0);
        return result;
    }
}
