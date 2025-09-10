package thread.volatile1;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class VolatileFlagMain {
    public static void main(String[] args) {

        MyTask task = new MyTask();
        Thread t = new Thread(task, "work");
        log("runFlag = " + task.runFlag);
        t.start();

        sleep(1000);
        log("runFlag를 false로 변경 시도");
        task.runFlag = false;
        log("runFlag = " + task.runFlag);
        log("main 종료");


    }

    static class MyTask implements Runnable {

        //boolean runFlag = true;
        volatile boolean runFlag = true;

        @Override
        public void run() {
            log("task 시작");
            while (runFlag) {
                // runFlag가 false로 변하면 탈출
            }
            log("task 종료");

        }

        /**
         *  14:29:25:017 [     main] runFlag = true
         *  14:29:25:024 [     work] task 시작
         *  14:29:26:027 [     main] runFlag를 false로 변경 시도
         *  14:29:26:028 [     work] task 종료
         *  14:29:26:029 [     main] runFlag = false
         *  14:29:26:029 [     main] main 종료
         */

    }
}

    //성능을 약간 포기하는 대신에, 값을 읽을 때, 값을 쓸 때 모두 메인 메모리에 직접 접근하면
    //된다.
    //자바에서는 `volatile` 이라는 키워드로 이런 기능을 제공한다.

