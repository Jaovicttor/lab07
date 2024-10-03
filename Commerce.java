import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Commerce {

    public static void main(String[] args) {

        AtomicInteger index = new AtomicInteger(0);
        AtomicInteger totalVendido = new AtomicInteger();
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        BlockingQueue<Order> ordersQueue = new LinkedBlockingDeque<Order>(100);
        BlockingQueue<Order> ordersCompletedQueue = new LinkedBlockingDeque<Order>();
        BlockingQueue<Order> ordersPendingQueue = new LinkedBlockingDeque<Order>();
        Map<String, Product> stockMap = new ConcurrentHashMap<>();
        initializeStock(stockMap);

        for (int i = 0; i < 5; i++) {
            executorService.execute(new OrderProducer(ordersQueue, index, new Random().nextInt(10) + 1, totalVendido ));
        }

        for (int i = 0; i < 3; i++) {
            executorService.execute(new Attendant( stockMap, ordersQueue, ordersCompletedQueue, ordersPendingQueue));
        }

        executorService.execute(new Replenisher(stockMap,ordersQueue, ordersPendingQueue));
        executorService.execute(new Report(stockMap, totalVendido, ordersCompletedQueue,  ordersPendingQueue));
    }

    public static void initializeStock(Map<String, Product> stock){
        stock.put("Cebola", new Product( 100, 1));
        stock.put("Pimentão", new Product( 100, 2));
        stock.put("Alface", new Product( 100, 3));
        stock.put("Pão", new Product( 100, 4));
        stock.put("Queijo", new Product( 100, 5));
        stock.put("Presunto", new Product( 100, 6));
        stock.put("Carne", new Product( 100, 7));
        stock.put("Bacon", new Product( 100, 8));
        stock.put("Frango", new Product( 100, 9));
    }

}
