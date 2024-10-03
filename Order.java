import enums.OrderStatus;

import java.sql.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {
    long id;
    OrderStatus status;
    Map<String, Integer> products;
    AtomicInteger totalVendido;

    public Order(long id, Map<String, Integer> products, AtomicInteger totalVendido) {
        this.id = id;
        this.status = OrderStatus.CREATED;
        this.products = products;
        this.totalVendido = totalVendido;
    }

    public void execute(Map<String, Product> stock) {

        boolean isPossible = true;
        for (Map.Entry<String, Integer> product : products.entrySet()) {
            Product productInStock = stock.get(product.getKey());
            if (productInStock == null) {
                this.status = OrderStatus.CANCELED;
                return;
            }
            int quantInStock = productInStock.getQuant();
            if (quantInStock < product.getValue()) {
                this.status = OrderStatus.PENDING;
                isPossible = false;
            }
        }
        if (isPossible) {
            for (Map.Entry<String, Integer> product : products.entrySet()) {
                String key = product.getKey();
                Product productInStock = stock.get(key);
                totalVendido.addAndGet(product.getValue() * productInStock.getValor());
                productInStock.setQuant( productInStock.getQuant() - product.getValue());
            }
            this.status = OrderStatus.COMPLETED;
        }
    }
}