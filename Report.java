import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Report implements Runnable {

    Map<String, Product> stock ;
    ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();

    AtomicInteger totalVendido;
    BlockingQueue<Order> ordersCompletedQueue;
    BlockingQueue<Order> ordersPendingQueue;

    public Report(Map<String, Product> stock, AtomicInteger totalVendido, BlockingQueue<Order> ordersCompletedQueue,  BlockingQueue<Order> ordersPendingQueue){
        this.stock = stock;
        this.ordersCompletedQueue = ordersCompletedQueue;
        this.ordersPendingQueue = ordersPendingQueue;
        this.totalVendido = totalVendido;
    }

    @Override
    public void run() {
        this.es.scheduleAtFixedRate(() -> {
            try {
                String dataHora = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
                String saida = "";
                saida += "-------------------------------------------\n";
                saida += "Relatorio de vendas - "+ dataHora +"\n";
                saida +="-------------------------------------------\n";
                saida += "Pedidos processados: " + ordersCompletedQueue.size() + "\n";
                saida += "Total vendido: " + totalVendido.get() + "\n";
                saida += "Pedidos Rejeitados: " + ordersPendingQueue.size() + "\n";
                saida += "-------------------------------------------";
                System.out.println(saida);
            } catch (Exception e) {

            }
        }, 30,30, TimeUnit.SECONDS);
    }
}
