package thread.control.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ThreadStopMainV3 {

    public static void main(String[] args) {

        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");
        thread.start();

        sleep(100);
        log("작업 중단 지시 thread.interrupt()");
        thread.interrupt();
        log("work 스레드 인터럽트 상태1 =" + thread.isInterrupted());


    }

    static class MyTask implements Runnable {


        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) { // 인터럽트 상태 변경 x

                log("작업 중");

            }
            log("work 스레드 인터럽트 상태2 = " + Thread.currentThread().isInterrupted());
            log("자원 정리");
            log("자원 종료");

        }

    }


}


/**
 *  09:15:08:303 [     work] 작업 중
 *  09:15:08:309 [     work] 작업 중
 *  09:15:08:310 [     work] 작업 중
 *  09:15:08:313 [     work] 작업 중
 *  09:15:08:313 [     work] 작업 중
 *  09:15:08:314 [     work] 작업 중
 *  09:15:08:315 [     work] 작업 중
 *  09:15:08:315 [     work] 작업 중
 *  09:15:08:315 [     work] 작업 중
 *  09:15:08:316 [     work] 작업 중
 *  09:15:08:317 [     work] 작업 중
 *  09:15:08:318 [     work] 작업 중
 *  09:15:08:319 [     work] 작업 중
 *  09:15:08:320 [     work] 작업 중
 *  09:15:08:320 [     work] 작업 중
 *  09:15:08:320 [     work] 작업 중
 *  09:15:08:321 [     work] 작업 중
 *  09:15:08:321 [     work] 작업 중
 *  09:15:08:322 [     work] 작업 중
 *  09:15:08:322 [     work] 작업 중
 *  09:15:08:322 [     work] 작업 중
 *  09:15:08:326 [     work] 작업 중
 *  09:15:08:327 [     work] 작업 중
 *  09:15:08:327 [     work] 작업 중
 *  09:15:08:328 [     work] 작업 중
 *  09:15:08:329 [     work] 작업 중
 *  09:15:08:330 [     work] 작업 중
 *  09:15:08:330 [     work] 작업 중
 *  09:15:08:330 [     work] 작업 중
 *  09:15:08:331 [     work] 작업 중
 *  09:15:08:331 [     work] 작업 중
 *  09:15:08:331 [     work] 작업 중
 *  09:15:08:332 [     work] 작업 중
 *  09:15:08:332 [     work] 작업 중
 *  09:15:08:333 [     work] 작업 중
 *  09:15:08:333 [     work] 작업 중
 *  09:15:08:333 [     work] 작업 중
 *  09:15:08:334 [     work] 작업 중
 *  09:15:08:335 [     work] 작업 중
 *  09:15:08:335 [     work] 작업 중
 *  09:15:08:336 [     work] 작업 중
 *  09:15:08:337 [     work] 작업 중
 *  09:15:08:337 [     work] 작업 중
 *  09:15:08:339 [     work] 작업 중
 *  09:15:08:340 [     work] 작업 중
 *  09:15:08:340 [     work] 작업 중
 *  09:15:08:340 [     work] 작업 중
 *  09:15:08:341 [     work] 작업 중
 *  09:15:08:341 [     work] 작업 중
 *  09:15:08:341 [     work] 작업 중
 *  09:15:08:342 [     work] 작업 중
 *  09:15:08:342 [     work] 작업 중
 *  09:15:08:343 [     work] 작업 중
 *  09:15:08:343 [     work] 작업 중
 *  09:15:08:343 [     main] 작업 중단 지시 thread.interrupt()
 *  09:15:08:345 [     work] 작업 중
 *  09:15:08:357 [     work] work 스레드 인터럽트 상태2 = true
 *  09:15:08:357 [     work] 자원 정리
 *  09:15:08:358 [     work] 자원 종료
 *  09:15:08:358 [     main] work 스레드 인터럽트 상태1 =true
 */

