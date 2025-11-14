package stockTrading.domain.model;

import org.assertj.core.data.MapEntry;

import java.util.HashMap;
import java.util.Map;

import static stockTrading.global.Exception.ErrorMessage.ACCOUNT_SYMBOL_QUANTITY_NOT_IN_RANGE;

public class AccountSymbol {
    private Symbol symbol;
    private int quantity;

    private AccountSymbol(Symbol symbol, int quantity) {
        this.symbol = symbol;
        this.quantity = quantity;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public static AccountSymbol of(Symbol symbol, int quantity) {
        validateAccountSymbolQuantity(quantity);
        return new AccountSymbol(symbol, quantity);
    }

    // =============== private metohd =====================

    private static void validateAccountSymbolQuantity(int quantity) {
        if (quantity <= 0 || quantity > 10000) {
            throw new IllegalArgumentException(ACCOUNT_SYMBOL_QUANTITY_NOT_IN_RANGE.getMessage());
        }
    }
}

