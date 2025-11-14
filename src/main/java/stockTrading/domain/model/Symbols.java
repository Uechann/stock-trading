package stockTrading.domain.model;

import stockTrading.domain.repository.SymbolRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static stockTrading.global.Exception.ErrorMessage.SYMBOL_DUPLICATED;
import static stockTrading.global.Exception.ErrorMessage.SYMBOL_NOT_FOUND;

public class Symbols {

    private final Set<Symbol> values = new HashSet<>();

    public Symbols() {}

    public Symbols(List<Symbol> symbols) {
        validate(symbols);
        values.addAll(symbols);
    }

    public boolean contains(Symbol symbol) {
        return values.contains(symbol);
    }

    public void add(Symbol symbol) {
        values.add(symbol);
    }

    public void remove(Symbol symbol) {
        values.remove(symbol);
    }

    public Set<Symbol> getValues() {
        return Collections.unmodifiableSet(values);
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
