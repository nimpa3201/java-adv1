package thread.sync.lock;

import java.util.concurrent.locks.LockSupport;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class LockSupportMainV2 {

    public static void main(String[] args) {

        Thread thread1 = new Thread(new ParkTest(), "Thread-1");
        thread1.start();

        // 잠시 대기하여 Thread-1이 park 상태에 빠질 시간을 준다.
        sleep(100);
        log("Thread-1 state: " + thread1.getState());






    }

    static class ParkTest implements Runnable{

        @Override
        public void run() {
            log("park 시작, 2초 대기");
            LockSupport.parkNanos(2000_000000); // 2초 뒤에 깨어남
            log("park 종료, state: " + Thread.currentThread().getState());
            log("인터럽트 상태: "  + Thread.currentThread().isInterrupted());
        }
    }
    /**
     *  20:27:45:578 [ Thread-1] park 시작, 2초 대기
     *  20:27:45:614 [     main] Thread-1 state: TIMED_WAITING
     *  20:27:47:590 [ Thread-1] park 종료, state: RUNNABLE
     *  20:27:47:602 [ Thread-1] 인터럽트 상태: false
     */
}


