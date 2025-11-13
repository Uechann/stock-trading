package stockTrading.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SymbolsTest {

    @Test
    @DisplayName("중복된 종목 입력시 예외 처리 테스트")
    void duplicateSymbolsTest() {
        Symbol symbol1 = new Symbol("AAPL");
        Symbol symbol2 = new Symbol("AAPL");

        assertThatThrownBy(() ->
                Symbols.of(List.of(symbol1, symbol2))
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
