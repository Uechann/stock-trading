package stockTrading.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PositionTest {

    // given
    Symbol symbol = new Symbol("AAPL");

    @Test
    @DisplayName("계좌 종목 보유 수량 입력 성공 테스트 ")
    void AccountSymbolInputSuccessTest() {

        // when
        Position position = Position.create(symbol, 10000, 5);

        // then
        assertThat(position.getSymbol()).isEqualTo(symbol);
        assertThat(position.getQuantity()).isEqualTo(5);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 100000})
    @DisplayName("계좌 종목 보유 수량이 0 이하 10,000 초과일시 예외 처리")
    void AccountSymbolInputFailTest(int quantity) {

        assertThatThrownBy(() ->
                Position.create(symbol, 10000, quantity)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
