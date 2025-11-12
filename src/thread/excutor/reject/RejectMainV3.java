package thread.excutor.reject;

import thread.excutor.RunnableTasks;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RejectMainV3 {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
            new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

        executor.submit(new RunnableTasks("task1"));
        executor.submit(new RunnableTasks("task2"));
        executor.submit(new RunnableTasks("task3"));
        executor.submit(new RunnableTasks("task4"));

        executor.close();

    }
    /**
     *  16:41:46:251 [     main] task2 시작
     *  16:41:46:252 [pool-1-thread-1] task1 시작
     *  16:41:47:261 [pool-1-thread-1] task1 완료
     *  16:41:47:261 [     main] task2 완료
     *  16:41:47:263 [     main] task3 시작
     *  16:41:48:268 [     main] task3 완료
     *  16:41:48:270 [pool-1-thread-1] task4 시작
     *  16:41:49:274 [pool-1-thread-1] task4 완료
     *
     *  작업을 거절하는 대신에, 작업을 용청한 스레드에 대신 일을 시킨다.
     */
}
