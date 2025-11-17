package stockTrading.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trades {

    private final List<Trade> trades = new ArrayList<>();

    public Trades() {}

    public void add(Trade trade) {
        trades.add(trade);
    }

    public List<Trade> findAll() {
        return Collections.unmodifiableList(trades);
    }

    public List<Trade> findBySymbol(Symbol symbol) {
        return trades.stream()
                .filter(trade -> trade.getSymbol().equals(symbol))
                .toList();
    }
}
