package thread.excutor.poolsize;

import thread.excutor.RunnableTasks;

import java.util.concurrent.*;

import static thread.excutor.ExecutorUtils.printState;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class PoolSizeMainV3 {

    public static void main(String[] args) {
        // ExecutorService es = Executors.newCachedThreadPool();
        ExecutorService es = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            3, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());


        log("pool 생성");

        printState(es);

        for (int i = 1; i <= 4; i++) {
            String taskName = "task" + i;
            es.execute(new RunnableTasks(taskName));
            printState(es, taskName);
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
 *  21:05:08:862 [     main] pool 생성
 *  21:05:08:924 [     main] [pool=0 , active=0 queuedTasks=0,completedTask=0]
 *  21:05:08:953 [pool-1-thread-1] task1 시작
 *  21:05:09:015 [     main] task1 -> [pool=1 , active=1 queuedTasks=0,completedTask=0]
 *  21:05:09:019 [pool-1-thread-2] task2 시작
 *  21:05:09:019 [     main] task2 -> [pool=2 , active=2 queuedTasks=0,completedTask=0]
 *  21:05:09:020 [     main] task3 -> [pool=3 , active=3 queuedTasks=0,completedTask=0]
 *  21:05:09:020 [pool-1-thread-3] task3 시작
 *  21:05:09:022 [     main] task4 -> [pool=4 , active=4 queuedTasks=0,completedTask=0]
 *  21:05:09:022 [pool-1-thread-4] task4 시작
 *  21:05:09:962 [pool-1-thread-1] task1 완료
 *  21:05:10:033 [pool-1-thread-2] task2 완료
 *  21:05:10:033 [pool-1-thread-3] task3 완료
 *  21:05:10:033 [pool-1-thread-4] task4 완료
 *  21:05:12:030 [     main] == 작업 수행 완료 ==
 *  21:05:12:031 [     main] [pool=4 , active=0 queuedTasks=0,completedTask=4]
 *  21:05:15:034 [     main] == maximumPoolSize 대기 시간 초과 ==
 *  21:05:15:035 [     main] [pool=0 , active=0 queuedTasks=0,completedTask=4]
 *  21:05:15:036 [     main] == shutdown 완료 ==
 *  21:05:15:036 [     main] [pool=0 , active=0 queuedTasks=0,completedTask=4]
 */


}

/**
 * 고정 스레드 풀 전략은 서버 자원은 여유가 있는데, 사용자만 점점 느려지는 문제가 발생할 수 있다. 반면에 캐시 스레드
 * 풀 전략은 서버의 자원을 최대한 사용하지만, 서버가 감당할 수 있는 임계점을 넘는 순간 시스템이 다운될 수 있다.
 */
