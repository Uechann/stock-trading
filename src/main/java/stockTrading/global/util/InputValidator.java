package stockTrading.global.util;

import static stockTrading.global.Exception.ErrorMessage.*;

public class InputValidator {

    // 종목 검증
    public static void validateSymbol(String symbolInput) {
        validateEmptyOrBlank(symbolInput);
        validateSymbolPattern(symbolInput);
    }


    // ============== private method =================

    private static void validateSymbolPattern(String symbolInput) {
        if (!symbolInput.matches("^[A-Z]{1,5}(,[A-Z{1,5}])$")) {
            throw new IllegalArgumentException(SYMBOL_INPUT_PATTERN_NOT_MATCH.getMessage());
        }
    }

    private static void validateEmptyOrBlank(String symbolInput) {
        if (symbolInput == null || symbolInput.isBlank()) {
            throw new IllegalArgumentException(INPUT_EMPTY_OR_BLANK.getMessage());
        }
    }
}
