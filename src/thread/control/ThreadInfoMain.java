package thread.control;

import thread.start.HelloRunnable;

import static util.MyLogger.log;

public class ThreadInfoMain {
    public static void main(String[] args) {
        // main 스레드
        Thread mainThread = Thread.currentThread();
        log("mainThread = " + mainThread);
        log("mainThread.threadId() = " + mainThread.threadId());
        log("mainThread.getName=() = " + mainThread.getName());
        log("mainThread.getPriority() = " + mainThread.getPriority()); // 기본 5,우선순위가 스케쥴러에 영향 줌
        log("mainThread.getThreadGroup() = " + mainThread.getThreadGroup());
        log("mainThread.getState() = " + mainThread.getState());


        Thread myThread = new Thread(new HelloRunnable(),"myThread");
        log("myThread = " + myThread);
        log("myThread.threadId() = " + myThread.threadId());
        log("myThread.getName=() = " + myThread.getName());
        log("myThread.getPriority() = " + myThread.getPriority()); // 기본 5,우선순위가 스레드 스케쥴러에 영향 줌
        log("myThread.getThreadGroup() = " + myThread.getThreadGroup());
        log("myThread.getState() = " + myThread.getState());

        /*
         21:05:30:108 [     main] mainThread = Thread[#1,main,5,main] 스레드 아이디, 스레드 이름, 스레드 우선수위, 스레드그룹
         21:05:30:122 [     main] mainThread.threadId() = 1
         21:05:30:123 [     main] mainThread.getName=() = main
         21:05:30:129 [     main] mainThread.getPriority() = 5
         21:05:30:130 [     main] mainThread.getThreadGroup() = java.lang.ThreadGroup[name=main,maxpri=10]
         21:05:30:130 [     main] mainThread.getState() = RUNNABLE
         21:05:30:132 [     main] myThread = Thread[#20,myThread,5,main]
         21:05:30:133 [     main] myThread.threadId() = 20
         21:05:30:133 [     main] myThread.getName=() = myThread
         21:05:30:133 [     main] myThread.getPriority() = 5
         21:05:30:134 [     main] myThread.getThreadGroup() = java.lang.ThreadGroup[name=main,maxpri=10]
         21:05:30:134 [     main] myThread.getState() = NEW
         */

    }
}
