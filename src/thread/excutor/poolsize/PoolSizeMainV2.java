package thread.excutor.poolsize;

import thread.excutor.RunnableTasks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static thread.excutor.ExecutorUtils.printState;
import static util.MyLogger.log;

public class PoolSizeMainV2 {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(2);

        log("pool 생성");
        printState(es);

        for (int i = 1; i <=6 ; i++) {
            String taskName = "task" + i;
            es.execute(new RunnableTasks(taskName));
            printState(es,taskName);
        }
    es.close();
        log("== shutdown 완료 ==");
    }

    /**
     * 19:46:30:561 [     main] pool 생성
     *  19:46:30:636 [     main] [pool=0 , active=0 queuedTasks=0,completedTask=0]
     *  19:46:30:647 [pool-1-thread-1] task1 시작
     *  19:46:30:683 [     main] task1 -> [pool=1 , active=1 queuedTasks=0,completedTask=0]
     *  19:46:30:685 [     main] task2 -> [pool=2 , active=2 queuedTasks=0,completedTask=0]
     *  19:46:30:685 [pool-1-thread-2] task2 시작
     *  19:46:30:686 [     main] task3 -> [pool=2 , active=2 queuedTasks=1,completedTask=0]
     *  19:46:30:688 [     main] task4 -> [pool=2 , active=2 queuedTasks=2,completedTask=0]
     *  19:46:30:688 [     main] task5 -> [pool=2 , active=2 queuedTasks=3,completedTask=0]
     *  19:46:30:689 [     main] task6 -> [pool=2 , active=2 queuedTasks=4,completedTask=0]
     *  19:46:31:653 [pool-1-thread-1] task1 완료
     *  19:46:31:654 [pool-1-thread-1] task3 시작
     *  19:46:31:691 [pool-1-thread-2] task2 완료
     *  19:46:31:692 [pool-1-thread-2] task4 시작
     *  19:46:32:658 [pool-1-thread-1] task3 완료
     *  19:46:32:660 [pool-1-thread-1] task5 시작
     *  19:46:32:697 [pool-1-thread-2] task4 완료
     *  19:46:32:698 [pool-1-thread-2] task6 시작
     *  19:46:33:665 [pool-1-thread-1] task5 완료
     *  19:46:33:702 [pool-1-thread-2] task6 완료
     *  19:46:33:704 [     main] == shutdown 완료 ==
     *
     *  2개의 스레드가 안정적으로 작업을 처리하는 것을 확인할 수 있다.
     *
     *  특징
     * 스레드 수가 고정되어 있기 때문에 CPU, 메모리 리소스가 어느정도 예측 가능한 안정적인 방식이다. 큐 사이즈도 제한
     * 이 없어서 작업을 많이 담아두어도 문제가 없다.
     */
}
