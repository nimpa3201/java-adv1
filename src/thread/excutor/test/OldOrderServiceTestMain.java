package thread.excutor.test;

import thread.excutor.test.OldOrderService;

public class OldOrderServiceTestMain {
    public static void main(String[] args) throws Exception {
        String orderNo = "Order#1234"; // 예시 주문 번호
        OldOrderService orderService = new OldOrderService();
        orderService.order(orderNo);
    }
    /**
     *  20:12:52:258 [pool-1-thread-3] 회계 시스템 업데이트: Order#1234
     *  20:12:52:258 [pool-1-thread-2] 배송 시스템 알림: Order#1234
     *  20:12:52:258 [pool-1-thread-1] 재고 업데이트: Order#1234
     *  20:12:53:278 [     main] 모든 주문 처리가 성공적으로 완료되었습니다.
     */
}