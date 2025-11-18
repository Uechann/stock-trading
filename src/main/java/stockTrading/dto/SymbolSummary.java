package stockTrading.dto;

public record SymbolSummary(
        // 체결 건수, 평균 체결가, 최종 체결가
        String symbol,
        Long tradeCount,
        double avgPrice,
        Long lastPrice
) {

    public static SymbolSummary create(String symbol, Long tradeCount, double avgPrice, Long lastPrice) {
        return new SymbolSummary(symbol, tradeCount, avgPrice, lastPrice);
    }

    // `AAPL: 체결 10건, 평균체결가 118, 최종체결가 120
    @Override
    public String toString() {
        return String.format(
                "%s: 체결 %d건, 평균체결가 %.2f, 최종 체결가 %d",
                symbol, tradeCount, avgPrice, lastPrice
        );
    }
}
