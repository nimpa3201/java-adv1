package thread.start;

public class DaemonThreadMain {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " : main() start");

        DeaMonThread deaMonThread = new DeaMonThread();
        deaMonThread.setDaemon(true); // 데몬 스레드 여부
        deaMonThread.start();
        System.out.println(Thread.currentThread().getName() + " : main() end");

        /*
        main : main() start
        main : main() end
        Thread-0 : run()
         - 사용자 스레드 종료되면 데몬 스레드와 종료 여부와 상관없이 종료
         */
    }

    static class DeaMonThread extends Thread {
        @Override
        public void run(){
            System.out.println(Thread.currentThread().getName() + " : run()");

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(Thread.currentThread().getName() + " : run() end");
        }
    }
}
