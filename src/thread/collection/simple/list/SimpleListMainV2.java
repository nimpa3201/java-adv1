package thread.collection.simple.list;

import static util.MyLogger.log;

public class SimpleListMainV2 {
    public static void main(String[] args) throws InterruptedException {
        test(new BasicList());
    }

    private static void test(SimpleList list) throws InterruptedException {
        log(list.getClass().getSimpleName());

        // A를 리스트에 저장하는 코드
        Runnable addA = new Runnable() {
            @Override
            public void run() {
                list.add("A");
                log("Thread-1 : list.add(A)");

            }
        };

        // B를 리스트에 저장하는 코드
        Runnable addB = new Runnable() {
            @Override
            public void run() {
                list.add("B");
                log("Thread-2 : list.add(B)");

            }
        };

        Thread thread1 = new Thread(addA,"Thread-1");
        Thread thread2 = new Thread(addB,"Thread-2");
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        log(list);
    }
    /**
     *  07:36:10:093 [     main] BasicList
     *  07:36:10:207 [ Thread-1] Thread-1 : list.add(A)
     *  07:36:10:207 [ Thread-2] Thread-2 : list.add(B)
     *  07:36:10:208 [     main] [B, null] size =2 ,capacity=5제
     */

    /**
     * 컬렉션 프레임워크 대부분은 스레드 세이프 하지 않음
     * add()와 같은 연산은 마치원자적인 연산처럼 느껴지지만 내부에서는 수 많은 연산들이 함께 사용됌
     */
}
