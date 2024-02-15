package common.data_structure;

import java.util.concurrent.atomic.AtomicInteger;

public class Result {
    public String threadName;
    public long invokeTime;
    public int atomicIntegerValue;
    public double doubleValue;

    public Result(String threadName) {
        this.threadName = threadName;
    }

    public void setOrAddInvokeTime(long invokeTime) {
        if (this.invokeTime == 0) {
            this.invokeTime = invokeTime;
        } else {
            this.invokeTime += invokeTime;
        }
    }
    public void setOrAddAtomicIntegerValue(AtomicInteger atomicInteger) {
        if (atomicIntegerValue == 0) {
            this.atomicIntegerValue = atomicInteger.get();
        } else {
            this.atomicIntegerValue += atomicInteger.get();
        }
    }
    public void setOrAddDoubleValue(double doubleValue) {
        if (this.doubleValue == 0.0D) {
            this.doubleValue = doubleValue;
        } else {
            this.doubleValue = Double.sum(this.doubleValue, doubleValue);
        }
    }
}
