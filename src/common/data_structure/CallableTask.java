package common.data_structure;

import java.util.concurrent.Callable;

public class CallableTask {
    private final Callable<Result> task;

    public CallableTask(Callable<Result> task) {
        this.task = task;
    }

    public Callable<Result> getTask() {
        return task;
    }
}
