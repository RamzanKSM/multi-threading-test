package common;

import common.data_structure.Result;
import tests.with_executor_service.XA_D_ES;
import tests.with_executor_service.XA_ES;
import tests.with_executor_service.OA_ES;
import tests.with_executor_service.OA_D_ES;
import tests.without_executor_service.XA_D;
import tests.without_executor_service.XA;
import tests.without_executor_service.OA;
import tests.without_executor_service.OA_D;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CommonUtils {
    public static AtomicInteger getAtomicIntegerFromArray(AtomicInteger[] atomicIntegers, int counter) {
        if (atomicIntegers.length > 1) {
            return atomicIntegers[counter];
        } else {
            return atomicIntegers[0];
        }
    }
    public static void soutThreadExecutionResult(Result result) {
        String outInfo = String.format("%s, Execution Time [%s], Atomic Integer [%s]", result.threadName(), result.invokeTime(), result.atomicIntegerValue());

        if (result.doubleValue() != 0) {
            outInfo += String.format(", Double [%s]", result.doubleValue());
        }

        System.out.println(outInfo);
    }

    public static void soutTestName(String className) {
        System.out.printf("TEST: %s\n", className);
    }

    public static List<Testable> getAllTests() {
        return List.of(
                new OA(),
                new OA_D(),
                new XA(),
                new XA_D(),
                new OA_D_ES(),
                new OA_ES(),
                new XA_D_ES(),
                new XA_ES()
        );
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
