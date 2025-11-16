package stockTrading.domain.model;

import java.util.HashMap;
import java.util.Map;

public class OrderBooks {

    private final Map<Symbol, OrderBook> orderBooks;

    public OrderBooks() {
        this.orderBooks = new HashMap<>();
    }

    public void add(OrderBook orderBook) {
        orderBooks.put(orderBook.getSymbol(), orderBook);
    }

    public OrderBook findBySymbol(Symbol symbol) {
        return orderBooks.get(symbol);
    }
}
