package thread.control.yield;

import thread.start.HelloRunnable;

import static util.ThreadUtils.sleep;

public class YieldMain {

    static final int THREAD_COUNT = 1000;
    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(new MyRunnable());
            thread.start();

        }

    }

    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "- " + i);
                //1. empty 틀정 스레드가 연달아 실행되는 것을 볼 수 있음
                //sleep(1); // 2. sleep 1초 동안 Runnable -> TIMED_WAITING으로 변경 1초 대기 후 다시 변경 -> 다른 스레드에게 실행 양보
                                // 스레드 상태가 두번 변경 되는 복잡한 과정을 거치고, 특정 시간만큼 스레드가 실행되지 않는 단점이 있음
                Thread.yield(); // 3. yield RUNNABLE 상태일때 , 실행 상태 실행 대기 상태를 가질 수 있다. 운영체제는 실행 상태의 스레드들을
                                    // 잠깐만 실행 상태로 변경해서 실행한다. 이 과정을 계속 반복한다. 참고로 자바에서는 두 상태를 구분할 수 없다.

            }
        }
    }
}
