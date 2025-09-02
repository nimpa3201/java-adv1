package thread.start;

public class BadThreadMain {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " : main() start");

        HelloThread helloThread = new HelloThread();

        System.out.println(Thread.currentThread().getName() + ": start() 호출 전");

        helloThread.run(); // 실행만 시킬 뿐 기다리지 않음

        System.out.println(Thread.currentThread().getName() + ": start() 호출 후");

        System.out.println(Thread.currentThread().getName() + " : main() end");


     /*
     main : main() start
     main: start() 호출 전
     main: run()
     main: start() 호출 후
     main : main() end

    `main` 스레드가 `run()` 메서드를 실행했기 때문에 `main` 스레드가 사용하는 스택위에 `run()` 스택 프레임이 올라간다.
     결과적으로 `main` 스레드에서 모든 것을 처리한 것이 된다.

      */
    }
}
