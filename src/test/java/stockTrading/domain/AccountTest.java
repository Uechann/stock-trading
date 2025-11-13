package stockTrading.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AccountTest {

    @Test
    @DisplayName("계좌 번호 입력 성공 테스트")
    void accountInputSuccessTest() {
        Account account = new Account("3333-12-1234567");
        assertThat(account.getId()).isEqualTo("3333-12-1234567");
    }

    @ParameterizedTest
    @ValueSource(strings = {"3333,11,1234567", "", " "})
    @DisplayName("계좌번호 입력 형식 실패 테스트")
    void accountInputFailTest(String input) {
        assertThatThrownBy(() ->
                new Account(input)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("초기 자금 초기화 성공 테스트")
    void accountFundsInitializeTest() {
        Account account = new Account("3333-12-1234567");
        account.initializeFunds(1_000_000);

        assertThat(account.getFunds()).isEqualTo(1_000_000);
    }

    @ParameterizedTest
    @ValueSource(ints = {-50000, 6000000})
    @DisplayName("초기 자금 초기화 실패 테스트")
    void accountFundsInitializeFailTest(int funds) {
        Account account = new Account("3333-12-1234567");

        assertThatThrownBy(() ->
                account.initializeFunds(funds)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
