package thread.collection.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynchronizedListMain {
    public static void main(String[] args) {
      List<String> list = Collections.synchronizedList( new ArrayList<>());
      list.add("data1");
      list.add("data2");
      list.add("data3");
      System.out.println(list.getClass());
      System.out.println("list = " + list);
    }
    /**
     * synchronized 프록시 방식의 단점**
     * 하지만 `synchronized` 프록시를 사용하는 방식은 다음과 같은 단점이 있다.
     * 첫째, 동기화 오버헤드가 발생한다. 비록 `synchronized` 키워드가 멀티스레드 환경에서 안전한 접근을 보장하
     * 지만, 각 메서드 호출 시마다 동기화 비용이 추가된다. 이로 인해 성능 저하가 발생할 수 있다.
     * 둘째, 전체 컬렉션에 대해 동기화가 이루어지기 때문에, 잠금 범위가 넓어질 수 있다. 이는 잠금 경합(lock
     * contention)을 증가시키고, 병렬 처리의 효율성을 저하시키는 요인이 된다. 모든 메서드에 대해 동기화를 적용하
     * 다 보면, 특정 스레드가 컬렉션을 사용하고 있을 때 다른 스레드들이 대기해야 하는 상황이 빈번해질 수 있다.
     * 셋째, 정교한 동기화가 불가능하다. `synchronized` 프록시를 사용하면 컬렉션 전체에 대한 동기화가 이루어지
     * 지만, 특정 부분이나 메서드에 대해 선택적으로 동기화를 적용하는 것은 어렵다. 이는 과도한 동기화로 이어질 수
     * 있다.
     */
}
