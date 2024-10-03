import enums.OrderStatus;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RecursiveTask;

public class Attendant implements Runnable {
    BlockingQueue<Order> orders;
    BlockingQueue<Order> ordersCompleted;
    BlockingQueue<Order> ordersPending;
    Map<String, Product> stockMap;

    public Attendant(Map<String, Product> stockMap, BlockingQueue<Order> orders,  BlockingQueue<Order> ordersCompleted,  BlockingQueue<Order> ordersPending){
        this.stockMap = stockMap;
        this.orders = orders;
        this.ordersCompleted = ordersCompleted;
        this.ordersPending = ordersPending;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Order order = orders.take();
                order.execute(this.stockMap);
                if(order.status == OrderStatus.COMPLETED){
                    ordersCompleted.add(order);
                    System.out.println("O pedido " + order.id + " foi concluido" );
                }else {
                    System.out.println("O pedido " + order.id + " saiu da fila" );
                    ordersPending.add(order);
                }
                Thread.sleep(1000 + (long) (new Random().nextFloat() * (15000 - 1000)));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Erro no attendant");
            }
        }
    }
}