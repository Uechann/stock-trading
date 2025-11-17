package stockTrading.dto;

import java.util.List;

public record Summary(
        List<TradeSummary> tradeSummaries,
        List<AccountSummary> accountSummaries,
        List<SymbolSummary> symbolSummaries,
        OrderSummary orderSummary
) {

    public static Summary create(
            List<TradeSummary> tradeSummaries,
            List<AccountSummary> accountSummaries,
            List<SymbolSummary> symbolSummaries,
            OrderSummary orderSummary
    ) {
        return new Summary(
                tradeSummaries,
                accountSummaries,
                symbolSummaries,
                orderSummary
        );
    }
}
