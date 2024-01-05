import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class OneAtominIntegerForAllThreads {
    private static final int target = 1_000_000;

    //avg result 260ms

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        int processorsCount = Runtime.getRuntime().availableProcessors();
        int threadsCount = processorsCount * 2;

        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        List<Callable<Long>> tasks = new ArrayList<>();

        for (int i = 0; i < threadsCount; i++) {
            final int finalI = i;
            Callable<Long> task = () -> {
                long startTime = System.currentTimeMillis();
                for (int j = 0; j < target; j++) {
                    atomicInteger.incrementAndGet();
                }
                long invokeTime = System.currentTimeMillis() - startTime;
                System.out.println("Thread " + finalI + ": total time [" + invokeTime + "] atomicInteger = [" + atomicInteger.get() + "]");
                return invokeTime;
            };
            tasks.add(task);
        }

        List<Future<Long>> tasksResult = executor.invokeAll(tasks);
        executor.shutdown();
        long totalTime = 0;
        long maxTime = 0;
        for (Future<Long> future : tasksResult) {
            long taskTime = future.get();
            totalTime += taskTime;
            maxTime = Math.max(maxTime, taskTime);
        }
        System.out.println("Total time [" + totalTime + "], Max time = [" + maxTime + "]");
    }
}