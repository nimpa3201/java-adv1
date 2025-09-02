package thread.start;

import static util.MyLogger.log;

public class ManyThreadMainV2 {

    public static void main(String[] args) {
        log("min() start");

        HelloRunnable runnable = new HelloRunnable();

        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(runnable);
            thread.start();

        }


        log("main() end");

    }
    // 스레드의 순서는 보장되지 않음
}
