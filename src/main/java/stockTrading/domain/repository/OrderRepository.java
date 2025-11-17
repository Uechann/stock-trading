package stockTrading.domain.repository;

import stockTrading.domain.model.Order;

import java.util.Optional;

public interface OrderRepository {

    // 주문 생성
    void add(Order order);

    //
    Optional<Order> findById(long id);
}
