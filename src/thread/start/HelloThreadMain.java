package thread.start;

public class HelloThreadMain {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " : main() start");

        HelloThread helloThread = new HelloThread();

        System.out.println(Thread.currentThread().getName() + ": start() 호출 전");

        helloThread.start(); // 실행만 시킬 뿐 기다리지 않음

        System.out.println(Thread.currentThread().getName() + ": start() 호출 후");

        System.out.println(Thread.currentThread().getName() + " : main() end");


     /*
     main : main() start
     main: start() 호출 전
     main: start() 호출 후
     Thread-0: run()
     main : main() end

    스레드는 순서와 실행 기간을 모두 보장하지 않는다. 이것이 바로 멀티스레드다!

      */
    }
}
