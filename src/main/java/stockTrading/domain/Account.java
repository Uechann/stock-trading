package stockTrading.domain;

import static stockTrading.global.Exception.ErrorMessage.ACCOUNT_FUNDS_NOT_IN_RANGE;
import static stockTrading.global.Exception.ErrorMessage.ACCOUNT_ID_PATTERN_NOT_MATCH;
import static stockTrading.global.constant.Pattern.ACCOUNT_ID_REGEX;

public class Account {

    private String id;
    private int funds;

    public Account(String id) {
        validateId(id);
        this.id = id;
        this.funds = 0;
    }

    public String getId() {
        return id;
    }

    public void initializeFunds(int funds) {
        validateFunds(funds);
        this.funds = funds;
    }

    public int getFunds() {
        return funds;
    }

    // ==================== private method =====================

    private static void validateFunds(int funds) {
        if (funds < 0 || funds > 5_000_000) {
            throw new IllegalArgumentException(ACCOUNT_FUNDS_NOT_IN_RANGE.getMessage());
        }
    }

    private static void validateId(String id) {
        if (!id.matches(ACCOUNT_ID_REGEX)) {
            throw new IllegalArgumentException(ACCOUNT_ID_PATTERN_NOT_MATCH.getMessage());
        }
    }
}
