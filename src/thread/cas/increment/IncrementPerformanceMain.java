package thread.cas.increment;

public class IncrementPerformanceMain {

    public static final long COUNT = 100_000_000;

    public static void main(String[] args) {
        test(new BasicInteger());
        test(new VolatileInteger());
        test(new SyncInteger());
        test(new MyAtomicInteger());

    }

    private static void test(IncrementInteger incrementInteger) {
        long startMs = System.currentTimeMillis();

        for (long i = 0; i < COUNT; i++) {
            incrementInteger.increment();

        }


        long endMs = System.currentTimeMillis();
        System.out.println(incrementInteger.getClass().getSimpleName() + ": ms=" + (endMs - startMs));


    }
    /**
     * BasicInteger: ms=343  CPU 캐시를 적극 활용하고 CPU 캐시의 위력을 알 수 있다 / 단일 스레드가 사용하는 경우 효율적
     * VolatileInteger: ms=889 원자적 연산일때는 유용 / 하지만 value++ 소용없음
     * SyncInteger: ms=2434   안전하지만 MyAtomicInteger 보다 느림
     * MyAtomicInteger: ms=903 synchronized , ReentrantLock 을 사용하는 경우보다 1,5 ~ 2배 정도 빠르다 -> 락을 사용하지 않고, 원자적 연산을 만들어냄
     */
}
