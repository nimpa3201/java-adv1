package thread.excutor.reject;

import thread.excutor.RunnableTasks;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static util.MyLogger.log;

public class RejectMainV2 {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
            new SynchronousQueue<>(), new ThreadPoolExecutor.DiscardPolicy()); //조용히 버리는 정책

        executor.submit(new RunnableTasks("task1"));
        executor.submit(new RunnableTasks("task2"));
        executor.submit(new RunnableTasks("task3"));

        executor.close();

    }
    /**
     *  16:39:27:007 [pool-1-thread-1] task1 시작
     *  16:39:28:027 [pool-1-thread-1] task1 완료
     */
}
