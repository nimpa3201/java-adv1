package thread.sync.lock;

import java.util.concurrent.locks.LockSupport;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class LockSupportMainV1 {

    public static void main(String[] args) {

        Thread thread1 = new Thread(new ParkTest(), "Thread-1");
        thread1.start();

        // 잠시 대기하여 Thread-1이 park 상태에 빠질 시간을 준다.
        sleep(100);
        log("Thread-1 state: " + thread1.getState());

        //log("main -> unpark(Thread-1)");1. unpark 사용
        //LockSupport.unpark(thread1);
        log("main -> thread1.interrupt()"); //2. interrupt() 사용
        thread1.interrupt();




    }

    static class ParkTest implements Runnable{

        @Override
        public void run() {
            log("park 시작");
            LockSupport.park();
            log("park 종료, state: " + Thread.currentThread().getState());
            log("인터럽트 상태: "  + Thread.currentThread().isInterrupted());
        }
    }

    /**
     *
     *  19:34:24:877 [ Thread-1] park 시작
     *  19:34:24:902 [     main] Thread-1 state: WAITING
     *  19:34:24:902 [     main] main -> unpark(Thread-1)
     *  19:34:24:904 [ Thread-1] park 종료, state: RUNNABLE
     *  19:34:24:917 [ Thread-1] 인터럽트 상태: false
     */

    /**
     *
     *  19:39:28:783 [ Thread-1] park 시작
     *  19:39:28:815 [     main] Thread-1 state: WAITING
     *  19:39:28:815 [     main] main -> thread1.interrupt()
     *  19:39:28:816 [ Thread-1] park 종료, state: RUNNABLE
     *  19:39:28:824 [ Thread-1] 인터럽트 상태: true
     */
}
