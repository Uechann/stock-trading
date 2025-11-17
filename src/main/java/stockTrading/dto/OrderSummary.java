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
}
