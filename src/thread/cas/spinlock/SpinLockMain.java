package thread.cas.spinlock;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class SpinLockMain {
    public static void main(String[] args) {
        //SpinLockBad spinLock = new SpinLockBad();
        SpinLock spinLock = new SpinLock();


        Runnable task = new Runnable() {

            @Override
            public void run() {
                spinLock.lock();
                try {
                    // 임계 영역
                    log("비지니스 로직 실행");
                    //sleep(1); // 오래 걸리는 로직에서 스핀 락 사용 x
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

    /**
     * 06:36:03:934 [ Thread-1] 락 획득 시도
     *  06:36:03:934 [ Thread-2] 락 획득 시도
     *  06:36:03:942 [ Thread-1] 락 획득 완료
     *  06:36:03:942 [ Thread-2] 락 획득 실패 - 스핀 대기
     *  06:36:03:943 [ Thread-1] 비지니스 로직 실행
     *  06:36:03:943 [ Thread-2] 락 획득 실패 - 스핀 대기
     *  06:36:03:943 [ Thread-1] 락 반납 완료
     *  06:36:03:943 [ Thread-2] 락 획득 완료
     *  06:36:03:944 [ Thread-2] 비지니스 로직 실행
     *  06:36:03:944 [ Thread-2] 락 반납 완료
     */
}

/**
 * 그럼 어떤 경우에 이런 방식이 효율적일까?
 * 안전한 임계 영역이 필요하지만, 연산이 길지 않고 매우매우매우! 짧게 끝날 때 사용해야 한다.
 * 예를 들어 숫자 값의 증가, 자료 구조의 데이터 추가와 같이 CPU 사이클이 금방 끝나는 연산에 사용하면 효과적이다.
 * 반면에 데이터베이스의 결과를 대기한다거나, 다른 서버의 요청을 기다린다거나 하는 것 처럼 오래 기다리는 작업에 사
 * 용하면 CPU를 계속 사용하며 기다리는 최악의 결과가 나올 수도 있다.
 */
