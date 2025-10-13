package thread.excutor.future;

import java.util.Random;
import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class CallableMainV1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<Integer> future = es.submit(new MyCallable());
        Integer result = future.get();
        log("result value =" + result);
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
     *  21:55:41:688 [pool-1-thread-1] Callable 시작
     *  21:55:43:717 [pool-1-thread-1] create value = 7
     *  21:55:43:718 [pool-1-thread-1] Callable 완료
     *  21:55:43:720 [     main] result value =7
     */
}
