package stockTrading.dto;

public record PositionSummary(
        // 종목 / 수량 / 평균 매입가 / 최종 체결가 / 평가 손익 / 수익률
        String symbol,
        int quantity,
        double averageCost,
        Long lastCost,
        Long profit,
        double profitRate
) {
}
