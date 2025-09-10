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

        boolean runFlag = true;
        //volatile boolean runFlag = true;

        @Override
        public void run() {
            log("task 시작");
            while (runFlag) {
                // runFlag가 false로 변하면 탈출
            }
            log("task 종료");

        }

        /**
         *  13:50:45:024 [     main] runFlag = true
         *  13:50:45:043 [     work] task 시작
         *  13:50:46:047 [     main] runFlag를 false로 변경 시도
         *  13:50:46:048 [     main] runFlag = false
         */
        /* work 스레드 종료 안됨 */
    }
}

    //캐시 메모리의 runFlag 값만 변한다는 것이다! 메인 메모리에 이 값이 즉시 반영되지 않는다.

