package stockTrading.infra;

import stockTrading.domain.model.Symbol;
import stockTrading.domain.model.SymbolPriceProvider;

import java.util.HashMap;
import java.util.Map;

public class InMemorySymbolPrice implements SymbolPriceProvider {

    private Map<Symbol, Integer> symbolPrices = new HashMap<>();

    @Override
    public void save(Symbol symbol, int price) {
        symbolPrices.put(symbol, price);
    }

    @Override
    public int getPrice(Symbol symbol) {
        return symbolPrices.get(symbol);
    }
}
