package tests;

import common.MultiThreadingTestSettings;
import common.Testable;

import java.util.List;

import static common.CommonUtils.getAllTests;

public class AllTests {
    public static void main(String[] args) {
        long totalTime = System.nanoTime();
        List<Testable> tests = getAllTests();
        for(Testable test : tests) {
            test.start();
        }
        MultiThreadingTestSettings.EXECUTOR.shutdown();
        System.out.println(System.nanoTime() - totalTime);
    }
}
