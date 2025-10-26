package thread.excutor.future;

import thread.excutor.CallableTask;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static util.MyLogger.log;

public class InvokeAllMain {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newFixedThreadPool(10);

        CallableTask task1 = new CallableTask("task1", 1000);
        CallableTask task2 = new CallableTask("task2", 2000);
        CallableTask task3 = new CallableTask("task3", 3000);

        List<CallableTask> taskes = List.of(task1, task2, task3);

        List<Future<Integer>> futures = es.invokeAll(taskes);
        for (Future<Integer> future : futures) {
            Integer value = future.get();
            log("value = " + value);
        }

        es.close();

    }

    /**
     * 21:57:38:877 [pool-1-thread-1] task1실행
     *  21:57:38:879 [pool-1-thread-3] task3실행
     *  21:57:38:879 [pool-1-thread-2] task2실행
     *  21:57:39:890 [pool-1-thread-1] task1완료
     *  21:57:40:889 [pool-1-thread-2] task2완료
     *  21:57:41:888 [pool-1-thread-3] task3완료
     *  21:57:41:890 [     main] value = 1000
     *  21:57:41:890 [     main] value = 2000
     *  21:57:41:891 [     main] value = 3000
     */
}
