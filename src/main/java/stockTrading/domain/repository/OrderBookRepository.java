package stockTrading.domain.repository;

import stockTrading.domain.model.OrderBook;
import stockTrading.domain.model.Symbol;

import java.util.Optional;

public interface OrderBookRepository {

    void add(OrderBook orderBook);
    Optional<OrderBook> findBySymbol(Symbol symbol);
}
