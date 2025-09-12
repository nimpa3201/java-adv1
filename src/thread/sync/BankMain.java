package thread.sync;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BankMain {
    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccountV1(1000);

        Thread t1 = new Thread(new WithdrawTask(account, 800), "t1");
        Thread t2 = new Thread(new WithdrawTask(account, 800), "t2");

        t1.start();
        t2.start();

        sleep(500); // 검증 완료까지 잠시 대기
        log("t1 state: " + t1.getState());
        log("t2 state: " + t2.getState());


        t1.join();
        t2.join();

        log("최종 잔액: " + account.getBalance());

    }
    /**
     *  20:05:37:632 [       t1] 거래 시작: BankAccountV1
     *  20:05:37:635 [       t2] 거래 시작: BankAccountV1
     *  20:05:37:651 [       t2] [검증 시작] 출금액: 800, 잔액: 1000
     *  20:05:37:651 [       t1] [검증 시작] 출금액: 800, 잔액: 1000
     *  20:05:37:652 [       t1] [검증 완료] 출금액: 800, 잔액: 1000
     *  20:05:37:652 [       t2] [검증 완료] 출금액: 800, 잔액: 1000
     *  20:05:38:075 [     main] t1 state: TIMED_WAITING
     *  20:05:38:076 [     main] t2 state: TIMED_WAITING
     *  20:05:38:654 [       t1] [출금 완료] 출금액: 800, 잔액: 200
     *  20:05:38:655 [       t1] 거래 종료
     *  20:05:38:656 [       t2] [출금 완료] 출금액: 800, 잔액: -600
     *  20:05:38:656 [       t2] 거래 종료
     *  20:05:38:664 [     main] 최종 잔액: -600
     */
}
