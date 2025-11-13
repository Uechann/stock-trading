package stockTrading.domain;

import stockTrading.global.Exception.ErrorMessage;

import static stockTrading.global.Exception.ErrorMessage.SYMBOL_NAME_PATTERN_NOT_MATCH;

public class Symbol {
    private final String name;

    public Symbol(String name) {
        validate(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private static void validate(String name) {
        if (!name.matches("^[A-Z]{1,5}$")) {
            throw new IllegalArgumentException(SYMBOL_NAME_PATTERN_NOT_MATCH.getMessage());
        }
    }
}
