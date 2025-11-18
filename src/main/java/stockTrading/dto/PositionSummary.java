package stockTrading.dto;

public record PositionSummary(
        // 종목 / 수량 / 평균 매입가 / 최종 체결가 / 평가 손익 / 수익률
        String symbol,
        int quantity,
        int averageCost,
        Long lastCost,
        Long profit,
        double profitRate
) {
    public static PositionSummary create(String symbol, int quantity, int averageCost, long lastCost, long profit, double profitRate) {
        return new PositionSummary(symbol, quantity, averageCost, lastCost, profit, profitRate);
    }

    @Override
    public String toString() {
        return String.format(
                "%s\t|%s\t|%d\t\t|%d\t\t|%d\t|%.2f",
                symbol, quantity, averageCost, lastCost, profit, profitRate);
    }
}
