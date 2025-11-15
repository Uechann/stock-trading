package stockTrading.domain.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static stockTrading.global.Exception.ErrorMessage.ACCOUNT_DUPLICATED;

public class Accounts {

    private Map<String, Account> values = new HashMap<>();

    public Accounts() {}

    public Accounts(List<Account> accounts) {
        accounts.forEach(account -> values.put(account.getId(), account));
    }

    public static Accounts of(List<Account> accounts) {
        validate(accounts);
        return new Accounts(accounts);
    }

    // 중복 검사
    public boolean contains(Account account) {
        return values.containsKey(account.getId());
    }

    // 계좌 추가
    public void add(Account account) {
        values.put(account.getId(), account);
    }

    // 계좌번호로 조회
    public Account findById(String id) {
        return values.get(id);
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
