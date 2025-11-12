package thread.excutor.reject;

import thread.excutor.RunnableTasks;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static util.MyLogger.log;

public class RejectMainV4 {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
            new SynchronousQueue<>(), new MyRejectedExecutionHandler());

        executor.submit(new RunnableTasks("task1"));
        executor.submit(new RunnableTasks("task2"));
        executor.submit(new RunnableTasks("task3"));

        executor.close();

    }

    static class MyRejectedExecutionHandler implements RejectedExecutionHandler{

        static AtomicInteger count = new AtomicInteger(0);

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            int i = count.incrementAndGet();
            log("[경고] 거절된 누적 작업 수: " + i);

        }
    }
    /**
     *  16:52:02:202 [pool-1-thread-1] task1 시작
     *  16:52:02:206 [     main] [경고] 거절된 누적 작업 수: 1
     *  16:52:02:218 [     main] [경고] 거절된 누적 작업 수: 2
     *  16:52:03:222 [pool-1-thread-1] task1 완료
     */
}
