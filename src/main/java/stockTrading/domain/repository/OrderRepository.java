package stockTrading.domain.repository;

import stockTrading.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    void add(Order order);
    Optional<Order> findById(long id);
    List<Order> findAll();
}
