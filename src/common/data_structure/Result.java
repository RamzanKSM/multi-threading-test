package common.data_structure;

import java.util.concurrent.atomic.AtomicLong;

public class Result {
    public String threadName;
    public long invokeTime;
    public long atomicLongValue;
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
    public void setOrAddAtomicLongValue(AtomicLong atomicLong) {
        if (atomicLongValue == 0) {
            this.atomicLongValue = atomicLong.get();
        } else {
            this.atomicLongValue += atomicLong.get();
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
