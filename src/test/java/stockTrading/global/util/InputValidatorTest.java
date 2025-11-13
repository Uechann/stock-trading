package stockTrading.global.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class InputValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("입력값은 비어있거나 빈칸일 경우 예외 처리")
    void inputEmplyOrBlankTest(String input) {

        assertThatThrownBy(() ->
                InputValidator.validateSymbol(input)
        ).isInstanceOf(IllegalArgumentException.class);

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "APPL,GOOG,msft", "AAPL,GOOG,MSFT123",
            "APPL,,GOOG", ",APPL,GOOG", "AAPL,GOOG,"
    })
    @DisplayName("종목 입력값 형식 실패 테스트")
    void inputSymbolPatternTest(String input) {
        assertThatThrownBy(() ->
                InputValidator.validateSymbol(input)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "abc123", "asdf"})
    @DisplayName("자금 입력이 빈값, 공백이거나 숫자가 아니면 예외 처리 테스트")
    void inputFundsPatternTest(String input) {
        assertThatThrownBy(() ->
                InputValidator.validateFunds(input)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
