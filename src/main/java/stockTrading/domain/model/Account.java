package stockTrading.domain.model;

import static stockTrading.global.Exception.ErrorMessage.ACCOUNT_FUNDS_NOT_IN_RANGE;
import static stockTrading.global.Exception.ErrorMessage.ACCOUNT_ID_PATTERN_NOT_MATCH;
import static stockTrading.global.constant.Pattern.ACCOUNT_ID_REGEX;

public class Account {

    private final String id;
    private int funds;
    private Positions positions;

    private Account(String id, int funds) {
        this.id = id;
        this.funds = funds;
    }

    public static Account create(String id, int funds) {
        validate(id, funds);
        return new Account(id, funds);
    }

    public String getId() {
        return id;
    }

    public int getFunds() {
        return funds;
    }

    public void initializeSymbolQuantities(Positions positions) {
        this.positions = positions;
    }

    public int getQuantity(Symbol symbol) {
        return positions.getQuantity(symbol);
    }

    // Trade 객체를 받아서 정산 적용
    public void applyTrade(Trade trade) {
        if (trade.getBuyerAccountId().equals(id)) {
            funds -= trade.getPrice() * trade.getQuantity();
            positions.incrementQuantity(trade.getSymbol(), trade.getQuantity());
        }

        if (trade.getSellerAccountId().equals(id)) {
            funds += trade.getPrice() * trade.getQuantity();
            positions.decrementQuantity(trade.getSymbol(), trade.getQuantity());
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
