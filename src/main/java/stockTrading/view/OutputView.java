package stockTrading.view;

import stockTrading.dto.*;

import java.util.List;

public class OutputView {

    private final StringBuilder builder = new StringBuilder();

    public OutputView() {
    }

    public void printSummary(Summary summary) {
        printTradesSummary(summary.tradeSummaries());
        System.out.println();
        printAccountsSummary(summary.accountSummaries());
        System.out.println();
        printSymbolsSummary(summary.symbolSummaries());
        System.out.println();
        printOrdersSummary(summary.orderSummary());
    }

    public void printTradesSummary(List<TradeSummary> tradeSummary) {
        System.out.println("체결 결과 입니다.");
        for (TradeSummary summary : tradeSummary) {
            System.out.println(summary.toString());
        }
    }

    /*
        - `계좌 <계좌ID> : 현금 <현금잔액>`
    - `/종목 | 보유수량 | 평균 매입가 | 최종 체결가 | 평가손익 | 수익률`
    - ```
      계좌 A1 : 현금 9,996,550
      종목  | 보유수량 | 평균 매입가 | 최종 체결가 | 평가손익 | 수익률
      AAPL | 30     | 115      | 120      | 150    | 0.52%
      GOOG | 10     | 200      | 250      | 500    | 2.00%
    * */
    public void printAccountsSummary(List<AccountSummary> accountSummary) {
        System.out.println("계좌별 통계입니다.");
        for (AccountSummary summary : accountSummary) {
            System.out.println(summary.toString());
        }
    }

    //     - `AAPL: 체결 10건, 평균체결가 118, 최종체결가 120`
    public void printSymbolsSummary(List<SymbolSummary> symbolSummary) {
        System.out.println("종목별 체결 통계입니다.");
        for (SymbolSummary summary : symbolSummary) {
            System.out.println(summary.toString());
        }
    }

    public void printOrdersSummary(OrderSummary orderSummary) {
        System.out.println("전체 주문 통계입니다.");
        System.out.println(orderSummary.toString());
    }
}
