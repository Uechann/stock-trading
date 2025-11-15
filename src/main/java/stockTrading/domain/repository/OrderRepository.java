package stockTrading.domain.repository;

import stockTrading.domain.model.Order;

public interface OrderRepository {

    // 주문 생성
    boolean add(Order order);

}
