package common.data_structure;

public record Result(
        String threadName,
        long invokeTime,
        int atomicIntegerValue,
        double doubleValue
) {
    public Result(String threadName, long invokeTime, int atomicIntegerValue) {
        this(threadName, invokeTime, atomicIntegerValue, 0);
    }
}
