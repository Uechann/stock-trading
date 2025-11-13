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
        assertThat(account.getNumber()).isEqualTo("3333-12-1234567");
    }

    @ParameterizedTest
    @ValueSource(strings = {"3333,11,1234567", "", " "})
    @DisplayName("계좌번호 입력 형식 실패 테스트")
    void accountInputFailTest(String input) {
        assertThatThrownBy(() ->
            new Account(input)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
