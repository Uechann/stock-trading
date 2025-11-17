package stockTrading.domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Orders {

    private Map<Long, Order> orders;

    public Orders() {
        orders = new HashMap<>();
    }

    public void add(Order order) {
        orders.put(order.getOrderId(), order);
    }

    public Order findById(long id) {
        return orders.get(id);
    }

    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }
}
