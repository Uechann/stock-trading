package stockTrading.infra;

import stockTrading.domain.model.Account;
import stockTrading.domain.model.Accounts;
import stockTrading.domain.repository.AccountRepository;

import static stockTrading.global.Exception.ErrorMessage.ACCOUNT_DUPLICATED;

public class InMemoryAccountRepository implements AccountRepository {

    private final Accounts accounts = new Accounts();

    @Override
    public void add(Account account) {
        validateDuplicated(account);
        accounts.add(account);
    }

    // ============== private metohd ==================

    private void validateDuplicated(Account account) {
        if (accounts.contains(account)) {
            throw new IllegalArgumentException(ACCOUNT_DUPLICATED.getMessage());
        }
    }
}
