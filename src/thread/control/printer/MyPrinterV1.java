package thread.control.printer;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class MyPrinterV1 {
    public static void main(String[] args) {

        Printer printer = new Printer();
        Thread printThread = new Thread(printer, "printer");
        printThread.start();


        Scanner userInput = new Scanner(System.in);

        while (true) {
            log("프린터할 문서를 입력하세요. 종료 (q): ");
            String input = userInput.nextLine();
            if (input.equals("q")) {
                printer.work = false;
                break;
            }

            printer.addJob(input);

        }


    }

    static class Printer implements Runnable {
        volatile boolean work = true;
        Queue<String> jobQueue = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {
            while (work) {
                if (jobQueue.isEmpty()) {
                    continue;
                }

                String job = jobQueue.poll();
                log("출력 시작 : " + job + ", 대기 문서" + jobQueue);
                sleep(3000);
                log("출력 완료:" + job);
            }
            log("프린터 종료");

        }

        public void addJob(String input) {
            jobQueue.offer(input);

        }
    }


    /**
     * 11:39:16:574 [     main] 프린터할 문서를 입력하세요. 종료 (q):
     * a
     *  11:39:20:006 [     main] 프린터할 문서를 입력하세요. 종료 (q):
     *  11:39:20:026 [  printer] 출력 시작 : a, 대기 문서[]
     * b
     *  11:39:20:238 [     main] 프린터할 문서를 입력하세요. 종료 (q):
     * c
     *  11:39:20:502 [     main] 프린터할 문서를 입력하세요. 종료 (q):
     * d
     *  11:39:20:763 [     main] 프린터할 문서를 입력하세요. 종료 (q):
     *  11:39:23:031 [  printer] 출력 완료:a
     *  11:39:23:033 [  printer] 출력 시작 : b, 대기 문서[c, d]
     *  11:39:26:039 [  printer] 출력 완료:b
     *  11:39:26:041 [  printer] 출력 시작 : c, 대기 문서[d]
     *  11:39:29:043 [  printer] 출력 완료:c
     *  11:39:29:045 [  printer] 출력 시작 : d, 대기 문서[]
     *  11:39:32:047 [  printer] 출력 완료:d
     * q
     *  11:39:51:367 [  printer] 프린터 종료
     */
}
