package thread.bounded;

import java.util.ArrayList;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BoundedMain {
    public static void main(String[] args) {
        // 1. BoundedQueue 선택
        BoundedQueue queue = new BoundedQueueV1(2);

        // 2. 생산자, 소비자 실행 순서 선택, 반드시 하나만 선택!
        producerFirst(queue); // 생산자 먼저 실행
        //consumerFirst(queue); // 서비자 먼저 실


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
     * 16:16:18:188 [     main] == [생산자 먼저 실행] 시작, BoundedQueueV1 ==
     *
     *  16:16:18:196 [     main] 생산자 시작
     *  16:16:18:214 [producer1] [생산 시도] data1 -> []
     *  16:16:18:215 [producer1] [생산 완료] data1 -> [data1]
     *  16:16:18:306 [producer2] [생산 시도] data2 -> [data1]
     *  16:16:18:307 [producer2] [생산 완료] data2 -> [data1, data2]
     *  16:16:18:410 [producer3] [생산 시도] data3 -> [data1, data2]
     *  16:16:18:412 [producer3] [put] 큐가 가득 참, 버림: data3
     *  16:16:18:412 [producer3] [생산 완료] data3 -> [data1, data2]
     *
     *  16:16:18:513 [     main] 현재 상태 출력, 큐 데이터: [data1, data2]
     *  16:16:18:514 [     main] producer1: TERMINATED
     *  16:16:18:514 [     main] producer2: TERMINATED
     *  16:16:18:515 [     main] producer3: TERMINATED
     *
     *  16:16:18:515 [     main] 소비자 시작
     *  16:16:18:518 [consumer1] [소비 시도]     ? <-[data1, data2]
     *  16:16:18:519 [consumer1] [소비 완료] data1<-[data2]
     *  16:16:18:619 [consumer2] [소비 시도]     ? <-[data2]
     *  16:16:18:620 [consumer2] [소비 완료] data2<-[]
     *  16:16:18:724 [consumer3] [소비 시도]     ? <-[]
     *  16:16:18:724 [consumer3] [소비 완료] null<-[]
     *
     *  16:16:18:828 [     main] 현재 상태 출력, 큐 데이터: []
     *  16:16:18:829 [     main] producer1: TERMINATED
     *  16:16:18:830 [     main] producer2: TERMINATED
     *  16:16:18:830 [     main] producer3: TERMINATED
     *  16:16:18:830 [     main] consumer1: TERMINATED
     *  16:16:18:831 [     main] consumer2: TERMINATED
     *  16:16:18:831 [     main] consumer3: TERMINATED
     *  16:16:18:832 [     main] == [생산자 먼저 실행] 종료, BoundedQueueV1 ==
     */


    /** 16:18:08:990 [     main] == [소비자 먼저 실행] 시작, BoundedQueueV1 ==

     16:18:08:997 [     main] 소비자 시작
     16:18:09:007 [consumer1] [소비 시도]     ? <-[]
     16:18:09:015 [consumer1] [소비 완료] null<-[]
     16:18:09:127 [consumer2] [소비 시도]     ? <-[]
     16:18:09:128 [consumer2] [소비 완료] null<-[]
     16:18:09:228 [consumer3] [소비 시도]     ? <-[]
     16:18:09:229 [consumer3] [소비 완료] null<-[]

     16:18:09:333 [     main] 현재 상태 출력, 큐 데이터: []
     16:18:09:336 [     main] consumer1: TERMINATED
     16:18:09:336 [     main] consumer2: TERMINATED
     16:18:09:337 [     main] consumer3: TERMINATED

     16:18:09:339 [     main] 생산자 시작
     16:18:09:343 [producer1] [생산 시도] data1 -> []
     16:18:09:346 [producer1] [생산 완료] data1 -> [data1]
     16:18:09:444 [producer2] [생산 시도] data2 -> [data1]
     16:18:09:445 [producer2] [생산 완료] data2 -> [data1, data2]
     16:18:09:544 [producer3] [생산 시도] data3 -> [data1, data2]
     16:18:09:545 [producer3] [put] 큐가 가득 참, 버림: data3
     16:18:09:545 [producer3] [생산 완료] data3 -> [data1, data2]

     16:18:09:656 [     main] 현재 상태 출력, 큐 데이터: [data1, data2]
     16:18:09:656 [     main] consumer1: TERMINATED
     16:18:09:657 [     main] consumer2: TERMINATED
     16:18:09:657 [     main] consumer3: TERMINATED
     16:18:09:657 [     main] producer1: TERMINATED
     16:18:09:658 [     main] producer2: TERMINATED
     16:18:09:658 [     main] producer3: TERMINATED
     16:18:09:659 [     main] == [소비자 먼저 실행] 종료, BoundedQueueV1 ==
     *
     */
}
