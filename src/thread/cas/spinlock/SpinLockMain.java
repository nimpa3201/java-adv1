package thread.cas.spinlock;

import static util.MyLogger.log;

public class SpinLockMain {
    public static void main(String[] args) {
        SpinLockBad spinLock = new SpinLockBad();

        Runnable task = new Runnable() {

            @Override
            public void run() {
                spinLock.lock();
                try {
                    // 임계 영역
                    log("비지니스 로직 실행");
                } finally {
                    spinLock.unlock();
                }
            }
        };
        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        t1.start();
        t2.start();


    }

    /** Thread1,2 모두 동시에 락 획득
     *  06:07:28:851 [ Thread-2] 락 획득 시도
     *  06:07:28:851 [ Thread-1] 락 획득 시도
     *  06:07:28:963 [ Thread-2] 락 획득 완료
     *  06:07:28:963 [ Thread-1] 락 획득 완료
     *  06:07:28:964 [ Thread-1] 비지니스 로직 실행
     *  06:07:28:964 [ Thread-2] 비지니스 로직 실행
     *  06:07:28:964 [ Thread-2] 락 반납 완료
     *  06:07:28:964 [ Thread-1] 락 반납 완료
     */
}
