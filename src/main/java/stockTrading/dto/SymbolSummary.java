package stockTrading.dto;

public record SymbolSummary(
        // 체결 건수, 평균 체결가, 최종 체결가
        String symbol,
        int tradeCount,
        double avgPrice,
        Long lastPrice
) {
}
