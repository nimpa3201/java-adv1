package thread.excutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static thread.excutor.ExecutorUtils.printState;
import static util.ThreadUtils.sleep;

public class PreStartPoolMain {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1000);
        printState(es);
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) es;
        poolExecutor.prestartAllCoreThreads();
        sleep(100);
        printState(es);
    }
    /**
     *  19:20:02:666 [     main] [pool=0 , active=0 queuedTasks=0,completedTask=0]
     *  19:20:03:123 [     main] [pool=1000 , active=0 queuedTasks=0,completedTask=0]
     */
}
