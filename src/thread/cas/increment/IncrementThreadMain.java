package thread.cas.increment;

import java.util.ArrayList;

import static util.ThreadUtils.sleep;

public class IncrementThreadMain {
    public static final int THREAD_COUNT =1000;

    public static void main(String[] args) throws InterruptedException {
        test(new BasicInteger());
        test(new VolatileInteger());
        test(new SyncInteger());

    }

    private static void test(IncrementInteger incrementInteger) throws InterruptedException {

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                sleep(10);   // 너무 빨리 실행되기 때문에 , 다른 스레드와 동시 실행을 위해 잠깐 쉬었다가 실행
                incrementInteger.increment();
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

        int result = incrementInteger.get();
        System.out.println(incrementInteger.getClass().getSimpleName() + ": " + result);
        //BasicInteger: 991 1000이 아니라 다른 숫자가 보인다. 스레드가 동시에 원자적이지 않은 value++를 호출했기 때문
        /**
         * BasicInteger: 993
         * VolatileInteger: 996
         * SyncInteger: 1000
         */
    }

}
