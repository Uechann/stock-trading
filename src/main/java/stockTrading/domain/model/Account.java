package stockTrading.domain.model;

import static stockTrading.global.Exception.ErrorMessage.ACCOUNT_FUNDS_NOT_IN_RANGE;
import static stockTrading.global.Exception.ErrorMessage.ACCOUNT_ID_PATTERN_NOT_MATCH;
import static stockTrading.global.constant.Pattern.ACCOUNT_ID_REGEX;

public class Account {

    private final String id;
    private int funds;
    private AccountSymbols accountSymbols;

    public Account(String id, int funds) {
        validate(id, funds);
        this.id = id;
        this.funds = funds;
    }

    public String getId() {
        return id;
    }

    public int getFunds() {
        return funds;
    }

    public void initializeSymbolQuantities(AccountSymbols accountSymbols) {
        this.accountSymbols = accountSymbols;
    }

    public int getQuantity(Symbol symbol) {
        return accountSymbols.getQuantity(symbol);
    }

    // Trade 객체를 받아서 정산 적용
    public void applyTrade(Trade trade) {
        if (trade.getBuyerAccountId().equals(id)) {
            funds -= trade.getPrice() * trade.getQuantity();
            accountSymbols.incrementQuantity(trade.getSymbol(), trade.getQuantity());
        }

        if (trade.getSellerAccountId().equals(id)) {
            funds += trade.getPrice() * trade.getQuantity();
            accountSymbols.decrementQuantity(trade.getSymbol(), trade.getQuantity());
        }
    }

    @Override
    public boolean equals(Object obj) {
        return id.equals((String) obj);
    }

    // ==================== private method =====================

    private static void validate(String id, int funds) {
        validateId(id);
        validateFunds(funds);
    }

    private static void validateFunds(int funds) {
        if (funds < 0 || funds > 5_000_000) {
            throw new IllegalArgumentException(ACCOUNT_FUNDS_NOT_IN_RANGE.getMessage());
        }
    }

    private static void validateId(String id) {
        if (!id.matches("^" + ACCOUNT_ID_REGEX + "$")) {
            throw new IllegalArgumentException(ACCOUNT_ID_PATTERN_NOT_MATCH.getMessage());
        }
    }
}
