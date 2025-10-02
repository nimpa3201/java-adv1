package thread.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class CasMainV3 {

    private static final int THREAD_COUNT =2;

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        System.out.println("start value =" + atomicInteger.get());

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                incrementAndGet(atomicInteger);

            }
        };

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(runnable);
            threads.add(thread);
            thread.start();

        }
        for (Thread thread : threads) {
            thread.join();
        }

        int result = atomicInteger.get();
        System.out.println(atomicInteger.getClass().getSimpleName() + " resultValue:" + result);

    }
    private static int incrementAndGet(AtomicInteger atomicInteger) {
        int getValue;
        boolean result;

        do{
            getValue = atomicInteger.get();
            sleep(100); // 스레드 동시 실행을 위한 대
            log("getValue: " + getValue);
            result = atomicInteger.compareAndSet(getValue, getValue + 1);
            log("result: " + result);


        }while(!result);
        return getValue+1;

    }
    /**
     * start value =0
     *  20:00:21:814 [ Thread-1] getValue: 0
     *  20:00:21:815 [ Thread-0] getValue: 0
     *  20:00:21:824 [ Thread-0] result: false <- 실패 상황
     *  20:00:21:824 [ Thread-1] result: true
     *  20:00:21:824 [ Thread-0] getValue: 1
     *  20:00:21:825 [ Thread-0] result: true
     * AtomicInteger resultValue:2
     */
}
