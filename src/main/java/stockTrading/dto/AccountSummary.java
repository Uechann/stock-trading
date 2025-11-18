package stockTrading.dto;

import java.util.List;

public record AccountSummary(
        // 계좌번호 자금 List<PositionSummary>
        String accountId,
        int funds,
        List<PositionSummary> positions
) {
    public static AccountSummary create(String accountId, int funds, List<PositionSummary> positions) {
        return new AccountSummary(accountId, funds, positions);
    }
}
