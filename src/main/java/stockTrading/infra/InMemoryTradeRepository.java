package stockTrading.infra;

import stockTrading.domain.model.Trade;
import stockTrading.domain.model.Trades;
import stockTrading.domain.repository.TradeRepository;

import java.util.List;

public class InMemoryTradeRepository implements TradeRepository {

    // ID가 필요할까 ?
    // 일금 컬렉션을 도입할까 ?
    // List로 관리할까 Map으로 관리할까 ?
    private final Trades trades = new Trades();

    @Override
    public void add(Trade trade) {
        trades.add(trade);
    }

    @Override
    public List<Trade> findAll() {
        return trades.findAll();
    }
}
