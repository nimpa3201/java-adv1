package thread.control;

import static util.MyLogger.log;

public class ThreadStateMain {
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new MyRunnable(), "myThread");
        log("myThread.state1 =" + thread.getState()); // NEW
        log("myThread.start()");
        thread.start();
        Thread.sleep(1000);
        log("myThread.state3 = " + thread.getState()); //TIMED_WAITING
        Thread.sleep(4000);
        log("myThread.state5 = " + thread.getState()); //TERMINATED


    }

    static class MyRunnable implements Runnable{

        @Override
        public void run() {

            try {
                log("start");
                Thread thread = Thread.currentThread();
                log("myThread.state2 = " + thread.getState()); //RUNNABLE
                log("sleep() start");
                Thread.sleep(3000);
                log("sleep() end");
                log("myThread.state4 = " + Thread.currentThread().getState()); // RUNNABLE
                log("end");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     *  19:16:49:127 [     main] myThread.state1 =NEW
     *  19:16:49:136 [     main] myThread.start()
     *  19:16:49:138 [ myThread] start
     *  19:16:49:139 [ myThread] myThread.state2 = RUNNABLE
     *  19:16:49:140 [ myThread] sleep() start
     *  19:16:50:144 [     main] myThread.state3 = TIMED_WAITING
     *  19:16:52:147 [ myThread] sleep() end
     *  19:16:52:149 [ myThread] myThread.state4 = RUNNABLE
     *  19:16:52:150 [ myThread] end
     *  19:16:54:147 [     main] myThread.state5 = TERMINATED
     */

}
