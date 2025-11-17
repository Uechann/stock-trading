package stockTrading.global.constant;

public class Pattern {
    // 종목 형식 패턴
    public static final String SYMBOL_INPUT_REGEX = "^[A-Z]{1,5}(,[A-Z]{1,5})*$";
    public static final String SYMBOL_NAME_REGEX = "[A-Z]{1,5}";
    public static final String ACCOUNT_ID_REGEX = "[0-9]{4}-[0-9]{2}-[0-9]{7}";

    // 계좌 종목 입력 형식
    public static final String ACCOUNT_SYMBOL_REGEX = "^[A-Z]{1,5}=[0-9]+(,[A-Z]{1,5}=[0-9]+)*$";

    // 숫자 형식
    public static final String NUMBER = "[0-9]+";

    // 주문 형식
    private static final String BUY_OR_SELL = "(BUY|SELL)";
    public static final String ORDER_REGEX = String.format(
            "^ORDER\\s+%s\\s+%s\\s+%s\\s+%s\\s+%s$",
            ACCOUNT_ID_REGEX, SYMBOL_NAME_REGEX, BUY_OR_SELL, NUMBER, NUMBER);
    public static final String CANCEL_REGEX = String.format(
            "^CANCEL\\s+%s$", NUMBER);
}
