package thread.cas;

import java.util.concurrent.atomic.AtomicInteger;

import static util.MyLogger.log;

public class CasMainV2 {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        System.out.println("start value = " + atomicInteger.get());

        // incrementAndGet 구현
        int resultValue1 = incrementAndGet(atomicInteger);
        System.out.println("resultValue1 = " + resultValue1);

        int resultValue2 = incrementAndGet(atomicInteger);
        System.out.println("resultValue2 = " + resultValue2);

    }

    private static int incrementAndGet(AtomicInteger atomicInteger) {
        int getValue;
        boolean result;

        do{
            getValue = atomicInteger.get();
            log("getValue: " + getValue);
            result = atomicInteger.compareAndSet(getValue, getValue + 1);
            log("result: " + result);


        }while(!result);
        return getValue+1;

    }
    /** 성공 케이스(메인 스레드 하나로 순서대로 실행하기때문)
     * start value = 0
     *  19:28:08:081 [     main] getValue: 0
     *  19:28:08:089 [     main] result: true
     * resultValue1 = 1
     *  19:28:08:090 [     main] getValue: 1
     *  19:28:08:090 [     main] result: true
     * resultValue2 = 2
     */
}
