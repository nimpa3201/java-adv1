package thread.bounded;

import java.util.ArrayList;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BoundedMain {
    public static void main(String[] args) {
        // 1. BoundedQueue 선택
        //BoundedQueue queue = new BoundedQueueV1(2);
        //BoundedQueue queue = new BoundedQueueV2(2);
        BoundedQueue queue = new BoundedQueueV3(2);

        // 2. 생산자, 소비자 실행 순서 선택, 반드시 하나만 선택!
        //producerFirst(queue); // 생산자 먼저 실행
        consumerFirst(queue); // 서비자 먼저 실행


    }

    private static void consumerFirst(BoundedQueue queue) {
        log("== [소비자 먼저 실행] 시작, " + queue.getClass().getSimpleName() + " ==");
        ArrayList<Thread> threads = new ArrayList<>();
        startConsumer(queue, threads);
        printAllState(queue, threads);
        startProducer(queue, threads);
        printAllState(queue, threads);
        log("== [소비자 먼저 실행] 종료, " + queue.getClass().getSimpleName() + " ==");

    }

    private static void producerFirst(BoundedQueue queue) {
        log("== [생산자 먼저 실행] 시작, " + queue.getClass().getSimpleName() + " ==");
        ArrayList<Thread> threads = new ArrayList<>();
        startProducer(queue, threads);
        printAllState(queue, threads);
        startConsumer(queue, threads);
        printAllState(queue, threads);
        log("== [생산자 먼저 실행] 종료, " + queue.getClass().getSimpleName() + " ==");
    }

    private static void startConsumer(BoundedQueue queue, ArrayList<Thread> threads) {
        System.out.println();
        log("소비자 시작");
        for (int i = 1; i <= 3; i++) {
            Thread consumer = new Thread(new ConsumerTask(queue), "consumer" + i);
            threads.add(consumer);
            consumer.start();
            sleep(100);


        }
    }


    private static void startProducer(BoundedQueue queue, ArrayList<Thread> threads) {
        System.out.println();
        log("생산자 시작");
        for (int i = 1; i <= 3; i++) {
            Thread producer = new Thread(new ProducerTask(queue, "data" + i), "producer" + i);
            threads.add(producer);
            producer.start();
            sleep(100);

        }

    }

    private static void printAllState(BoundedQueue queue, ArrayList<Thread> threads) {
        System.out.println();
        log("현재 상태 출력, 큐 데이터: " + queue);
        for (Thread thread : threads) {
            log(thread.getName() + ": " + thread.getState());
        }
    }

    /**
     * 06:33:08:584 [     main] == [생산자 먼저 실행] 시작, BoundedQueueV3 ==
     *
     *  06:33:08:591 [     main] 생산자 시작
     *  06:33:08:610 [producer1] [생산 시도] data1 -> []
     *  06:33:08:611 [producer1] [put] 생산자 데이터 저장, notify() 호출
     *  06:33:08:612 [producer1] [생산 완료] data1 -> [data1]
     *  06:33:08:711 [producer2] [생산 시도] data2 -> [data1]
     *  06:33:08:711 [producer2] [put] 생산자 데이터 저장, notify() 호출
     *  06:33:08:712 [producer2] [생산 완료] data2 -> [data1, data2]
     *  06:33:08:815 [producer3] [생산 시도] data3 -> [data1, data2]
     *  06:33:08:816 [producer3] [put] 큐가 가득 참, 생산자 대기
     *
     *  06:33:08:919 [     main] 현재 상태 출력, 큐 데이터: [data1, data2]
     *  06:33:08:920 [     main] producer1: TERMINATED
     *  06:33:08:921 [     main] producer2: TERMINATED
     *  06:33:08:921 [     main] producer3: WAITING
     *
     *  06:33:08:922 [     main] 소비자 시작
     *  06:33:08:925 [consumer1] [소비 시도]     ? <-[data1, data2]
     *  06:33:08:926 [consumer1] [take] 소비자 데이터 획득, notify() 호출
     *  06:33:08:927 [consumer1] [소비 완료] data1<-[data2]
     *  06:33:08:928 [producer3] [put] 생산자 깨어남
     *  06:33:08:928 [producer3] [put] 생산자 데이터 저장, notify() 호출
     *  06:33:08:929 [producer3] [생산 완료] data3 -> [data2, data3]
     *  06:33:09:026 [consumer2] [소비 시도]     ? <-[data2, data3]
     *  06:33:09:027 [consumer2] [take] 소비자 데이터 획득, notify() 호출
     *  06:33:09:028 [consumer2] [소비 완료] data2<-[data3]
     *  06:33:09:129 [consumer3] [소비 시도]     ? <-[data3]
     *  06:33:09:130 [consumer3] [take] 소비자 데이터 획득, notify() 호출
     *  06:33:09:131 [consumer3] [소비 완료] data3<-[]
     *
     *  06:33:09:232 [     main] 현재 상태 출력, 큐 데이터: []
     *  06:33:09:233 [     main] producer1: TERMINATED
     *  06:33:09:235 [     main] producer2: TERMINATED
     *  06:33:09:235 [     main] producer3: TERMINATED
     *  06:33:09:236 [     main] consumer1: TERMINATED
     *  06:33:09:236 [     main] consumer2: TERMINATED
     *  06:33:09:237 [     main] consumer3: TERMINATED
     *  06:33:09:240 [     main] == [생산자 먼저 실행] 종료, BoundedQueueV3 ==
     */

    /**
     * 06:34:06:225 [     main] == [소비자 먼저 실행] 시작, BoundedQueueV3 ==
     *
     *  06:34:06:231 [     main] 소비자 시작
     *  06:34:06:239 [consumer1] [소비 시도]     ? <-[]
     *  06:34:06:240 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     *  06:34:06:342 [consumer2] [소비 시도]     ? <-[]
     *  06:34:06:342 [consumer2] [take] 큐에 데이터가 없음, 소비자 대기
     *  06:34:06:443 [consumer3] [소비 시도]     ? <-[]
     *  06:34:06:443 [consumer3] [take] 큐에 데이터가 없음, 소비자 대기
     *
     *  06:34:06:544 [     main] 현재 상태 출력, 큐 데이터: []
     *  06:34:06:554 [     main] consumer1: WAITING
     *  06:34:06:555 [     main] consumer2: WAITING
     *  06:34:06:555 [     main] consumer3: WAITING
     *
     *  06:34:06:555 [     main] 생산자 시작
     *  06:34:06:563 [producer1] [생산 시도] data1 -> []
     *  06:34:06:563 [producer1] [put] 생산자 데이터 저장, notify() 호출
     *  06:34:06:564 [consumer1] [take] 소비자 데이터 획득, notify() 호출
     *  06:34:06:564 [consumer1] [소비 완료] data1<-[]
     *  06:34:06:565 [consumer2] [take] 큐에 데이터가 없음, 소비자 대기
     *  06:34:06:564 [producer1] [생산 완료] data1 -> [data1]
     *  06:34:06:663 [producer2] [생산 시도] data2 -> []
     *  06:34:06:664 [producer2] [put] 생산자 데이터 저장, notify() 호출
     *  06:34:06:664 [producer2] [생산 완료] data2 -> [data2]
     *  06:34:06:664 [consumer3] [take] 소비자 데이터 획득, notify() 호출
     *  06:34:06:665 [consumer2] [take] 큐에 데이터가 없음, 소비자 대기
     *  06:34:06:665 [consumer3] [소비 완료] data2<-[]
     *  06:34:06:768 [producer3] [생산 시도] data3 -> []
     *  06:34:06:769 [producer3] [put] 생산자 데이터 저장, notify() 호출
     *  06:34:06:769 [producer3] [생산 완료] data3 -> [data3]
     *  06:34:06:770 [consumer2] [take] 소비자 데이터 획득, notify() 호출
     *  06:34:06:770 [consumer2] [소비 완료] data3<-[]
     *
     *  06:34:06:869 [     main] 현재 상태 출력, 큐 데이터: []
     *  06:34:06:870 [     main] consumer1: TERMINATED
     *  06:34:06:871 [     main] consumer2: TERMINATED
     *  06:34:06:872 [     main] consumer3: TERMINATED
     *  06:34:06:872 [     main] producer1: TERMINATED
     *  06:34:06:873 [     main] producer2: TERMINATED
     *  06:34:06:873 [     main] producer3: TERMINATED
     *  06:34:06:874 [     main] == [소비자 먼저 실행] 종료, BoundedQueueV3 ==
     */
}
