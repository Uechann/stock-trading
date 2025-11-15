package stockTrading.global.util;

import static stockTrading.global.Exception.ErrorMessage.*;
import static stockTrading.global.constant.Pattern.*;

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

    // 자금 검증
    public static void validateFunds(String fundsInput) {
        validateEmptyOrBlank(fundsInput);
        validateAccountFundsPattern(fundsInput);
    }

    // 계좌 종목 보유 수량 검증
    public static void validateSymbolQuantity(String symbolQuantityInput) {
        validateEmptyOrBlank(symbolQuantityInput);
        validateAccountSymbolQuantityPattern(symbolQuantityInput);
    }

    // 주문 검증
    public static boolean validateOrder(String orderInput) {
        validateEmptyOrBlank(orderInput);
        validateOrderPattern(orderInput);
        return true;
    }

    // ============== private method =================

    private static void validateOrderPattern(String orderInput) {
        if (!orderInput.matches(ORDER_REGEX)) {
            throw new IllegalArgumentException(ORDER_PATTERN_NOT_MATCH.getMessage());
        }
    }

    private static void validateAccountSymbolQuantityPattern(String symbolQuantityInput) {
        if (!symbolQuantityInput.matches(ACCOUNT_SYMBOL_REGEX)) {
            throw new IllegalArgumentException(ACCOUNT_SYMBOL_QUANTITY_NOT_MATCH.getMessage());
        }
    }

    private static void validateAccountFundsPattern(String fundsInput) {
        if (!fundsInput.matches("^" + NUMBER + "$")) {
            throw new IllegalArgumentException(ACCOUNT_FUNDS_PATTERN_NOT_MATCH.getMessage());
        }
    }

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
