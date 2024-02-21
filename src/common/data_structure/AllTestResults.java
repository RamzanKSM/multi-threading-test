package common.data_structure;

import java.util.List;

public class AllTestResults {
    public String testName;
    public long totalTestExecutionTime;
    public List<Result> threadResults;

    public AllTestResults() {
    }

    public AllTestResults(String testName, long totalTestExecutionTime, List<Result> threadResults) {
        this.testName = testName;
        this.totalTestExecutionTime = totalTestExecutionTime;
        this.threadResults = threadResults;
    }
}
