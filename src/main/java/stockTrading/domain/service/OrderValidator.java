package stockTrading.domain.service;

import stockTrading.domain.model.Account;
import stockTrading.domain.model.Symbol;

import static stockTrading.global.Exception.ErrorMessage.*;

public class OrderValidator {

    private static final int PRICE_UNDER = 0;
    private static final int PRICE_UPPER = 1_000_000;
    private static final int QUANTITY_UNDER = 0;
    private static final int QUANTITY_UPPER = 10_000;
    private static final int TOTAL_PRICE_UPPER = 10_000_000;

    public OrderValidator() {}

    public void validate(Account account, Symbol symbol, String side, int price, int quantity) {
        // BUY SELL 검증
        validateSide(side);
        validatePrice(price);
        validateQuantity(quantity);
        validateTotalPrice(price, quantity);
        validateAccountFunds(account, side, price, quantity);
        validateSymbolQuantity(account, symbol, side, quantity);
    }

    private static void validateSide(String side) {
        if (!side.equals("BUY") || !side.equals("SELL")) {
            throw new IllegalArgumentException(ORDER_SIDE_IS_NOT_ALLOWED.getMessage());
        }
    }

    // =============== private method ==================

    private static void validateTotalPrice(int price, int quantity) {
        if (price * quantity > TOTAL_PRICE_UPPER) {
            throw new IllegalArgumentException(ORDER_TOTAL_PRICE_IS_OVER.getMessage());
        }
    }

    private static void validateAccountFunds(Account account, String side, int price, int quantity) {
        if (side.equals("BUY") && account.getFunds() < price * quantity) {
            throw new IllegalArgumentException(ORDER_ACCOUNT_FUNDS_IS_LEAK.getMessage());
        }
    }

    private static void validateSymbolQuantity(Account account, Symbol symbol, String side, int quantity) {
        if (side.equals("SELL") && account.getQuantity(symbol) < quantity) {
            throw new IllegalArgumentException(ORDER_ACCOUNT_SYMBOL_QUANTITY_IS_LEAK.getMessage());
        }
    }

    private static void validateQuantity(int quantity) {
        validateQuantityUnder(quantity);
        validateQuantityUpper(quantity);
    }

    private static void validateQuantityUnder(int quantity) {
        if (quantity <= QUANTITY_UNDER) {
            throw new IllegalArgumentException(ORDER_QUANTITY_IS_POSITIVE.getMessage());
        }
    }

    private static void validateQuantityUpper(int quantity) {
        if (quantity > QUANTITY_UPPER) {
            throw new IllegalArgumentException(ORDER_QUANTITY_IS_OVER.getMessage());
        }
    }

    private static void validatePrice(int price) {
        validatePriceUnder(price);
        validatePriceUpper(price);
    }

    private static void validatePriceUnder(int price) {
        if (price <= PRICE_UNDER) {
            throw new IllegalArgumentException(ORDER_PRICE_IS_POSITIVE.getMessage());
        }
    }

    private static void validatePriceUpper(int price) {
        if (price > PRICE_UPPER) {
            throw new IllegalArgumentException(ORDER_PRICE_IS_OVER.getMessage());
        }
    }
}
