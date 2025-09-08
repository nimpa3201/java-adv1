package thread.control.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ThreadStopMainV2 {

    public static void main(String[] args) {

        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");
        thread.start();

        sleep(4000);
        log("작업 중단 지시 thread.interrupt()");
        thread.interrupt();
        log("work 스레드 인터럽트 상태1 =" + thread.isInterrupted());


    }

    static class MyTask implements Runnable {


        @Override
        public void run() {
            while (true) {
                try {
                    log("작업 중");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    log("work 스레드 인터럽트 상태2 = " + Thread.currentThread().isInterrupted());
                    log("interrupt message =" + e.getMessage());
                    log("state =" + Thread.currentThread().getState());
                }
                log("자원 정리");
                log("자원 종료");
            }


        }
    }

    /**
     04:02:58:010 [     work] 작업 중
     04:03:01:018 [     work] 자원 정리
     04:03:01:020 [     work] 자원 종료
     04:03:01:021 [     work] 작업 중
     04:03:01:957 [     main] 작업 중단 지시 thread.interrupt()
     04:03:01:972 [     main] work 스레드 인터럽트 상태1 =true
     04:03:01:972 [     work] work 스레드 인터럽트 상태2 = false
     04:03:01:975 [     work] interrupt message =sleep interrupted
     04:03:01:976 [     work] state =RUNNABLE
     04:03:01:976 [     work] 자원 정리
     04:03:01:977 [     work] 자원 종료
     */
}
