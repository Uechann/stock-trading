package stockTrading.domain.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Orders {

    private Map<UUID, Order> orders;

    public Orders() {
        orders = new HashMap<>();
    }

    public void add(Order order) {
        orders.put(order.getOrderId(), order);
    }
}
