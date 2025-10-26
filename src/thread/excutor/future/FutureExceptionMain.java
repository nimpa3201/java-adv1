package thread.excutor.future;

import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class FutureExceptionMain {
    public static void main(String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(1);
        log("작업 전달");
        Future<Integer> future = es.submit(new ExCallable());
        sleep(1000); // 잠시 대기

        try {
            log("future.get() 호출 시도, future.state(): " + future.state());
            Integer result = future.get();
            log("result value =" + result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            log("e = " + e);
            Throwable cause = e.getCause(); // 원본 예외
            log("cause =" + cause);
        }
        es.close();
    }

    static class ExCallable implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            log("Callable 실행, 예외 발생");
            throw new IllegalStateException("ex!");
        }
    }
    /**
     * 21:35:50:632 [     main] 작업 전달
     *  21:35:50:644 [pool-1-thread-1] Callable 실행, 예외 발생
     *  21:35:51:654 [     main] future.get() 호출 시도, future.state(): FAILED
     *  21:35:51:654 [     main] e = java.util.concurrent.ExecutionException: java.lang.IllegalStateException: ex!
     *  21:35:51:655 [     main] cause =java.lang.IllegalStateException: ex!
     */
}
