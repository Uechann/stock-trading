package stockTrading.domain;

import static stockTrading.global.Exception.ErrorMessage.ACCOUNT_ID_PATTERN_NOT_MATCH;
import static stockTrading.global.constant.Pattern.ACCOUNT_ID_REGEX;

public class Account {
    private String id;

    public Account(String id) {
        validate(id);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    // ==================== private method =====================

    private static void validate(String id) {
        if (!id.matches(ACCOUNT_ID_REGEX)) {
            throw new IllegalArgumentException(ACCOUNT_ID_PATTERN_NOT_MATCH.getMessage());
        }
    }
}
