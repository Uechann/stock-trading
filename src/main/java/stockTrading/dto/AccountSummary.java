package stockTrading.dto;

import java.util.List;
import java.util.stream.Collectors;

public record AccountSummary(
        // 계좌번호 자금 List<PositionSummary>
        String accountId,
        int funds,
        List<PositionSummary> positions
) {
    public static AccountSummary create(String accountId, int funds, List<PositionSummary> positions) {
        return new AccountSummary(accountId, funds, positions);
    }

    /*
    *         - `계좌 <계좌ID> : 현금 <현금잔액>`
    - `/종목 | 보유수량 | 평균 매입가 | 최종 체결가 | 평가손익 | 수익률`
    - ```
      계좌 A1 : 현금 9,996,550
      종목  | 보유수량 | 평균 매입가 | 최종 체결가 | 평가손익 | 수익률
      AAPL | 30     | 115      | 120      | 150    | 0.52%
      GOOG | 10     | 200      | 250      | 500    | 2.00%
    * */
    @Override
    public String toString() {
        String accountAndFunds = String.format("계좌 %s : 자금 %,3d", accountId, funds);
        String column = "종목\t\t|보유량\t|평균 매입가\t|최종 체결가\t|평가손익\t|수익률\t";
        String positionOutput = positions.stream()
                .map(PositionSummary::toString)
                .collect(Collectors.joining("\n"));

        return String.join("\n", accountAndFunds, column, positionOutput);
    }
}
