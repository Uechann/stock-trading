package stockTrading.domain.model;

import static stockTrading.global.Exception.ErrorMessage.SYMBOL_NAME_PATTERN_NOT_MATCH;
import static stockTrading.global.constant.Pattern.SYMBOL_NAME_REGEX;

/**
 * 값 객체 VO는 ID가 필요 없고 Repository에서 관리하고
 * 값 객체에 대한 유효성 검사 추가 등은 Registry에서 관리한다.
 */
public record Symbol(String name) {

    public Symbol {
        validate(name);
    }

    // ============== private method ======================

    private static void validate(String name) {
        if (!name.matches("^" + SYMBOL_NAME_REGEX + "$")) {
            throw new IllegalArgumentException(SYMBOL_NAME_PATTERN_NOT_MATCH.getMessage());
        }
    }
}
