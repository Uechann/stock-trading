package stockTrading.domain.repository;

import stockTrading.domain.model.Symbol;
import stockTrading.domain.model.Symbols;

import java.util.List;

import static stockTrading.global.Exception.ErrorMessage.SYMBOL_DUPLICATED;

public class SymbolRegistry {

    private final Symbols symbols;

    public SymbolRegistry(Symbols symbols) {
        this.symbols = symbols;
    }

    public boolean isContains(Symbol symbol) {
        return symbols.contains(symbol);
    }

    public void add(Symbol symbol) {
        validateDuplicated(symbol);
        symbols.add(symbol);
    }

    private void validateDuplicated(Symbol symbol) {
        if (isContains(symbol)) {
            throw new IllegalArgumentException(SYMBOL_DUPLICATED.getMessage());
        }
    }

    public List<Symbol> findAll() {
        return symbols.findAll();
    }
}
