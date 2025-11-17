package stockTrading.domain.service;

import stockTrading.domain.model.Order;
import stockTrading.domain.model.OrderBook;
import stockTrading.domain.model.Trade;
import stockTrading.domain.repository.OrderBookRepository;

import java.util.List;

import static stockTrading.global.Exception.ErrorMessage.ORDER_BOOK_SYMBOL_NOT_FOUND;

public class MatchingService {

    // OrderBook 레포
    // Trading 레포 의존성
    private final OrderBookRepository orderBookRepository;

    public MatchingService(OrderBookRepository orderBookRepository) {
        this.orderBookRepository = orderBookRepository;
    }


    public List<Trade> match(Order order) {
        // symbol 을 통해 OrderBook 찾고 orderBook.match() 실행
        OrderBook orderBook = findOrderBookBySymbol(order);

        orderBook.add(order);
        return orderBook.match();
    }

    // ============= private method ====================

    private OrderBook findOrderBookBySymbol(Order order) {
        return orderBookRepository.findBySymbol(order.getSymbol())
                .orElseThrow(() -> new IllegalArgumentException(ORDER_BOOK_SYMBOL_NOT_FOUND.getMessage()));
    }
}
