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
}
