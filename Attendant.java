import enums.OrderStatus;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RecursiveTask;

public class Attendant implements Runnable {
    BlockingQueue<Order> bq;
    Map<String, Integer> stockMap;

    public Attendant(BlockingQueue<Order> bq, Map<String, Integer> stockMap){
        this.bq = bq;
        this.stockMap = stockMap;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Order order = bq.take();
                order.execute(this.stockMap);
                if(order.status == OrderStatus.COMPLETED){
                    System.out.println("Pedido " + order.id + " concluido");
                }else {
                    System.out.println("Pedido " + order.id + " falhou");
                }
                Thread.sleep(1000 + (long) (new Random().nextFloat() * (15000 - 1000)));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Erro no attendant");
            }
        }
    }
}