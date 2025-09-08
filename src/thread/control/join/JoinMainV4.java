package thread.control.join;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class JoinMainV4 {
    public static void main(String[] args) throws InterruptedException {
        log("start");

        SumTask task1 = new SumTask(1, 50);

        Thread thread1 = new Thread(task1, "thread-1");

        thread1.start();


        // 스레드가 종료될 때 까지 대기
        log("join(1000) - main 스레드가 thread1 종료까지 1초 대기");
        thread1.join(1000);

        log("main 스레드 대기 완료");



        log("task1.result =" + task1.result);





        log("end");
    }

    static class SumTask implements Runnable {
        int startValue;
        int endValue;
        int result = 0;

        public SumTask(int startValue, int endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        public void run() {
            log("작업 시작");
            sleep(2000);

            int sum = 0;
            for (int i = startValue; i <= endValue; i++) {
                sum += i;

            }
            result = sum;
            log("작업 완료 result =" + result);


        }
    }

    /**
     11:25:06:373 [     main] start
     11:25:06:382 [     main] join(1000) - main 스레드가 thread1 종료까지 1초 대기
     11:25:06:382 [ thread-1] 작업 시작
     11:25:07:383 [     main] main 스레드 대기 완료
     11:25:07:392 [     main] task1.result =0
     11:25:07:392 [     main] end
     11:25:08:385 [ thread-1] 작업 완료 result =1275
     */
}
