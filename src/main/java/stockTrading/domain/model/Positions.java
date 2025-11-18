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

    public int getQuantity(Symbol symbol) {
        if (!values.containsKey(symbol)) {
            return 0;
        }

        return values.get(symbol).getQuantity();
    }

    // 종목 보유량 증가
    public void incrementQuantity(Symbol symbol, int quantity) {
        values.get(symbol).increaseQuantity(quantity);
    }

    // 종목 보유량 감소

    public void decrementQuantity(Symbol symbol, int quantity) {
        values.get(symbol).decreaseQuantity(quantity);
    }
}
