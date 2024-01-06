import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class OneAtomicIntegerFroThreadAndDoublesForEachThreads extends AbstractMultiThreadingTest {

    public static void main(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger(0);
        Double[] doubles = CommonUtils.getFullDoubleArray(THREADS_COUNT);

        List<Callable<Map<String, Map<String, Number>>>> tasks = CommonUtils.getTasks(THREADS_COUNT, doubles, TARGET, atomicInteger);

        Map<String, Number> finalResult = CommonUtils.getResultOfInvokes(EXECUTOR, tasks, QUANTITY_EXECUTIONS);

        EXECUTOR.shutdown();

        CommonUtils.soutAverageTime(finalResult, QUANTITY_EXECUTIONS);
    }

}
