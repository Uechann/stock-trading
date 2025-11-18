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
                trade.getSymbol().name(),
                trade.getPrice(),
                trade.getQuantity()
        );
    }

    @Override
    public String toString() {
        return String.format(
                "[체결] %s %d X %d (BUY: %s, SELL: %s)",
                symbol, price, quantity, buyerAccountId, sellerAccountId
        );
    }
}
