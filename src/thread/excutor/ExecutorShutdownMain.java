package thread.excutor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static thread.excutor.ExecutorUtils.printState;
import static util.MyLogger.log;

public class ExecutorShutdownMain {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(2);
        es.execute(new RunnableTasks("taskA"));
        es.execute(new RunnableTasks("taskB"));
        es.execute(new RunnableTasks("taskC"));
        es.execute(new RunnableTasks("longTask",100_000)); // 100초 대기
        printState(es);
        log("== shutdown 시작");
        shutdownAndAwaitTermination(es);
        log("== shutdown 완료");
        printState(es);


    }

    private static void shutdownAndAwaitTermination(ExecutorService es) {
        es.shutdown(); // non-blocking, 새로운 작업을 받지 않는다. 처리 중이거나, 큐에 이미 대기중인 작업은 처리한다. 이후에 풀의 스레드를 종료한다.
        try {
            // 이미 대기중인 작업들을 모두 완료할 때 까지 10초 기다린다.
            if(!es.awaitTermination(10, TimeUnit.SECONDS)){
                // 정상 종료가 너무 오래 걸리면..
                log("서비스 정상 종료 실패 -> 강제 종료 시도");
                es.shutdownNow();
                // 작업이 취소될 때 까지 대기한다.
                if(!es.awaitTermination(10,TimeUnit.SECONDS)){
                    log("서비스가 종료되지 않았습니다.");
                }
            };
        } catch (InterruptedException e) {
            // awaitTermination()으로 대기중인 현재 스레드가 인터럽트 될 수 있다.
            es.shutdownNow();
        }


    }

    /**
     * 18:04:17:348 [pool-1-thread-2] taskB 시작
     *  18:04:17:348 [pool-1-thread-1] taskA 시작
     *  18:04:17:350 [     main] [pool=2 , active=2 queuedTasks=2,completedTask=0]
     *  18:04:17:361 [     main] == shutdown 시작
     *  18:04:18:362 [pool-1-thread-2] taskB 완료
     *  18:04:18:363 [pool-1-thread-2] taskC 시작
     *  18:04:18:363 [pool-1-thread-1] taskA 완료
     *  18:04:18:364 [pool-1-thread-1] longTask 시작
     *  18:04:19:364 [pool-1-thread-2] taskC 완료
     *  18:04:27:366 [     main] 서비스 정상 종료 실패 -> 강제 종료 시도
     *  18:04:27:368 [pool-1-thread-1] 인터럽트 발생, sleep interrupted
     *  18:04:27:369 [     main] == shutdown 완료
     *  18:04:27:370 [     main] [pool=0 , active=0 queuedTasks=0,completedTask=4]
     * Exception in thread "pool-1-thread-1" java.lang.RuntimeException: java.lang.InterruptedException: sleep interrupted
     * 	at util.ThreadUtils.sleep(ThreadUtils.java:11)
     * 	at thread.excutor.RunnableTasks.run(RunnableTasks.java:23)
     * 	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
     * 	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
     * 	at java.base/java.lang.Thread.run(Thread.java:1583)
     * Caused by: java.lang.InterruptedException: sleep interrupted
     * 	at java.base/java.lang.Thread.sleep0(Native Method)
     * 	at java.base/java.lang.Thread.sleep(Thread.java:509)
     * 	at util.ThreadUtils.sleep(ThreadUtils.java:8)
     * 	... 4 more
     */
}
