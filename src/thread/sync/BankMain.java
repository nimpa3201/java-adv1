package thread.sync;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BankMain {
    public static void main(String[] args) throws InterruptedException {
        //BankAccount account = new BankAccountV1(1000);
        BankAccount account = new BankAccountV2(1000);

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
     21:37:27:908 [       t1] 거래 시작: BankAccountV2
     21:37:27:924 [       t1] [검증 시작] 출금액: 800, 잔액: 1000
     21:37:27:925 [       t1] [검증 완료] 출금액: 800, 잔액: 1000
     21:37:28:357 [     main] t1 state: TIMED_WAITING
     21:37:28:358 [     main] t2 state: BLOCKED
     21:37:28:929 [       t1] [출금 완료] 출금액: 800, 잔액: 200
     21:37:28:931 [       t1] 거래 종료
     21:37:28:932 [       t2] 거래 시작: BankAccountV2
     21:37:28:933 [       t2] [검증 시작] 출금액: 800, 잔액: 200
     21:37:28:935 [       t2] [검증 실패] 출금액: 800, 잔액: 200
     21:37:28:943 [     main] 최종 잔액: 200
     */
}
