package common;

import common.data_structure.AllTestResults;
import common.data_structure.Result;
import tests.with_executor_service.OA_D_ES;
import tests.with_executor_service.OA_ES;
import tests.with_executor_service.XA_D_ES;
import tests.with_executor_service.XA_ES;
import tests.without_executor_service.OA;
import tests.without_executor_service.OA_D;
import tests.without_executor_service.XA;
import tests.without_executor_service.XA_D;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static common.MultiThreadingTestSettings.RESULT_FILE;
import static common.MultiThreadingTestSettings.SHOW_EACH_THREAD_RESULT;
import static common.MultiThreadingTestSettings.THREADS_COUNT;

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

    public static void soutAllThreadExecutionResult(AllTestResults allTestResults) {
        long resultsInvokeTimeSum = allTestResults.threadResults.stream().mapToLong(result -> result.invokeTime).sum();
        long averageTime = resultsInvokeTimeSum / allTestResults.threadResults.size();
        String outputInfo = getOutputString(allTestResults, averageTime);
        writeResultInFile(outputInfo);
        System.out.print(outputInfo);
    }

    private static String getOutputString(AllTestResults allTestResults, long averageTime) {
        String outInfo;
        if (SHOW_EACH_THREAD_RESULT) {
            outInfo = String.format(
                    """
                                Total time [%s],
                                Average time [%s]
                        """, allTestResults.totalTestExecutionTime, averageTime
            );
        } else {
            outInfo = String.format(
                    """
                                %s,
                                    Total time [%s],
                                    Average time [%s]
                            """, allTestResults.testName, allTestResults.totalTestExecutionTime, averageTime
            );
        }
        return outInfo;
    }

    private static void writeResultInFile(String output) {
        try(FileWriter writer = new FileWriter(RESULT_FILE, true)) {
            writer.write(output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void logThreadCount() {
        try(FileWriter writer = new FileWriter(RESULT_FILE, true)) {
            String threadCount = "Thread count - " + THREADS_COUNT + " {\n";
            writer.write(threadCount);
            System.out.print(threadCount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void logCloseTestBlockInFile() {
        try(FileWriter writer = new FileWriter(RESULT_FILE, true)) {
            String closeBlockChar = "}\n";

            writer.write(closeBlockChar);
            System.out.print(closeBlockChar);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void soutThreadExecutionResult(Result result) {
        if (SHOW_EACH_THREAD_RESULT) {
            String outInfo = String.format("        %s, Execution Time [%s], Atomic Long [%s] {%s}", result.threadName, result.invokeTime, result.atomicLongValue, result.atomicLongID);

            if (result.doubleValue != 0) {
                outInfo += String.format(", Double [%s]", result.doubleValue);
            }
            System.out.println(outInfo);
        }
    }

    public static void soutTestName(String className) {
        if (SHOW_EACH_THREAD_RESULT) {
            System.out.printf("TEST: %s\n", className);
        }
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
