package stockTrading.domain.model;

import java.util.*;

import static stockTrading.global.Exception.ErrorMessage.SYMBOL_DUPLICATED;
import static stockTrading.global.Exception.ErrorMessage.SYMBOL_NOT_FOUND;

public class Symbols {

    private final Set<Symbol> values = new HashSet<>();

    public Symbols() {}

    private Symbols(List<Symbol> symbols) {
        values.addAll(symbols);
    }

    public static Symbols create(List<Symbol> symbols) {
        validate(symbols);
        return new Symbols(symbols);
    }

    public boolean contains(Symbol symbol) {
        return values.contains(symbol);
    }

    public void add(Symbol symbol) {
        values.add(symbol);
    }

    public List<Symbol> findAll() {
        return new ArrayList<>(values);
    }

    // =============== private method =======================

    private void validateContains(Symbol symbol) {
        if (!values.contains(symbol)) {
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
