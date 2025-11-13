package stockTrading.domain;

import java.util.List;

import static stockTrading.global.Exception.ErrorMessage.ACCOUNT_DUPLICATED;

public class Accounts {
    private List<Account> accounts;

    private Accounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public static Accounts of(List<Account> accounts) {
        validate(accounts);
        return new Accounts(accounts);
    }

    // ============== private method ===================

    private static void validate(List<Account> accounts) {
        int count = (int) accounts.stream()
                .map(Account::getId)
                .distinct()
                .count();

        if(count < accounts.size()) {
            throw new IllegalArgumentException(ACCOUNT_DUPLICATED.getMessage());
        }
    }
}
