package thread.control.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ThreadStopMainV1 {

    public static void main(String[] args) {

        MyTask task = new MyTask();
        Thread thread = new Thread(task,"work");
        thread.start();

        sleep(4000);
        log("작업 중단 지시 runFlag = false");
        task.runFlag = false;



    }

    static class MyTask implements Runnable{

        volatile boolean runFlag = true;

        @Override
        public void run() {
            while (runFlag){
                log("작업 중");
                sleep(3000);
            }
            log("자원 정리");
            log("자원 종료");

        }
    }

    /**
     *  03:43:21:614 [     work] 작업 중
     *  03:43:24:628 [     work] 작업 중
     *  03:43:25:552 [     main] 작업 중단 지시 runFlag = false
     *  03:43:27:630 [     work] 자원 정리
     *  03:43:27:632 [     work] 자원 종료
     */
}
