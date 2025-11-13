package stockTrading.domain;

import java.util.List;

import static stockTrading.global.Exception.ErrorMessage.SYMBOL_DUPLICATED;

public class Symbols {
    private List<Symbol> symbols;

    private Symbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    public static Symbols of(List<Symbol> symbols) {
        validate(symbols);
        return new Symbols(symbols);
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
