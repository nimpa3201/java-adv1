package thread.start;

import static util.MyLogger.log;

public class ManyThreadMainV1 {

    public static void main(String[] args) {
        log("min() start");

        HelloRunnable runnable = new HelloRunnable();
        Thread thread1 = new Thread(runnable);
        thread1.start();
        Thread thread2 = new Thread(runnable);
        thread2.start();
        Thread thread3 = new Thread(runnable);
        thread3.start();

        log("main() end");


        /*
        18:24:23:469 [     main] min() start
        Thread-0: run()
        Thread-1: run()
        18:24:23:479 [     main] main() end
        Thread-2: run()
         */

    }
}
