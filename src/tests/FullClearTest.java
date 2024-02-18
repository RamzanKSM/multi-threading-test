package tests;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicLong;

import static common.MultiThreadingTestSettings.TARGET;


public class FullClearTest {

    private static final int N = 7;

    public static void main(String[] args) {
        AtomicLong atomicLongS = new AtomicLong();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < TARGET; i++) {
            atomicLongS.incrementAndGet();
        }
        long invokeTime = System.currentTimeMillis() - startTime;
        System.out.println(Thread.currentThread().getName() + " " + invokeTime + "/" + atomicLongS + "/"+ System.identityHashCode(atomicLongS));
    }

//    public static void main(String[] args) {
//        FullClearTest f = new FullClearTest();
//        AtomicLong[] atomicLongs = new AtomicLong[N];
//        Arrays.fill(atomicLongs, new AtomicLong());
//
//        Thread[] threads = new Thread[atomicLongs.length];
//        for (int i = 0; i < atomicLongs.length; i++) {
//            threads[i] = f.getThread(atomicLongs[i]);
//        }
//        for (Thread thread : threads) {
//            thread.start();
//        }
//
//        for (Thread thread : threads) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
//    }
    Callable<Long> getCallable(AtomicLong atomicLong) {
        return () -> {
            AtomicLong atomicLongS = new AtomicLong(atomicLong.get());
            long startTime = System.currentTimeMillis();

            for (int j = 0; j < TARGET / N; j++) {
                atomicLongS.incrementAndGet();
            }

            long invokeTime = System.currentTimeMillis() - startTime;
            System.out.println(Thread.currentThread().getName() + " " + invokeTime + "/" + atomicLongS + "/"+ System.identityHashCode(atomicLongS));
            return invokeTime;
        };
    }

    Thread getThread(AtomicLong atomicLongs) {
            return new Thread(
                    new FutureTask<>(
                            getCallable(atomicLongs)
                    )
            );
    }
}
