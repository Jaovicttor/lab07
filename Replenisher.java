import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Replenisher implements Runnable {

    Map<String, Product> stock ;
    List<String> products;
    ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
    BlockingQueue<Order> ordersPending;
    BlockingQueue<Order> orders;


    public Replenisher(Map<String, Product> stock,BlockingQueue<Order> ordersQueue, BlockingQueue<Order> ordersPending){
        this.stock = stock;
        this.products = new ArrayList<String>();
        initializeProducts();
        this.ordersPending = ordersPending;
        this.orders = ordersQueue;
    }

    @Override
    public void run() {
        this.es.scheduleAtFixedRate(() -> {
            try {
                String product = this.products.get(new Random().nextInt(10));
                Product productInStock = stock.get(product);
                productInStock.setQuant( new Random().nextInt(50));
                returnOrderToList(product);
            } catch (Exception e) {

            }
        }, 0,10, TimeUnit.SECONDS);
    }

    private void  returnOrderToList(String product) {
        for (Order order : this.ordersPending) {
            if (order.products.keySet().stream().anyMatch(value -> value.contains(product))) {
                System.out.println("O produto " + order.id + " voltou pra fila" );
                this.orders.add(order);
            }
        }
    }


    private void initializeProducts(){
        this.products.add("Tomate");
        this.products.add("Cebola");
        this.products.add("Pimentão");
        this.products.add("Alface");
        this.products.add("Pão");
        this.products.add("Queijo");
        this.products.add("Presunto");
        this.products.add("Carne");
        this.products.add("Bacon");
        this.products.add("Frango");
    }
}
