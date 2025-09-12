package thread.sync;

public interface BankAccount {
    boolean withdraw(int amount); // 계좌의 잔액 > 출금 금액이면  ture  아니면 false

    int getBalance();
}
