package stockTrading.dto;

public record TradeSummary(
        // 매수자 매도자 종목 가격 수량
        String buyerAccountId,
        String sellerAccountId,
        String symbol,
        int price,
        int quantity
) {

}
