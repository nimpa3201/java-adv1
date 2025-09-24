package thread.bounded;

import java.util.ArrayList;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BoundedMain {
    public static void main(String[] args) {
        // 1. BoundedQueue 선택
        //BoundedQueue queue = new BoundedQueueV1(2);
        //BoundedQueue queue = new BoundedQueueV2(2);
        //BoundedQueue queue = new BoundedQueueV3(2);
        //BoundedQueue queue = new BoundedQueueV4(2);
        //BoundedQueue queue = new BoundedQueueV5(2);
        BoundedQueue queue = new BoundedQueueV6_1(2);

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

    /**
     *  20:07:33:017 [     main] == [생산자 먼저 실행] 시작, BoundedQueueV4 ==
     *
     *  20:07:33:030 [     main] 생산자 시작
     *  20:07:33:066 [producer1] [생산 시도] data1 -> []
     *  20:07:33:066 [producer1] [put] 생산자 데이터 저장, notify() 호출
     *  20:07:33:067 [producer1] [생산 완료] data1 -> [data1]
     *  20:07:33:171 [producer2] [생산 시도] data2 -> [data1]
     *  20:07:33:171 [producer2] [put] 생산자 데이터 저장, notify() 호출
     *  20:07:33:172 [producer2] [생산 완료] data2 -> [data1, data2]
     *  20:07:33:277 [producer3] [생산 시도] data3 -> [data1, data2]
     *  20:07:33:278 [producer3] [put] 큐가 가득 참, 생산자 대기
     *
     *  20:07:33:382 [     main] 현재 상태 출력, 큐 데이터: [data1, data2]
     *  20:07:33:383 [     main] producer1: TERMINATED
     *  20:07:33:384 [     main] producer2: TERMINATED
     *  20:07:33:384 [     main] producer3: WAITING
     *
     *  20:07:33:385 [     main] 소비자 시작
     *  20:07:33:387 [consumer1] [소비 시도]     ? <-[data1, data2]
     *  20:07:33:387 [consumer1] [take] 소비자 데이터 획득, notify() 호출
     *  20:07:33:388 [producer3] [put] 생산자 깨어남
     *  20:07:33:389 [producer3] [put] 생산자 데이터 저장, notify() 호출
     *  20:07:33:389 [producer3] [생산 완료] data3 -> [data2, data3]
     *  20:07:33:388 [consumer1] [소비 완료] data1<-[data2]
     *  20:07:33:491 [consumer2] [소비 시도]     ? <-[data2, data3]
     *  20:07:33:492 [consumer2] [take] 소비자 데이터 획득, notify() 호출
     *  20:07:33:492 [consumer2] [소비 완료] data2<-[data3]
     *  20:07:33:594 [consumer3] [소비 시도]     ? <-[data3]
     *  20:07:33:594 [consumer3] [take] 소비자 데이터 획득, notify() 호출
     *  20:07:33:595 [consumer3] [소비 완료] data3<-[]
     *
     *  20:07:33:698 [     main] 현재 상태 출력, 큐 데이터: []
     *  20:07:33:700 [     main] producer1: TERMINATED
     *  20:07:33:701 [     main] producer2: TERMINATED
     *  20:07:33:704 [     main] producer3: TERMINATED
     *  20:07:33:705 [     main] consumer1: TERMINATED
     *  20:07:33:705 [     main] consumer2: TERMINATED
     *  20:07:33:706 [     main] consumer3: TERMINATED
     *  20:07:33:707 [     main] == [생산자 먼저 실행] 종료, BoundedQueueV4 ==
     */

    /**
     * 20:09:35:201 [     main] == [소비자 먼저 실행] 시작, BoundedQueueV4 ==
     *
     *  20:09:35:211 [     main] 소비자 시작
     *  20:09:35:220 [consumer1] [소비 시도]     ? <-[]
     *  20:09:35:221 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     *  20:09:35:325 [consumer2] [소비 시도]     ? <-[]
     *  20:09:35:326 [consumer2] [take] 큐에 데이터가 없음, 소비자 대기
     *  20:09:35:428 [consumer3] [소비 시도]     ? <-[]
     *  20:09:35:428 [consumer3] [take] 큐에 데이터가 없음, 소비자 대기
     *
     *  20:09:35:532 [     main] 현재 상태 출력, 큐 데이터: []
     *  20:09:35:540 [     main] consumer1: WAITING
     *  20:09:35:540 [     main] consumer2: WAITING
     *  20:09:35:541 [     main] consumer3: WAITING
     *
     *  20:09:35:541 [     main] 생산자 시작
     *  20:09:35:546 [producer1] [생산 시도] data1 -> []
     *  20:09:35:547 [producer1] [put] 생산자 데이터 저장, notify() 호출
     *  20:09:35:548 [producer1] [생산 완료] data1 -> [data1]
     *  20:09:35:548 [consumer1] [take] 소비자 데이터 획득, notify() 호출
     *  20:09:35:549 [consumer1] [소비 완료] data1<-[]
     *  20:09:35:549 [consumer2] [take] 큐에 데이터가 없음, 소비자 대기
     *  20:09:35:652 [producer2] [생산 시도] data2 -> []
     *  20:09:35:653 [producer2] [put] 생산자 데이터 저장, notify() 호출
     *  20:09:35:654 [producer2] [생산 완료] data2 -> [data2]
     *  20:09:35:658 [consumer3] [take] 소비자 데이터 획득, notify() 호출
     *  20:09:35:659 [consumer3] [소비 완료] data2<-[]
     *  20:09:35:660 [consumer2] [take] 큐에 데이터가 없음, 소비자 대기
     *  20:09:35:757 [producer3] [생산 시도] data3 -> []
     *  20:09:35:758 [producer3] [put] 생산자 데이터 저장, notify() 호출
     *  20:09:35:758 [producer3] [생산 완료] data3 -> [data3]
     *  20:09:35:758 [consumer2] [take] 소비자 데이터 획득, notify() 호출
     *  20:09:35:759 [consumer2] [소비 완료] data3<-[]
     *
     *  20:09:35:859 [     main] 현재 상태 출력, 큐 데이터: []
     *  20:09:35:859 [     main] consumer1: TERMINATED
     *  20:09:35:860 [     main] consumer2: TERMINATED
     *  20:09:35:860 [     main] consumer3: TERMINATED
     *  20:09:35:861 [     main] producer1: TERMINATED
     *  20:09:35:861 [     main] producer2: TERMINATED
     *  20:09:35:861 [     main] producer3: TERMINATED
     *  20:09:35:862 [     main] == [소비자 먼저 실행] 종료, BoundedQueueV4 ==
     */

    /**
     *  20:37:22:336 [     main] == [생산자 먼저 실행] 시작, BoundedQueueV5 ==
     *
     *  20:37:22:341 [     main] 생산자 시작
     *  20:37:22:356 [producer1] [생산 시도] data1 -> []
     *  20:37:22:356 [producer1] [put] 생산자 데이터 저장, consumerCond.signal() 호출
     *  20:37:22:357 [producer1] [생산 완료] data1 -> [data1]
     *  20:37:22:451 [producer2] [생산 시도] data2 -> [data1]
     *  20:37:22:451 [producer2] [put] 생산자 데이터 저장, consumerCond.signal() 호출
     *  20:37:22:451 [producer2] [생산 완료] data2 -> [data1, data2]
     *  20:37:22:555 [producer3] [생산 시도] data3 -> [data1, data2]
     *  20:37:22:555 [producer3] [put] 큐가 가득 참, 생산자 대기
     *
     *  20:37:22:656 [     main] 현재 상태 출력, 큐 데이터: [data1, data2]
     *  20:37:22:657 [     main] producer1: TERMINATED
     *  20:37:22:658 [     main] producer2: TERMINATED
     *  20:37:22:658 [     main] producer3: WAITING
     *
     *  20:37:22:658 [     main] 소비자 시작
     *  20:37:22:660 [consumer1] [소비 시도]     ? <-[data1, data2]
     *  20:37:22:661 [consumer1] [take] 소비자 데이터 획득,  producerCond.signal() 호출
     *  20:37:22:661 [consumer1] [소비 완료] data1<-[data2]
     *  20:37:22:662 [producer3] [put] 생산자 깨어남
     *  20:37:22:662 [producer3] [put] 생산자 데이터 저장, consumerCond.signal() 호출
     *  20:37:22:662 [producer3] [생산 완료] data3 -> [data2, data3]
     *  20:37:22:766 [consumer2] [소비 시도]     ? <-[data2, data3]
     *  20:37:22:766 [consumer2] [take] 소비자 데이터 획득,  producerCond.signal() 호출
     *  20:37:22:767 [consumer2] [소비 완료] data2<-[data3]
     *  20:37:22:870 [consumer3] [소비 시도]     ? <-[data3]
     *  20:37:22:870 [consumer3] [take] 소비자 데이터 획득,  producerCond.signal() 호출
     *  20:37:22:872 [consumer3] [소비 완료] data3<-[]
     *
     *  20:37:22:971 [     main] 현재 상태 출력, 큐 데이터: []
     *  20:37:22:971 [     main] producer1: TERMINATED
     *  20:37:22:971 [     main] producer2: TERMINATED
     *  20:37:22:972 [     main] producer3: TERMINATED
     *  20:37:22:972 [     main] consumer1: TERMINATED
     *  20:37:22:976 [     main] consumer2: TERMINATED
     *  20:37:22:976 [     main] consumer3: TERMINATED
     *  20:37:22:977 [     main] == [생산자 먼저 실행] 종료, BoundedQueueV5 ==
     */

    /**
     * 20:38:16:022 [     main] == [소비자 먼저 실행] 시작, BoundedQueueV5 ==
     *
     *  20:38:16:031 [     main] 소비자 시작
     *  20:38:16:050 [consumer1] [소비 시도]     ? <-[]
     *  20:38:16:050 [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
     *  20:38:16:151 [consumer2] [소비 시도]     ? <-[]
     *  20:38:16:152 [consumer2] [take] 큐에 데이터가 없음, 소비자 대기
     *  20:38:16:256 [consumer3] [소비 시도]     ? <-[]
     *  20:38:16:256 [consumer3] [take] 큐에 데이터가 없음, 소비자 대기
     *
     *  20:38:16:361 [     main] 현재 상태 출력, 큐 데이터: []
     *  20:38:16:374 [     main] consumer1: WAITING
     *  20:38:16:374 [     main] consumer2: WAITING
     *  20:38:16:374 [     main] consumer3: WAITING
     *
     *  20:38:16:375 [     main] 생산자 시작
     *  20:38:16:378 [producer1] [생산 시도] data1 -> []
     *  20:38:16:378 [producer1] [put] 생산자 데이터 저장, consumerCond.signal() 호출
     *  20:38:16:379 [producer1] [생산 완료] data1 -> [data1]
     *  20:38:16:381 [consumer1] [take] 소비자 데이터 획득,  producerCond.signal() 호출
     *  20:38:16:382 [consumer1] [소비 완료] data1<-[]
     *  20:38:16:478 [producer2] [생산 시도] data2 -> []
     *  20:38:16:480 [producer2] [put] 생산자 데이터 저장, consumerCond.signal() 호출
     *  20:38:16:481 [producer2] [생산 완료] data2 -> [data2]
     *  20:38:16:481 [consumer2] [take] 소비자 데이터 획득,  producerCond.signal() 호출
     *  20:38:16:483 [consumer2] [소비 완료] data2<-[]
     *  20:38:16:583 [producer3] [생산 시도] data3 -> []
     *  20:38:16:583 [producer3] [put] 생산자 데이터 저장, consumerCond.signal() 호출
     *  20:38:16:584 [consumer3] [take] 소비자 데이터 획득,  producerCond.signal() 호출
     *  20:38:16:584 [producer3] [생산 완료] data3 -> [data3]
     *  20:38:16:584 [consumer3] [소비 완료] data3<-[]
     *
     *  20:38:16:688 [     main] 현재 상태 출력, 큐 데이터: []
     *  20:38:16:688 [     main] consumer1: TERMINATED
     *  20:38:16:689 [     main] consumer2: TERMINATED
     *  20:38:16:690 [     main] consumer3: TERMINATED
     *  20:38:16:691 [     main] producer1: TERMINATED
     *  20:38:16:691 [     main] producer2: TERMINATED
     *  20:38:16:691 [     main] producer3: TERMINATED
     *  20:38:16:692 [     main] == [소비자 먼저 실행] 종료, BoundedQueueV5 ==
     */

    /**
     * 21:45:17:152 [     main] == [생산자 먼저 실행] 시작, BoundedQueueV6_1 ==
     *
     *  21:45:17:161 [     main] 생산자 시작
     *  21:45:17:181 [producer1] [생산 시도] data1 -> []
     *  21:45:17:182 [producer1] [생산 완료] data1 -> [data1]
     *  21:45:17:285 [producer2] [생산 시도] data2 -> [data1]
     *  21:45:17:285 [producer2] [생산 완료] data2 -> [data1, data2]
     *  21:45:17:388 [producer3] [생산 시도] data3 -> [data1, data2]
     *
     *  21:45:17:493 [     main] 현재 상태 출력, 큐 데이터: [data1, data2]
     *  21:45:17:495 [     main] producer1: TERMINATED
     *  21:45:17:495 [     main] producer2: TERMINATED
     *  21:45:17:496 [     main] producer3: WAITING
     *
     *  21:45:17:496 [     main] 소비자 시작
     *  21:45:17:499 [consumer1] [소비 시도]     ? <-[data1, data2]
     *  21:45:17:501 [consumer1] [소비 완료] data1<-[data2]
     *  21:45:17:504 [producer3] [생산 완료] data3 -> [data2, data3]
     *  21:45:17:604 [consumer2] [소비 시도]     ? <-[data2, data3]
     *  21:45:17:605 [consumer2] [소비 완료] data2<-[data3]
     *  21:45:17:709 [consumer3] [소비 시도]     ? <-[data3]
     *  21:45:17:710 [consumer3] [소비 완료] data3<-[]
     *
     *  21:45:17:812 [     main] 현재 상태 출력, 큐 데이터: []
     *  21:45:17:812 [     main] producer1: TERMINATED
     *  21:45:17:813 [     main] producer2: TERMINATED
     *  21:45:17:814 [     main] producer3: TERMINATED
     *  21:45:17:814 [     main] consumer1: TERMINATED
     *  21:45:17:815 [     main] consumer2: TERMINATED
     *  21:45:17:815 [     main] consumer3: TERMINATED
     *  21:45:17:816 [     main] == [생산자 먼저 실행] 종료, BoundedQueueV6_1 ==
     */

    /**
     * 21:46:15:169 [     main] == [소비자 먼저 실행] 시작, BoundedQueueV6_1 ==
     *
     *  21:46:15:175 [     main] 소비자 시작
     *  21:46:15:184 [consumer1] [소비 시도]     ? <-[]
     *  21:46:15:286 [consumer2] [소비 시도]     ? <-[]
     *  21:46:15:387 [consumer3] [소비 시도]     ? <-[]
     *
     *  21:46:15:492 [     main] 현재 상태 출력, 큐 데이터: []
     *  21:46:15:501 [     main] consumer1: WAITING
     *  21:46:15:502 [     main] consumer2: WAITING
     *  21:46:15:502 [     main] consumer3: WAITING
     *
     *  21:46:15:503 [     main] 생산자 시작
     *  21:46:15:505 [producer1] [생산 시도] data1 -> []
     *  21:46:15:506 [producer1] [생산 완료] data1 -> [data1]
     *  21:46:15:506 [consumer1] [소비 완료] data1<-[]
     *  21:46:15:609 [producer2] [생산 시도] data2 -> []
     *  21:46:15:610 [producer2] [생산 완료] data2 -> [data2]
     *  21:46:15:611 [consumer2] [소비 완료] data2<-[]
     *  21:46:15:711 [producer3] [생산 시도] data3 -> []
     *  21:46:15:712 [consumer3] [소비 완료] data3<-[]
     *  21:46:15:712 [producer3] [생산 완료] data3 -> [data3]
     *
     *  21:46:15:812 [     main] 현재 상태 출력, 큐 데이터: []
     *  21:46:15:812 [     main] consumer1: TERMINATED
     *  21:46:15:813 [     main] consumer2: TERMINATED
     *  21:46:15:813 [     main] consumer3: TERMINATED
     *  21:46:15:813 [     main] producer1: TERMINATED
     *  21:46:15:814 [     main] producer2: TERMINATED
     *  21:46:15:814 [     main] producer3: TERMINATED
     *  21:46:15:815 [     main] == [소비자 먼저 실행] 종료, BoundedQueueV6_1 ==
     */
}
