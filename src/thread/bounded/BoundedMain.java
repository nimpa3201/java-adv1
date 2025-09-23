package thread.bounded;

import java.util.ArrayList;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BoundedMain {
    public static void main(String[] args) {
        // 1. BoundedQueue 선택
        //BoundedQueue queue = new BoundedQueueV1(2);
        BoundedQueue queue = new BoundedQueueV2(2);

        // 2. 생산자, 소비자 실행 순서 선택, 반드시 하나만 선택!
        //producerFirst(queue); // 생산자 먼저 실행
        consumerFirst(queue); // 서비자 먼저 실


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
     * 06:01:50:657 [     main] == [생산자 먼저 실행] 시작, BoundedQueueV2 ==
     *
     *  06:01:50:664 [     main] 생산자 시작
     *  06:01:50:682 [producer1] [생산 시도] data1 -> []
     *  06:01:50:683 [producer1] [생산 완료] data1 -> [data1]
     *  06:01:50:776 [producer2] [생산 시도] data2 -> [data1]
     *  06:01:50:776 [producer2] [생산 완료] data2 -> [data1, data2]
     *  06:01:50:880 [producer3] [생산 시도] data3 -> [data1, data2]
     *  06:01:50:880 [producer3] [put] 큐가 가득 참, 생산자 대기
     *
     *  06:01:50:980 [     main] 현재 상태 출력, 큐 데이터: [data1, data2]
     *  06:01:50:981 [     main] producer1: TERMINATED
     *  06:01:50:982 [     main] producer2: TERMINATED
     *  06:01:50:982 [     main] producer3: TIMED_WAITING
     *
     *  06:01:50:983 [     main] 소비자 시작
     *  06:01:50:985 [consumer1] [소비 시도]     ? <-[data1, data2]
     *  06:01:51:091 [consumer2] [소비 시도]     ? <-[data1, data2]
     *  06:01:51:192 [consumer3] [소비 시도]     ? <-[data1, data2]
     *
     *  06:01:51:292 [     main] 현재 상태 출력, 큐 데이터: [data1, data2]
     *  06:01:51:293 [     main] producer1: TERMINATED
     *  06:01:51:294 [     main] producer2: TERMINATED
     *  06:01:51:294 [     main] producer3: TIMED_WAITING
     *  06:01:51:295 [     main] consumer1: BLOCKED
     *  06:01:51:295 [     main] consumer2: BLOCKED
     *  06:01:51:296 [     main] consumer3: BLOCKED
     *  06:01:51:296 [     main] == [생산자 먼저 실행] 종료, BoundedQueueV2 ==
     *  06:01:51:883 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:01:52:888 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:01:53:895 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:01:54:903 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:01:55:910 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:01:56:919 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:01:57:926 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:01:58:928 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:01:59:931 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:00:933 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:01:936 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:02:937 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:03:939 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:04:944 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:05:951 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:06:956 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:07:961 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:08:963 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:09:965 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:10:971 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:11:979 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:12:981 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:13:983 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:14:989 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:15:993 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:16:996 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:18:003 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:19:005 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:20:013 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:21:017 [producer3] [put] 큐가 가득 참, 생산자 대기
     *  06:02:22:019 [producer3] [put] 큐가 가득 참, 생산자 대기
     */


    /**  06:05:05:389 [     main] == [소비자 먼저 실행] 시작, BoundedQueueV2 ==

     06:05:05:397 [     main] 소비자 시작
     06:05:05:407 [consumer1] [소비 시도]     ? <-[]
     06:05:05:407 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:05:513 [consumer2] [소비 시도]     ? <-[]
     06:05:05:613 [consumer3] [소비 시도]     ? <-[]

     06:05:05:717 [     main] 현재 상태 출력, 큐 데이터: []
     06:05:05:730 [     main] consumer1: TIMED_WAITING
     06:05:05:731 [     main] consumer2: BLOCKED
     06:05:05:732 [     main] consumer3: BLOCKED

     06:05:05:732 [     main] 생산자 시작
     06:05:05:736 [producer1] [생산 시도] data1 -> []
     06:05:05:839 [producer2] [생산 시도] data2 -> []
     06:05:05:941 [producer3] [생산 시도] data3 -> []

     06:05:06:043 [     main] 현재 상태 출력, 큐 데이터: []
     06:05:06:043 [     main] consumer1: TIMED_WAITING
     06:05:06:044 [     main] consumer2: BLOCKED
     06:05:06:044 [     main] consumer3: BLOCKED
     06:05:06:045 [     main] producer1: BLOCKED
     06:05:06:045 [     main] producer2: BLOCKED
     06:05:06:045 [     main] producer3: BLOCKED
     06:05:06:046 [     main] == [소비자 먼저 실행] 종료, BoundedQueueV2 ==
     06:05:06:412 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:07:414 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:08:423 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:09:424 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:10:425 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:11:427 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:12:429 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:13:432 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:14:434 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:15:439 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:16:446 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:17:453 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:18:455 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:19:456 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:20:462 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:21:467 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:22:469 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:23:475 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:24:480 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:25:486 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:26:491 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:27:495 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:28:502 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:29:504 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:30:507 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     06:05:31:513 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     */
}
