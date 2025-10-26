package thread.excutor.future;

import thread.excutor.CallableTask;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import static util.MyLogger.log;

public class InvokeAnyMain {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newFixedThreadPool(10);

        CallableTask task1 = new CallableTask("task1", 1000);
        CallableTask task2 = new CallableTask("task2", 2000);
        CallableTask task3 = new CallableTask("task3", 3000);

        List<CallableTask> taskes = List.of(task1, task2, task3);

        Integer value = es.invokeAny(taskes);
        log("value = " + value);

        es.close();

    }

    /**
     *  21:59:52:125 [pool-1-thread-3] task3실행
     *  21:59:52:125 [pool-1-thread-2] task2실행
     *  21:59:52:125 [pool-1-thread-1] task1실행
     *  21:59:53:137 [pool-1-thread-1] task1완료
     *  21:59:53:139 [     main] value = 1000
     *  21:59:53:139 [pool-1-thread-2] 인터럽트 발생, sleep interrupted
     *  21:59:53:139 [pool-1-thread-3] 인터럽트 발생, sleep interrupted
     */
}
