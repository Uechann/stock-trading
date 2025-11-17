package stockTrading.dto;

import stockTrading.domain.model.Trade;

public record TradeSummary(
        // 매수자 매도자 종목 가격 수량
        String buyerAccountId,
        String sellerAccountId,
        String symbol,
        int price,
        int quantity
) {
    public static TradeSummary of(Trade trade) {
        return new TradeSummary(
                trade.getBuyerAccountId(),
                trade.getSellerAccountId(),
                trade.getSymbol().getName(),
                trade.getPrice(),
                trade.getQuantity()
        );
    }
}
