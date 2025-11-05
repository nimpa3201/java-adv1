package thread.excutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import static util.MyLogger.log;

public abstract class ExecutorUtils {

    public static void printState(ExecutorService executorService){
        if (executorService instanceof ThreadPoolExecutor poolExecutor){
            int pool = poolExecutor.getPoolSize();
            int active = poolExecutor.getActiveCount();
            int queuedTasks = poolExecutor.getQueue().size();
            long completedTask = poolExecutor.getCompletedTaskCount();
            log("[pool="+pool +" , active=" +active + " queuedTasks=" +queuedTasks + ",completedTask="+ completedTask + "]");
        } else{
            log(executorService);
        }
    }

    public static void printState(ExecutorService executorService,String taskName){
        if (executorService instanceof ThreadPoolExecutor poolExecutor){
            int pool = poolExecutor.getPoolSize();
            int active = poolExecutor.getActiveCount();
            int queuedTasks = poolExecutor.getQueue().size();
            long completedTask = poolExecutor.getCompletedTaskCount();
            log(taskName + " -> [pool="+pool +" , active=" +active + " queuedTasks=" +queuedTasks + ",completedTask="+ completedTask + "]");
        } else{
            log(executorService);
        }
    }



    /**
     * pool = 스레드 풀에서 관리되는 스레드의 숫자
     * active = 작업을 수행하는 스레드의 숫자
     * queuedTasks = 큐에 대기중인 작업의 숫자
     * completedTask = 완료된 작업의 숫자
     */

}
