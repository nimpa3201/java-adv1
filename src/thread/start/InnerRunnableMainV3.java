package thread.start;

import static util.MyLogger.log;

// 변수 없이 전달
public class InnerRunnableMainV3 {
    public static void main(String[] args) {
        log("main() start");


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                log("run");
            }
        });
        thread.start();


        log("main() end");

    }



}
