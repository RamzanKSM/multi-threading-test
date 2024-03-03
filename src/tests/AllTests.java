package tests;

import common.MultiThreadingTestSettings;
import common.Testable;

import static common.CommonUtils.getAllTests;
import static common.CommonUtils.logCloseTestBlockInFile;
import static common.CommonUtils.logThreadCount;
import static common.MultiThreadingTestSettings.DELETE_OLD_FILE;
import static common.MultiThreadingTestSettings.RESULT_FILE;

public class AllTests {
    public static void main(String[] args) {
        if (DELETE_OLD_FILE) {
            RESULT_FILE.delete();
        }
        logThreadCount();
        for(Testable test : getAllTests()) {
            test.start();
        }
        MultiThreadingTestSettings.EXECUTOR.shutdown();
        logCloseTestBlockInFile();
    }
}
