package thread.sync;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BankMain {
    public static void main(String[] args) throws InterruptedException {
        //BankAccount account = new BankAccountV1(1000);
        //BankAccount account = new BankAccountV2(1000);
        //BankAccount account = new BankAccountV3(1000);
        BankAccount account = new BankAccountV4(1000);

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
     02:50:45:564 [       t2] 거래 시작: BankAccountV3
     02:50:45:564 [       t1] 거래 시작: BankAccountV3
     02:50:45:581 [       t2] [검증 시작] 출금액: 800, 잔액: 1000
     02:50:45:582 [       t2] [검증 완료] 출금액: 800, 잔액: 1000
     02:50:46:011 [     main] t1 state: BLOCKED
     02:50:46:012 [     main] t2 state: TIMED_WAITING
     02:50:46:584 [       t2] [출금 완료] 출금액: 800, 잔액: 200
     02:50:46:585 [       t2] 거래 종료
     02:50:46:587 [       t1] [검증 시작] 출금액: 800, 잔액: 200
     02:50:46:588 [       t1] [검증 실패] 출금액: 800, 잔액: 200
     02:50:46:601 [     main] 최종 잔액: 200
     */

    // 임계 영역은 가능환 최소한의 범위에 적용해야 한다. 그래야 동시에 여러 스레드가 실행할 수 있는 부분을 늘려서, 전체적인 처리 성능을 더 높일 수 있다
    /**
     *  06:23:32:712 [       t1] 거래 시작: BankAccountV4
     *  06:23:32:712 [       t2] 거래 시작: BankAccountV4
     *  06:23:32:739 [       t1] [검증 시작] 출금액: 800, 잔액: 1000
     *  06:23:32:740 [       t1] [검증 완료] 출금액: 800, 잔액: 1000
     *  06:23:33:140 [     main] t1 state: TIMED_WAITING
     *  06:23:33:141 [     main] t2 state: WAITING
     *  06:23:33:743 [       t1] [출금 완료] 출금액: 800, 잔액: 200
     *  06:23:33:744 [       t1] 거래 종료
     *  06:23:33:744 [       t2] [검증 시작] 출금액: 800, 잔액: 200
     *  06:23:33:746 [       t2] [검증 실패] 출금액: 800, 잔액: 200
     *  06:23:33:752 [     main] 최종 잔액: 200
     */

}
