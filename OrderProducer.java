import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderProducer implements Runnable {

    BlockingQueue<Order> bq;
    AtomicInteger index;
    ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();

    int period;
    List<String> products;
    AtomicInteger totalVendido;



    public OrderProducer(BlockingQueue<Order> bq, AtomicInteger index, int period, AtomicInteger totalVendido){
        this.bq = bq;
        this.index = index;
        this.period = period;
        this.products = new ArrayList<String>();
        initializeProducts();
        this.totalVendido = totalVendido;
    }

    @Override
    public void run() {
        this.es.scheduleAtFixedRate(() -> {
            Order order = new Order(index.getAndIncrement(), generateListProducts(), totalVendido );
            try {
                this.bq.put(order);
            } catch (Exception e) {

            }
        }, 0, this.period, TimeUnit.SECONDS);
    }

    private Map<String, Integer> generateListProducts(){
        Random random = new Random();
        Map<String, Integer> listProducts = new HashMap<String, Integer>();
        int quantProducst = random.nextInt(5);
        for (int i = 0; i < quantProducst ; i++) {
            String product = this.products.get(random.nextInt(10));
            int quant = random.nextInt(10) + 1;
            listProducts.put(product, quant);
        }
        return listProducts;
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
