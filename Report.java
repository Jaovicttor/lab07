import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Report implements Runnable {

    Map<String, Integer> stock ;
    ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();



    public Report(Map<String, Integer> stock){
        this.stock = stock;
    }

    @Override
    public void run() {
        this.es.scheduleAtFixedRate(() -> {
            try {
                String dataHora = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());

                System.out.println("-------------------------------------------");
                System.out.println("Situação do Estoque - " + dataHora);
                System.out.println("-------------------------------------------");

                for (Map.Entry<String, Integer> entry : stock.entrySet()) {
                    System.out.println(entry.getKey() + " : " + entry.getValue());
                }
                System.out.println("-------------------------------------------");
            } catch (Exception e) {
                System.out.println("Erro no orderProducer");
            }
        }, 0,60, TimeUnit.SECONDS);
    }
}
