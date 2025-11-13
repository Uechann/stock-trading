package stockTrading.global.util;

import static stockTrading.global.Exception.ErrorMessage.*;
import static stockTrading.global.constant.Pattern.SYMBOL_INPUT_REGEX;

public class InputValidator {

    // 공통 빈 값만 검증하는 메서드
    public static void validateGlobalEmptyOrBlank(String input) {
        validateEmptyOrBlank(input);
    }

    // 종목 검증
    public static void validateSymbol(String symbolInput) {
        validateEmptyOrBlank(symbolInput);
        validateSymbolPattern(symbolInput);
    }

    // ============== private method =================

    private static void validateSymbolPattern(String symbolInput) {
        if (!symbolInput.matches(SYMBOL_INPUT_REGEX)) {
            throw new IllegalArgumentException(SYMBOL_INPUT_PATTERN_NOT_MATCH.getMessage());
        }
    }

    private static void validateEmptyOrBlank(String symbolInput) {
        if (symbolInput == null || symbolInput.isBlank()) {
            throw new IllegalArgumentException(INPUT_EMPTY_OR_BLANK.getMessage());
        }
    }
}
