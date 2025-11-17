package stockTrading.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AccountTest {

    @Test
    @DisplayName("계좌 번호 입력 성공 테스트")
    void accountInputSuccessTest() {
        Account account = Account.create("3333-12-1234567", 0);
        assertThat(account.getId()).isEqualTo("3333-12-1234567");
    }

    @ParameterizedTest
    @ValueSource(strings = {"3333,11,1234567", "", " "})
    @DisplayName("계좌번호 입력 형식 실패 테스트")
    void accountInputFailTest(String input) {
        assertThatThrownBy(() ->
                Account.create(input, 0)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("초기 자금 초기화 성공 테스트")
    void accountFundsInitializeTest() {
        Account account = Account.create("3333-12-1234567", 1_000_000);

        assertThat(account.getFunds()).isEqualTo(1_000_000);
    }

    @ParameterizedTest
    @ValueSource(ints = {-50000, 6000000})
    @DisplayName("초기 자금 초기화 실패 테스트")
    void accountFundsInitializeFailTest(int funds) {

        assertThatThrownBy(() ->
                Account.create("3333-12-1234567", funds)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("계좌 종목 보유 수량 성공 테스트 ")
    void accountSymbolQuantitySuccessTest() {
        // given
        Account account = Account.create("3333-12-1234567", 0);
        Symbol apple = new Symbol("APPL");
        Symbol google = new Symbol("GOOG");

        // when
        AccountSymbol accountSymbol1 = AccountSymbol.create(apple, 50);
        AccountSymbol accountSymbol2 = AccountSymbol.create(google, 30);
        AccountSymbols accountSymbols = new AccountSymbols();
        accountSymbols.add(accountSymbol1);
        accountSymbols.add(accountSymbol2);

        account.initializeSymbolQuantities(accountSymbols);

        // then
        assertThat(account.getQuantity(apple)).isEqualTo(50);
        assertThat(account.getQuantity(google)).isEqualTo(30);
    }
}
