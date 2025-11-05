package thread.excutor.poolsize;

import thread.excutor.RunnableTasks;

import java.util.concurrent.*;

import static thread.excutor.ExecutorUtils.printState;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class PoolSizeMainV1 {
    public static void main(String[] args) {
        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
        ExecutorService es = new ThreadPoolExecutor(2, 4,
            3000, TimeUnit.MICROSECONDS, workQueue);
        printState(es);

        es.execute(new RunnableTasks("task1"));
        printState(es,"task1");

        es.execute(new RunnableTasks("task2"));
        printState(es,"task2");

        es.execute(new RunnableTasks("task3"));
        printState(es,"task3");

        es.execute(new RunnableTasks("task4"));
        printState(es,"task4");

        es.execute(new RunnableTasks("task5"));
        printState(es,"task5");

        es.execute(new RunnableTasks("task6"));
        printState(es,"task6");

        try {
            es.execute(new RunnableTasks("task7"));
        }catch (RejectedExecutionException e){
            log("task7 실행 거절 예외 발생: " + e);
        }

        sleep(3000);
        log("== 작업 수행 완료 ==");
        printState(es);

        sleep(3000);
        log("== maximumPoolSize 대기 시간 초과 ==");
        printState(es);

        es.close();
        log("== shutdown 완료 ==");
        printState(es);


    }
    /**
     * 19:41:07:523 [     main] [pool=0 , active=0 queuedTasks=0,completedTask=0]
     *  19:41:07:541 [pool-1-thread-1] task1 시작
     *  19:41:07:576 [     main] task1 -> [pool=1 , active=1 queuedTasks=0,completedTask=0]
     *  19:41:07:578 [     main] task2 -> [pool=2 , active=2 queuedTasks=0,completedTask=0]
     *  19:41:07:578 [pool-1-thread-2] task2 시작
     *  19:41:07:578 [     main] task3 -> [pool=2 , active=2 queuedTasks=1,completedTask=0]
     *  19:41:07:579 [     main] task4 -> [pool=2 , active=2 queuedTasks=2,completedTask=0]
     *  19:41:07:580 [     main] task5 -> [pool=3 , active=3 queuedTasks=2,completedTask=0]
     *  19:41:07:580 [pool-1-thread-3] task5 시작
     *  19:41:07:582 [pool-1-thread-4] task6 시작
     *  19:41:07:592 [     main] task6 -> [pool=4 , active=4 queuedTasks=2,completedTask=0]
     *  19:41:07:593 [     main] task7 실행 거절 예외 발생: java.util.concurrent.RejectedExecutionException: Task thread.excutor.RunnableTasks@3c0ecd4b rejected from java.util.concurrent.ThreadPoolExecutor@6108b2d7[Running, pool size = 4, active threads = 4, queued tasks = 2, completed tasks = 0]
     *  19:41:08:546 [pool-1-thread-1] task1 완료
     *  19:41:08:547 [pool-1-thread-1] task3 시작
     *  19:41:08:583 [pool-1-thread-2] task2 완료
     *  19:41:08:584 [pool-1-thread-2] task4 시작
     *  19:41:08:585 [pool-1-thread-3] task5 완료
     *  19:41:08:588 [pool-1-thread-4] task6 완료
     *  19:41:09:550 [pool-1-thread-1] task3 완료
     *  19:41:09:588 [pool-1-thread-2] task4 완료
     *  19:41:10:597 [     main] == 작업 수행 완료 ==
     *  19:41:10:599 [     main] [pool=2 , active=0 queuedTasks=0,completedTask=6]
     *  19:41:13:604 [     main] == maximumPoolSize 대기 시간 초과 ==
     *  19:41:13:606 [     main] [pool=2 , active=0 queuedTasks=0,completedTask=6]
     *  19:41:13:609 [     main] == shutdown 완료 ==
     *  19:41:13:610 [     main] [pool=0 , active=0 queuedTasks=0,completedTask=6]
     */
}
