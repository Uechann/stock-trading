package stockTrading.infra;

import stockTrading.domain.model.OrderBook;
import stockTrading.domain.model.OrderBooks;
import stockTrading.domain.model.Symbol;
import stockTrading.domain.repository.OrderBookRepository;

import java.util.Optional;

public class InMemoryOrderBookRepository implements OrderBookRepository {

    private final OrderBooks orderBooks = new OrderBooks();

    @Override
    public void add(OrderBook orderBook) {
        orderBooks.add(orderBook);
    }

    @Override
    public Optional<OrderBook> findBySymbol(Symbol symbol) {
        return Optional.ofNullable(orderBooks.findBySymbol(symbol));
    }
}
