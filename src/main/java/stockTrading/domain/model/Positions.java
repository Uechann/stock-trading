package stockTrading.domain.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Positions {
    private final Map<Symbol, Position> values = new HashMap<>();

    public Positions() {
    }

    private Positions(List<Position> positions) {
        positions.forEach(symbol -> values.put(symbol.getSymbol(), symbol));
    }

    public static Positions create(List<Position> positions) {
        return new Positions(positions);
    }

    public void add(Position position) {
        values.put(position.getSymbol(), position);
    }

    public void applyBuy(Trade trade) {
        // 없을 때 예외 처리
        values.get(trade.getSymbol()).applyBuy(trade);
    }

    public void applySell(Trade trade) {
        // 없을 때 예외 처리
        values.get(trade.getSymbol()).applySell(trade);
    }

    public int getQuantity(Symbol symbol) {
        if (!values.containsKey(symbol)) {
            return 0;
        }

        return values.get(symbol).getQuantity();
    }

    public int getAvgCost(Symbol symbol) {
        return values.get(symbol).getAvgCost();
    }
}
