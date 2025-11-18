package stockTrading.domain.model;

import static stockTrading.global.Exception.ErrorMessage.ACCOUNT_SYMBOL_QUANTITY_NOT_IN_RANGE;

public class Position {
    private Symbol symbol;
    private int quantity;
    private int totalPrice; // 총 매입 원가 금액 (매입가 * 수량)의 합산

    private Position(Symbol symbol, int quantity) {
        this.symbol = symbol;
        this.quantity = quantity;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public static Position create(Symbol symbol, int quantity) {
        validateAccountSymbolQuantity(quantity);
        return new Position(symbol, quantity);
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void decreaseQuantity(int quantity) {
        this.quantity -= quantity;
    }

    // =============== private metohd =====================

    private static void validateAccountSymbolQuantity(int quantity) {
        if (quantity <= 0 || quantity > 10000) {
            throw new IllegalArgumentException(ACCOUNT_SYMBOL_QUANTITY_NOT_IN_RANGE.getMessage());
        }
    }
}

