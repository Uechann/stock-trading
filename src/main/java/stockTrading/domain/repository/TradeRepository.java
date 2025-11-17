package stockTrading.domain.repository;

import stockTrading.domain.model.Symbol;
import stockTrading.domain.model.Trade;

import java.util.List;

public interface TradeRepository {

    void add(Trade trade);
    List<Trade> findAll();
    List<Trade> findBySymbol(Symbol symbol);
}
