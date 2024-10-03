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

    Map<String, Integer> stock ;
    List<String> products;
    ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();


    public Replenisher(Map<String, Integer> stock){
        this.stock = stock;
        this.products = new ArrayList<String>();
        initializeProducts();
    }

    @Override
    public void run() {
        this.es.scheduleAtFixedRate(() -> {
            try {
                String product = this.products.get(new Random().nextInt(10));
                int quant = new Random().nextInt(50);
                this.stock.put(product,quant);
            } catch (Exception e) {
                System.out.println("Erro no orderProducer");
            }
        }, 0,10, TimeUnit.SECONDS);
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
