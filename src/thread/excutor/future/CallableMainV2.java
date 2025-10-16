package thread.excutor.future;

import java.util.Random;
import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class CallableMainV2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService es = Executors.newFixedThreadPool(1);
        log("submit() 호출");
        Future<Integer> future = es.submit(new MyCallable());
        log("future 즉시 반환, future = " + future);


        log("future.get() [블로킹] 메서드 호출 시작 -> main 스레드 WAITING");
        Integer result = future.get();
        log("future.get() [블로킹] 메서드 호출 완료 -> main 스레드 RUNNABLE");
        log("result value = " + result);
        log("future 완료, future = " +future);
        es.close();
    }

    static class MyCallable implements Callable<Integer>{

        @Override
        public Integer call()  { // 반환 타입이 있음
            log("Callable 시작");
            sleep(2000);
            int value = new Random().nextInt(10);
            log("create value = " + value);
            log("Callable 완료");
            return value;
        }
    }
    /**
     * 00:16:52:008 [     main] submit() 호출
     *  00:16:52:022 [pool-1-thread-1] Callable 시작
     *  00:16:52:024 [     main] future 즉시 반환, future = java.util.concurrent.FutureTask@28d25987[Not completed, task = thread.excutor.future.CallableMainV2$MyCallable@59fa1d9b]
     *  00:16:52:027 [     main] future.get() [블로킹] 메서드 호출 시작 -> main 스레드 WAITING
     *  00:16:54:037 [pool-1-thread-1] create value = 2
     *  00:16:54:037 [pool-1-thread-1] Callable 완료
     *  00:16:54:039 [     main] future.get() [블로킹] 메서드 호출 완료 -> main 스레드 RUNNABLE
     *  00:16:54:040 [     main] result value =2
     *  00:16:54:041 [     main] future 완료, future = java.util.concurrent.FutureTask@28d25987[Completed normally]
     */
}
