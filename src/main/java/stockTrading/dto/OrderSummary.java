package stockTrading.dto;

public record OrderSummary(
        // 총 주문수, 체결수, 부분체결수, 취소수
        int orderCount,
        int completeCount,
        int partialTradeCount,
        int pendingCount,
        int cancelCount
) {
    public static OrderSummary create(int orderCount, int completeCount, int partialTradeCount, int pendingCount, int cancelCount) {
        return new OrderSummary(orderCount, completeCount, partialTradeCount, pendingCount, cancelCount);
    }

    // `통계: 총주문 6, 체결 4, 부분체결 1, 취소 1`
    @Override
    public String toString() {
        return String.format(
                "통계: 총 주문 %d, 체결 수 %d, 부분 체결 수 %d, 대기 수 %d, 취소 수 %d",
                orderCount, completeCount, partialTradeCount, pendingCount, cancelCount
        );
    }
}
