package stockTrading.domain;

import java.util.HashMap;
import java.util.Map;

import static stockTrading.global.Exception.ErrorMessage.ACCOUNT_SYMBOL_QUANTITY_NOT_IN_RANGE;

public class AccountSymbol {
    private Account account;
    private Symbol symbol;
    private int quantity;

    private AccountSymbol(Account account, Symbol symbol, int quantity) {
        this.account = account;
        this.symbol = symbol;
        this.quantity = quantity;
    }

    public Account getAccount() {
        return account;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public static AccountSymbol of(Account account, Symbol symbol, int quantity) {
        validateAccountSymbolQuantity(quantity);
        return new AccountSymbol(account, symbol, quantity);
    }

    public boolean isSameAccountId(String accountId) {
        return account.equals(accountId);
    }

    public Map<Symbol, Integer> toMap() {
        Map<Symbol, Integer> map = new HashMap<>();
        map.put(symbol, quantity);
        return map;
    }

    // =============== private metohd =====================

    private static void validateAccountSymbolQuantity(int quantity) {
        if (quantity <= 0 || quantity > 10000) {
            throw new IllegalArgumentException(ACCOUNT_SYMBOL_QUANTITY_NOT_IN_RANGE.getMessage());
        }
    }
}

