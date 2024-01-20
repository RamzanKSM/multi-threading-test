package common;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static common.MultiThreadingTestSettings.QUANTITY_EXECUTIONS;

public class CommonUtils {
    public static void soutAverageTime(Map<String, Number> results) {

                Map<String, Number> sortedMap = results.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.comparingDouble(Number::doubleValue)))
                        .collect(Collectors.toMap(
                                    Map.Entry::getKey,
                                    Map.Entry::getValue,
                                    (e1, e2) -> e1,
                                    LinkedHashMap::new
                                )
                        );
                sortedMap.forEach((key, value) -> System.out.printf("%s average time [%s]\n", key, value.doubleValue() / QUANTITY_EXECUTIONS));
    }
    public static AtomicInteger getAtomicIntegerFromArray(AtomicInteger[] atomicIntegers, int counter) {
        if (atomicIntegers.length > 1) {
            return atomicIntegers[counter];
        } else {
            return atomicIntegers[0];
        }
    }
    public static String getThreadKey(int threadNumber) {
        return String.format("Thread %s", threadNumber);
    }

    public static void soutThreadExecutionResult(String threadKey, long invokeTime, AtomicInteger atomicInteger, Double dbl) {
        String outInfo = String.format("%s, Execution Time [%s], Atomic Integer [%s]", threadKey, invokeTime, atomicInteger);

        if (dbl != null) {
            outInfo += String.format(", Double [%s]", dbl);
        }

        System.out.println(outInfo);
    }

    public static void soutTestsNumber(int counter) {
        System.out.printf("TEST %s\n", counter);
    }
    public static AtomicInteger[] getFullAtomicIntegerArray(int length) {
        AtomicInteger[] result = new AtomicInteger[length];
        for (int i = 0; i < length; i++) {
            result[i] = new AtomicInteger(0);
        }
        return result;
    }
    public static Double[] getFullDoubleArray(int length) {
        Double[] result = new Double[length];
        Arrays.fill(result, 0.0);
        return result;
    }
}
