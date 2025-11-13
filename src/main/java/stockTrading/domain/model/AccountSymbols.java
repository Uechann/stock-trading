package stockTrading.domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountSymbols {
    private List<AccountSymbol> accountSymbols;

    public AccountSymbols() {
        accountSymbols = new ArrayList<>();
    }

    public void add(AccountSymbol accountSymbol) {
        accountSymbols.add(accountSymbol);
    }

    public Map<Symbol, Integer> findByAccountId(String accountId) {
        Map<Symbol, Integer> result = new HashMap<>();

        accountSymbols.stream()
                .filter(a -> a.isSameAccountId(accountId))
                .map(AccountSymbol::toMap)
                .forEach(result::putAll);

        return result;
    }
}
