import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class MyThread {



    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(8);
        for (int i =0; i<10; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("test");
                }
            });
        }
    }
}