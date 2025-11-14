package stockTrading.domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountSymbols {
    private final Map<Symbol, AccountSymbol> values = new HashMap<>();

    public AccountSymbols() {
    }

    public AccountSymbols(List<AccountSymbol> accountSymbols) {
        accountSymbols.forEach(symbol -> values.put(symbol.getSymbol(), symbol));
    }

    public void add(AccountSymbol accountSymbol) {
        values.put(accountSymbol.getSymbol(), accountSymbol);
    }

    public int getQuantity(Symbol symbol) {
        if (!values.containsKey(symbol)) {
            return 0;
        }

        return values.get(symbol).getQuantity();
    }
}
