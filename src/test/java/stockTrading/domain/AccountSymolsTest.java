package stockTrading.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AccountSymolsTest {

    @Test
    @DisplayName("계좌 종목 보유 수량 성공 테스트 ")
    void accountSymbolQuantitySuccessTest() {
        // given
        Account account = new Account("3333-12-1234567");
        Symbol apple = new Symbol("APPL");
        Symbol google = new Symbol("GOOG");
        // 계좌 -> 종목 소유 (개수)
        // 계좌-종목 중간 클래스 ? 양방향 ? 단방향 ?

        // when
        AccountSymbol accountSymbol1 = AccountSymbol.of(account, apple, 50);
        AccountSymbol accountSymbol2 = AccountSymbol.of(account, google, 30);
        AccountSymbols accountSymbols = new AccountSymbols();
        accountSymbols.add(accountSymbol1);
        accountSymbols.add(accountSymbol2);

        // then
        Map<Symbol, Integer> result = accountSymbols.findByAccountId(account.getId());
        assertThat(result.get(apple)).isEqualTo(50);
        assertThat(result.get(google)).isEqualTo(30);
    }
}
