package stockTrading.global.Exception;

public enum ErrorMessage {

    // 공통
    INPUT_EMPTY_OR_BLANK("[ERROR] 입력값이 비어있거나 공백입니다."),

    // 종목
    SYMBOL_INPUT_PATTERN_NOT_MATCH("[ERROR] 종목 입력 형식에 맞지 않습니다."),
    SYMBOL_NAME_PATTERN_NOT_MATCH("[ERROR] 종목 이름 형식에 맞지 않습니다."),
    SYMBOL_DUPLICATED("[ERROR] 종목 이름이 중복되었습니다."),

    // 계좌
    ACCOUNT_ID_PATTERN_NOT_MATCH("[ERROR] 계좌 번호 형식에 맞지 않습니다."),
    ACCOUNT_DUPLICATED("[ERROR] 계좌 번호가 중복되었습니다.");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
