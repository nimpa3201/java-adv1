package thread.excutor;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class RunnableTasks implements Runnable{

    private final String name;
    private  int sleepMs = 1000;

    public RunnableTasks(String name) {
        this.name = name;
    }

    public RunnableTasks(String name, int sleepMs) {
        this.name = name;
        this.sleepMs = sleepMs;
    }

    @Override
    public void run() {
        log(name +" 시작");
        sleep(sleepMs); // 작업 시간 시뮬레이션
        log(name +" 완료");
    }
}
