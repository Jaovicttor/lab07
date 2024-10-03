import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Commerce {

    public static void main(String[] args) {

        AtomicInteger index = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(15);

        BlockingQueue<Order> ordersQueue = new LinkedBlockingDeque<Order>(50);
        Map<String, Integer> stockMap = new ConcurrentHashMap<>();
        initializeStock(stockMap);

        for (int i = 0; i < 10; i++) {
            executorService.execute(new OrderProducer(ordersQueue, index, new Random().nextInt(10) + 1));
        }

        for (int i = 0; i < 3; i++) {
            executorService.execute(new Attendant(ordersQueue, stockMap));
        }

        executorService.execute(new Replenisher(stockMap));
        executorService.execute(new Report(stockMap));


    }

    public static void initializeStock(Map<String, Integer> stock){
        stock.put("Cebola", 100);
        stock.put("Pimentão", 100);
        stock.put("Alface", 100);
        stock.put("Pão", 100);
        stock.put("Queijo", 100);
        stock.put("Presunto", 100);
        stock.put("Carne", 100);
        stock.put("Bacon", 100);
        stock.put("Frango", 100);
    }
}
