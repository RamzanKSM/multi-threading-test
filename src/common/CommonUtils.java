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
import java.util.concurrent.atomic.AtomicLong;

public class CommonUtils {
    public static List<Testable> getAllTests() {
        return List.of(
                new OA(),
                new OA_ES(),
                new OA_D(),
                new OA_D_ES(),
                new XA(),
                new XA_ES(),
                new XA_D(),
                new XA_D_ES()
        );
    }
    public static void soutThreadExecutionResult(Result result) {
        String outInfo = String.format("%s, Execution Time [%s], Atomic Long [%s] {%s}", result.threadName, result.invokeTime, result.atomicLongValue, result.atomicLongID);

        if (result.doubleValue != 0) {
            outInfo += String.format(", Double [%s]", result.doubleValue);
        }

        System.out.println(outInfo);
    }

    public static void soutTestName(String className) {
        System.out.printf("TEST: %s\n", className);
    }

    public static AtomicLong getAtomicLongFromArray(AtomicLong[] atomicLongs, int counter) {
        if (atomicLongs.length > 1) {
            return atomicLongs[counter];
        } else {
            return atomicLongs[0];
        }
    }
    public static AtomicLong[] getFullAtomicLongArray(int length) {
        AtomicLong[] result = new AtomicLong[length];
        for (int i = 0; i < length; i++) {
            result[i] = new AtomicLong(0);
        }
        return result;
    }
    public static Double[] getFullDoubleArray(int length) {
        Double[] result = new Double[length];
        Arrays.fill(result, 0.0);
        return result;
    }
}
