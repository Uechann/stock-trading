package stockTrading.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AccountsTest {

    @Test
    @DisplayName("중복된 계좌 번호 입력 실패 테스트")
    void duplicatedAccountsTest() {
        Account account1 = new Account("3333-12-1234567");
        Account account2 = new Account("3333-12-1234567");

        assertThatThrownBy(() ->
                Accounts.of(List.of(account1, account2))
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
