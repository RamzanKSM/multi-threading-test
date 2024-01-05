import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class OneAtomicIntegerFroThreadAndDoublesForEachThreads {
    private static final int target = 1_000_000;
    private static final double plus = 0.5;
    private static final int times = 10;

    private static final String TOTAL_TIME_KEY = "Total time";

    //avg result 280ms
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        int processorsCount = Runtime.getRuntime().availableProcessors();
        int threadsCount = processorsCount;

        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Double[] doubles = getFullDoubleArray(threadsCount);
        List<Callable<Map<String, Map<String, Number>>>> tasks = new ArrayList<>();

        for (int i = 0; i < threadsCount; i++) {
            final int finalI = i;
            Callable<Map<String, Map<String, Number>>> task = () -> {
                Double dbl = doubles[finalI];
                Map<String, Map<String, Number>> map = new HashMap<>(1);

                long startTime = System.currentTimeMillis();

                for (int j = 0; j < target; j++) {
                    atomicInteger.incrementAndGet();
                    dbl += plus;
                }

                long invokeTime = System.currentTimeMillis() - startTime;

                Map<String, Number> data = new HashMap<>();
                data.put(TOTAL_TIME_KEY, invokeTime);
                data.put("atomic integer", atomicInteger.get());
                data.put("double", dbl);

                map.put("Thread" + finalI, data);

                return map;
            };
            tasks.add(task);
        }

        Map<String, Number> finalResult = new HashMap<>();
        for (int i = 0; i < times; i++) {
            List<Future<Map<String, Map<String, Number>>>> tasksResult = executor.invokeAll(tasks);
            executor.awaitTermination(1, TimeUnit.SECONDS);
            for (int j = 0; j < tasksResult.size(); j++) {
                String threadKey = "Thread" + j;
                Number time = tasksResult.get(j).get().get(threadKey).get(TOTAL_TIME_KEY);
                Number currentInvokeTime = finalResult.get(threadKey);

                if (currentInvokeTime == null) {
                    finalResult.put(threadKey, time);
                } else {
                    finalResult.put(threadKey, time.longValue() + currentInvokeTime.longValue());
                }
                System.out.println(threadKey + " total time [ " + time + " ]");
            }
        }
        executor.shutdown();
        for (String key : finalResult.keySet()) {
            System.out.println(key + " average time [" + finalResult.get(key).longValue() / times + "]");
        }
    }
    private static Double[] getFullDoubleArray(int length) {
        Double[] result = new Double[length];
        Arrays.fill(result, 0.0);
        return result;
    }
}
