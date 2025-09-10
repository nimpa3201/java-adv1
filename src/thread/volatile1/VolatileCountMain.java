package thread.volatile1;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class VolatileCountMain {
    public static void main(String[] args) {

        MyTask task = new MyTask();
        Thread thread = new Thread(task,"work");
        thread.start();

        sleep(1000);

        task.flag = false;
        log("flag = " + task.flag + " , count =" + task.count + " in main");

    }

    static class MyTask implements Runnable{

        //boolean flag = true;
        //long count;

        volatile boolean flag = true;
        volatile long count;

        @Override
        public void run() {

            while(flag){
                count++;
                //1억번에 한번씩 출력
                if(count % 100_00_000 ==0){
                    log("flag = " + flag + " , count =" + count + " in while()");
                }
            }
            log("flag = " + flag + " , count =" + count + "종료");
        }
    }
    /**
     *  boolean flag = true; 일때
     *  long count;
     *  14:48:32:827 [     work] flag = true , count =10000000 in while()
     *  14:48:32:913 [     work] flag = true , count =20000000 in while()
     *  14:48:32:944 [     work] flag = true , count =30000000 in while()
     *  14:48:32:972 [     work] flag = true , count =40000000 in while()
     *  14:48:32:998 [     work] flag = true , count =50000000 in while()
     *  14:48:33:026 [     work] flag = true , count =60000000 in while()
     *  14:48:33:056 [     work] flag = true , count =70000000 in while()
     *  14:48:33:084 [     work] flag = true , count =80000000 in while()
     *  14:48:33:119 [     work] flag = true , count =90000000 in while()
     *  14:48:33:149 [     work] flag = true , count =100000000 in while()
     *  14:48:33:181 [     work] flag = true , count =110000000 in while()
     *  14:48:33:214 [     work] flag = true , count =120000000 in while()
     *  14:48:33:245 [     work] flag = true , count =130000000 in while()
     *  14:48:33:277 [     work] flag = true , count =140000000 in while()
     *  14:48:33:308 [     work] flag = true , count =150000000 in while()
     *  14:48:33:346 [     work] flag = true , count =160000000 in while()
     *  14:48:33:387 [     work] flag = true , count =170000000 in while()
     *  14:48:33:423 [     work] flag = true , count =180000000 in while()
     *  14:48:33:456 [     work] flag = true , count =190000000 in while()
     *  14:48:33:488 [     work] flag = true , count =200000000 in while()
     *  14:48:33:536 [     work] flag = true , count =210000000 in while()
     *  14:48:33:587 [     work] flag = true , count =220000000 in while()
     *  14:48:33:627 [     work] flag = true , count =230000000 in while()
     *  14:48:33:673 [     work] flag = true , count =240000000 in while()
     *  14:48:33:712 [     work] flag = true , count =250000000 in while()
     *  14:48:33:718 [     main] flag = false , count =251320976 in main  <- main false 시점과
     *  14:48:33:751 [     work] flag = true , count =260000000 in while() <- work false 시점이 다른 것 알 수 있/
     *  14:48:33:752 [     work] flag = false , count =260000000종료
     */


    /**
     *  volatile boolean flag = true;
     *  volatile long count; 사용
     * 14:54:47:723 [     work] flag = true , count =10000000 in while()
     *  14:54:47:828 [     work] flag = true , count =20000000 in while()
     *  14:54:47:917 [     work] flag = true , count =30000000 in while()
     *  14:54:48:016 [     work] flag = true , count =40000000 in while()
     *  14:54:48:108 [     work] flag = true , count =50000000 in while()
     *  14:54:48:201 [     work] flag = true , count =60000000 in while()
     *  14:54:48:294 [     work] flag = true , count =70000000 in while()
     *  14:54:48:387 [     work] flag = true , count =80000000 in while()
     *  14:54:48:483 [     work] flag = true , count =90000000 in while()
     *  14:54:48:560 [     work] flag = false , count =97594339종료
     *  14:54:48:560 [     main] flag = false , count =97594339 in main
     */

    // volatile를 쓰면 메인 메모리에 직접 접근하기떄문에 성능이 떨어진다.
}
