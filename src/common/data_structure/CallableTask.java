package common.data_structure;

import java.util.concurrent.Callable;

public record CallableTask(Callable<Result> task) {}
