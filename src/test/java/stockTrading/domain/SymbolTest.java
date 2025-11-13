package stockTrading.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SymbolTest {

    @Test
    @DisplayName("종목은 알파벳 대문자 1~5자리 형식이어야 한다.")
    void symbolNameTest() {
        Symbol symbol = new Symbol("AAPL");
        assertThat(symbol).isEqualsTo("AAPL");
    }

}
