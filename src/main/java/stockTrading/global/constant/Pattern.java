package stockTrading.global.constant;

public class Pattern {
    // 종목 형식 패턴
    public static final String SYMBOL_INPUT_REGEX = "^[A-Z]{1,5}(,[A-Z{1,5}])$";
    public static final String SYMBOL_NAME_REGEX = "^[A-Z]{1,5}$";
    public static final String ACCOUNT_ID_REGEX = "^[0-9]{4}-[0-9]{2}-[0-9]{7}$";

    // 숫자 형식
    public static final String ACCOUNT_FUNDS_REGEX = "^[0-9]+$";
}
