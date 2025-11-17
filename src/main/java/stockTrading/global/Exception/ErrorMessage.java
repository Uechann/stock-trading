package stockTrading.global.Exception;

public enum ErrorMessage {

    // 공통
    INPUT_EMPTY_OR_BLANK("[ERROR] 입력값이 비어있거나 공백입니다."),

    // 종목
    SYMBOL_INPUT_PATTERN_NOT_MATCH("[ERROR] 종목 입력 형식에 맞지 않습니다."),
    SYMBOL_NAME_PATTERN_NOT_MATCH("[ERROR] 종목 이름 형식에 맞지 않습니다."),
    SYMBOL_DUPLICATED("[ERROR] 종목 이름이 중복되었습니다."),
    SYMBOL_NOT_FOUND("[ERROR] 종목이 없습니다."),

    // 계좌
    ACCOUNT_ID_PATTERN_NOT_MATCH("[ERROR] 계좌 번호 형식에 맞지 않습니다."),
    ACCOUNT_DUPLICATED("[ERROR] 계좌 번호가 중복되었습니다."),
    ACCOUNT_FUNDS_PATTERN_NOT_MATCH("[ERROR] 계좌 자금 숫자 패턴에 맞지 않습니다."),
    ACCOUNT_FUNDS_NOT_IN_RANGE("[ERROR] 계좌 자금 숫자 범위에 맞지 않습니다."),
    ACCOUNT_SYMBOL_QUANTITY_NOT_MATCH("[ERROR] 계좌 종목 보유 수량에 대한 입력 형식이 맞지 않습니다."),
    ACCOUNT_SYMBOL_QUANTITY_NOT_IN_RANGE("[ERROR] 계좌 종 목 보유 수량 범위에 맞지 않습니다."),
    ACCOUNT_NOT_FOUND("[ERROR] 계좌가 존재하지 않습니다."),


    // 주문
    ORDER_PATTERN_NOT_MATCH("[ERROR] 주문 형식에 맞지 않습니다."),
    ORDER_REQUEST_SIZE_NOT_MATCH("[ERROR] 주문 요청 필요한 값이 누락되었습니다."),
    ORDER_SIDE_IS_NOT_ALLOWED("[ERROR] 주문은 매도 매수만 가능합니다."),
    ORDER_PRICE_IS_POSITIVE("[ERROR] 주문은 가격은 양수만 가능합니다."),
    ORDER_PRICE_IS_OVER("[ERROR] 주문 가격이 한도(1,000,000)를 초과하였습니다."),
    ORDER_QUANTITY_IS_POSITIVE("[ERROR] 주문 수량은 양수만 가능합니다."),
    ORDER_QUANTITY_IS_OVER("[ERROR] 주문 수량이 한도(10,000)를 초과하였습니다."),
    ORDER_TOTAL_PRICE_IS_OVER("[ERROR] 주문 총 가격이 한도(10,000,000)를 초과하였습니다."),
    ORDER_ACCOUNT_FUNDS_IS_LEAK("[ERROR] 계좌 잔고가 부족합니다."),
    ORDER_ACCOUNT_SYMBOL_QUANTITY_IS_LEAK("[ERROR] 계좌 종목 보유량이 부족합니다."),
    ORDER_NOT_FOUND("[ERROR] 해당 주문을 찾을 수 없습니다."),
    ORDER_CAN_NOT_CANCEL("[ERROR] 해당 주문을 취소할 수 없습니다."),


    // 매칭
    ORDER_BOOK_SYMBOL_NOT_FOUND("[ERROR] 해당 종목이 없습니다."),

    // 정산
    ACCOUNT_FUNDS_IS_NOT_ENOUGH("[ERROR] 정산 중 계좌 잔고가 부족합니다."),
    ACCOUNT_SYMBOL_QUANTITY_IS_NOT_ENOUGH("[ERROR] 정산 중 계좌 종목 보유량이 부족합니다."),


    // 내부 에러
    ID_SET_FAIL("[ERROR] ID 값 설정에 실패했습니다."),
    ID_ALREADY_SET("[ERROR] ID 값이 이미 설정되어 있습니다.");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
