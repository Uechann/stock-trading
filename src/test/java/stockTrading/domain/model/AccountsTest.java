package stockTrading.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AccountsTest {

    @Test
    @DisplayName("중복된 계좌 번호 입력 실패 테스트")
    void duplicatedAccountsTest() {
        Account account1 = Account.create("3333-12-1234567", 0);
        Account account2 = Account.create("3333-12-1234567", 0);

        assertThatThrownBy(() ->
                Accounts.create(List.of(account1, account2))
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
