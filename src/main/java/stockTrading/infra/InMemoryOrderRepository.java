package stockTrading.infra;

import stockTrading.domain.model.Order;
import stockTrading.domain.model.Orders;
import stockTrading.domain.repository.OrderRepository;

public class InMemoryOrderRepository implements OrderRepository {

    private Orders orders = new Orders();

    @Override
    public void add(Order order) {
        orders.add(order);
    }
}
