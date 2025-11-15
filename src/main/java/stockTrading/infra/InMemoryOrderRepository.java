package stockTrading.infra;

import stockTrading.domain.model.Order;
import stockTrading.domain.repository.OrderRepository;

public class InMemoryOrderRepository implements OrderRepository {

    @Override
    public boolean add(Order order) {
        return false;
    }
}
