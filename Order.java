import enums.OrderStatus;

import java.sql.Date;
import java.util.Map;
import java.util.Random;

public class Order {
    long id;
    OrderStatus status;
    Map<String, Integer> products;

    public Order(long id, Map<String, Integer> products) {
        this.id = id;
        this.status = OrderStatus.CREATED;
        this.products = products;
    }

    public void execute(Map<String, Integer> stock) {

        boolean isPossible = true;
        for (Map.Entry<String, Integer> product : products.entrySet()) {
            Integer quantInStock = stock.get(product.getKey());

            if (quantInStock == null) {
                this.status = OrderStatus.CANCELED;
                return;
            }

            if (quantInStock < product.getValue()) {
                this.status = OrderStatus.PENDING;
                isPossible = false;
            }
        }
        if (isPossible) {
            for (Map.Entry<String, Integer> product : products.entrySet()) {
                String key = product.getKey();
                int quantInStock = stock.get(key);
                stock.put(key, quantInStock - product.getValue());
            }
            this.status = OrderStatus.COMPLETED;
        }
    }
}