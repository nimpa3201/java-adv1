package thread.collection.simple.list;

public class SyncProxyList implements SimpleList {

    private SimpleList target;

    public SyncProxyList(SimpleList target) {
        this.target = target;
    }

    @Override
    // 1. lock 획득
    public synchronized int size() {
        // 2. 원본 메서드 호출
        return target.size();
        //3. 원본 메서드 반납
    // 4. lock 반납
    }

    @Override
    public synchronized void add(Object e) {
        target.add(e);

    }

    @Override
    public synchronized Object get(int index) {
        return target.get(index);
    }

    @Override
    public String toString() {
        return target.toString() + " by " + this.getClass().getSimpleName();
    }

    /**
     * **프록시 패턴의 주요 목적**
     * **접근 제어**: 실제 객체에 대한 접근을 제한하거나 통제할 수 있다.
     * **성능 향상**: 실제 객체의 생성을 지연시키거나 캐싱하여 성능을 최적화할 수 있다.
     * **부가 기능 제공**: 실제 객체에 추가적인 기능(로깅, 인증, 동기화 등)을 투명하게 제공할 수 있다.
     */
}
