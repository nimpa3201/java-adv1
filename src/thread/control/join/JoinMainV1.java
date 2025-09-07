package thread.control.join;

import com.sun.jdi.event.ThreadDeathEvent;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class JoinMainV1 {
    public static void main(String[] args) {
        log("start");

        SumTask task1 = new SumTask(1, 50);
        SumTask task2 = new SumTask(51, 100);
        Thread thread1 = new Thread(task1, "thread-1");
        Thread thread2 = new Thread(task2, "thread-2");

        thread1.start();
        thread2.start();

        log("task1.result =" + task1.result);
        log("task2.result =" + task2.result);

        int sumAll = task1.result + task2.result;

        log("task1+ task2 = " + sumAll);


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
     *  04:16:49:190 [     main] start
     *  04:16:49:199 [ thread-1] 작업 시작
     *  04:16:49:200 [ thread-2] 작업 시작
     *  04:16:49:210 [     main] task1.result =0
     *  04:16:49:212 [     main] task2.result =0
     *  04:16:49:213 [     main] task1+ task2 = 0
     *  04:16:49:214 [     main] end
     *  04:16:51:207 [ thread-1] 작업 완료 result =1275
     *  04:16:51:207 [ thread-2] 작업 완료 result =3775
     */
}
