package thread.excutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static thread.excutor.ExecutorUtils.*;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ExecutorBasicMain {

    public static void main(String[] args) {
        ExecutorService es = new ThreadPoolExecutor(2, 2, 0, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<>());
        log("== 초기 상태 ==");
        printState(es);
        es.execute(new RunnableTasks("taskA"));
        es.execute(new RunnableTasks("taskB"));
        es.execute(new RunnableTasks("taskC"));
        es.execute(new RunnableTasks("taskD"));
        log("== 작업 수행 중 ==");
        printState(es);

        sleep(3000);
        log("== 작업 수행 완료 ==");
        printState(es);

        es.close();
        log("== showdown 완료 ==");
        printState(es);


    }

    /**
     *  20:44:04:378 [     main] == 초기 상태 ==
     *  20:44:04:428 [     main] [pool=0 , active=0 queuedTasks=0,completedTask=0]
     *  20:44:04:432 [pool-1-thread-1] taskA 시작
     *  20:44:04:438 [     main] == 작업 수행 중 ==
     *  20:44:04:438 [pool-1-thread-2] taskB 시작
     *  20:44:04:442 [     main] [pool=2 , active=2 queuedTasks=2,completedTask=0] 스레드 2개 만들고 나머지 두 task는 만든 스레드 재사용
     *  20:44:05:442 [pool-1-thread-1] taskA 완료
     *  20:44:05:442 [pool-1-thread-1] taskC 시작
     *  20:44:05:445 [pool-1-thread-2] taskB 완료
     *  20:44:05:446 [pool-1-thread-2] taskD 시작
     *  20:44:06:447 [pool-1-thread-2] taskD 완료
     *  20:44:06:447 [pool-1-thread-1] taskC 완료
     */


    /**
     *  20:47:37:030 [     main] == 초기 상태 ==
     *  20:47:37:073 [     main] [pool=0 , active=0 queuedTasks=0,completedTask=0]
     *  20:47:37:076 [pool-1-thread-1] taskA 시작
     *  20:47:37:076 [pool-1-thread-2] taskB 시작
     *  20:47:37:078 [     main] == 작업 수행 중 ==
     *  20:47:37:079 [     main] [pool=2 , active=2 queuedTasks=2,completedTask=0]
     *  20:47:38:083 [pool-1-thread-1] taskA 완료
     *  20:47:38:084 [pool-1-thread-1] taskC 시작
     *  20:47:38:083 [pool-1-thread-2] taskB 완료
     *  20:47:38:086 [pool-1-thread-2] taskD 시작
     *  20:47:39:088 [pool-1-thread-1] taskC 완료
     *  20:47:39:089 [pool-1-thread-2] taskD 완료
     *  20:47:40:085 [     main] == 작업 수행 완료 ==
     *  20:47:40:086 [     main] [pool=2 , active=0 queuedTasks=0,completedTask=4]
     */


    /**
     *
     *  20:49:25:214 [     main] == 초기 상태 ==
     *  20:49:25:287 [     main] [pool=0 , active=0 queuedTasks=0,completedTask=0]
     *  20:49:25:291 [pool-1-thread-2] taskB 시작
     *  20:49:25:291 [pool-1-thread-1] taskA 시작
     *  20:49:25:293 [     main] == 작업 수행 중 ==
     *  20:49:25:295 [     main] [pool=2 , active=2 queuedTasks=2,completedTask=0]
     *  20:49:26:296 [pool-1-thread-1] taskA 완료
     *  20:49:26:297 [pool-1-thread-2] taskB 완료
     *  20:49:26:298 [pool-1-thread-1] taskC 시작
     *  20:49:26:298 [pool-1-thread-2] taskD 시작
     *  20:49:27:299 [pool-1-thread-1] taskC 완료
     *  20:49:27:299 [pool-1-thread-2] taskD 완료
     *  20:49:28:296 [     main] == 작업 수행 완료 ==
     *  20:49:28:297 [     main] [pool=2 , active=0 queuedTasks=0,completedTask=4]
     *  20:49:28:300 [     main] == shutdown 완료 ==
     *  20:49:28:301 [     main] [pool=0 , active=0 queuedTasks=0,completedTask=4]
     */
}
