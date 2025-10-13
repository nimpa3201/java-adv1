package thread.excutor;

import java.util.Random;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class RunnableMain {
    public static void main(String[] args) throws InterruptedException {
        MyRunnable task = new MyRunnable();
        Thread thread = new Thread(task, "Thread-1");
        thread.start();
        thread.join();
        int result = task.value;
        log("result value = " + result);

    }

    static class MyRunnable implements Runnable {

        int value;

        @Override
        public void run() {
            log("Runnable 시작");
            sleep(2000);
            value = new Random().nextInt(10);
            log("create value = " + value);

            log("Runnable 완료");


        }
    }
    /**
     *  21:19:32:643 [ Thread-1] Runnable 시작
     *  21:19:34:683 [ Thread-1] create value = 3
     *  21:19:34:684 [ Thread-1] Runnable 완료
     *  21:19:34:685 [     main] result value = 3
     */
}
/*
프로그램이 시작되면 `Thread-1` 이라는 별도의 스레드를 하나 만든다.
`Thread-1` 이 수행하는 `MyRunnable` 은 무작위 값을 하나 구한 다음에 `value` 필드에 보관한다.
클라이언트인 `main` 스레드가 이 별도의 스레드에서 만든 무작위 값을 얻어오려면 `Thread-1` 스레드가 종료될
때까지 기다려야 한다. 그래서 `main` 스레드는 `join()` 을 호출해서 대기한다.
이후에 `main` 스레드에서 `MyRunnable` 인스턴스의 `value` 필드를 통해 최종 무작위 값을 획득한다.


별도의 스레드에서 만든 무작위 값 하나를 받아오는 과정이 이렇게 복잡하다.
작업 스레드(`Thread-1` )는 값을 어딘가에 보관해두어야 하고, 요청 스레드(`main` )는 작업 스레드의 작업이 끝날 때
까지 `join()` 을 호출해서 대기한 다음에, 어딘가에 보관된 값을 찾아서 꺼내야 한다.
작업 스레드는 간단히 값을 `return` 을 통해 반환하고, 요청 스레드는 그 반환 값을 바로 받을 수 있다면 코드가 훨씬
더 간결해질 것이다.
이런 문제를 해결하기 위해 Executor 프레임워크는 `Callable` 과 `Future` 라는 인터페이스를 도입했다.
 */
