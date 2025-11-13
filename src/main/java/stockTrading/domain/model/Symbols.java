package stockTrading.domain.model;

import java.util.List;

import static stockTrading.global.Exception.ErrorMessage.SYMBOL_DUPLICATED;
import static stockTrading.global.Exception.ErrorMessage.SYMBOL_NOT_FOUND;

public class Symbols {
    private List<Symbol> symbols;

    private Symbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    public static Symbols of(List<Symbol> symbols) {
        validate(symbols);
        return new Symbols(symbols);
    }

    public boolean contains(String symbol) {
        List<String> sumbolNames = symbols.stream()
                .map(Symbol::getName)
                .toList();

        validateContains(sumbolNames, symbol);
        return true;
    }

    // =============== private method =======================

    private void validateContains(List<String> sumbolNames, String symbol) {
        if(!sumbolNames.contains(symbol)) {
            throw new IllegalArgumentException(SYMBOL_NOT_FOUND.getMessage());
        }
    }

    private static void validate(List<Symbol> symbols) {
        int count = (int) symbols.stream()
                .map(Symbol::getName)
                .distinct()
                .count();

        if (count < symbols.size()) {
            throw new IllegalArgumentException(SYMBOL_DUPLICATED.getMessage());
        }
    }
}
