package stockTrading.domain.service;

import stockTrading.domain.model.Order;
import stockTrading.domain.model.OrderBook;
import stockTrading.domain.model.OrderStatus;
import stockTrading.domain.model.Trade;
import stockTrading.domain.repository.OrderBookRepository;
import stockTrading.domain.repository.OrderRepository;

import java.util.List;

import static stockTrading.global.Exception.ErrorMessage.*;

public class TradeService {

    // OrderBook 레포
    // Trading 레포 의존성
    private final OrderRepository orderRepository;
    private final OrderBookRepository orderBookRepository;

    public TradeService(OrderRepository orderRepository, OrderBookRepository orderBookRepository) {
        this.orderRepository = orderRepository;
        this.orderBookRepository = orderBookRepository;
    }

    public List<Trade> match(Order order) {
        // symbol 을 통해 OrderBook 찾고 orderBook.match() 실행
        OrderBook orderBook = findOrderBookBySymbol(order);

        orderBook.add(order);
        return orderBook.match();
    }

    public void cancelOrder(Long orderId) {
        Order order = findOrderById(orderId);

        // 취소할 수 없는 상황이면 예외 처리
        validateOrderStatus(order);
        order.cancel();

        // OrderBook에서 제거
        OrderBook orderBook = findOrderBook(order);
        orderBook.removeOrder(orderId);
    }

    // ============= private method ====================

    private static void validateOrderStatus(Order order) {
        if (!order.getStatus().equals(OrderStatus.PENDING)) {
            throw new IllegalArgumentException(ORDER_CAN_NOT_CANCEL.getMessage());
        }
    }

    private OrderBook findOrderBook(Order order) {
        return orderBookRepository.findBySymbol(order.getSymbol())
                .orElseThrow(() -> new IllegalArgumentException(ORDER_BOOK_SYMBOL_NOT_FOUND.getMessage()));
    }

    private OrderBook findOrderBookBySymbol(Order order) {
        return orderBookRepository.findBySymbol(order.getSymbol())
                .orElseThrow(() -> new IllegalArgumentException(ORDER_BOOK_SYMBOL_NOT_FOUND.getMessage()));
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException(ORDER_NOT_FOUND.getMessage()));
    }
}
