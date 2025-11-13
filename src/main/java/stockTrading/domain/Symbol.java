package stockTrading.domain;

import static stockTrading.global.Exception.ErrorMessage.SYMBOL_NAME_PATTERN_NOT_MATCH;
import static stockTrading.global.constant.Pattern.SYMBOL_NAME_REGEX;

public class Symbol {
    private final String name;

    public Symbol(String name) {
        validate(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // ============== private method ======================

    private static void validate(String name) {
        if (!name.matches(SYMBOL_NAME_REGEX)) {
            throw new IllegalArgumentException(SYMBOL_NAME_PATTERN_NOT_MATCH.getMessage());
        }
    }
}
