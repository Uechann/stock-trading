package stockTrading.domain.service;

import stockTrading.domain.model.Account;
import stockTrading.domain.model.Symbol;
import stockTrading.domain.model.SymbolPriceProvider;
import stockTrading.domain.model.Trade;
import stockTrading.domain.repository.AccountRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static stockTrading.global.Exception.ErrorMessage.*;

public class SettlementService {

    private final AccountRepository accountRepository;
    private final SymbolPriceProvider symbolPriceProvider;

    public SettlementService(AccountRepository accountRepository, SymbolPriceProvider symbolPriceProvider) {
        this.accountRepository = accountRepository;
        this.symbolPriceProvider = symbolPriceProvider;
    }

    public void settle(List<Trade> trades) {
        // 매치 결과 Trade를 통해 매수자와 매도자의 가격과 수량 검증
        for (Trade trade : trades) {
            Account buyer = getBuyer(trade);
            Account seller = getSeller(trade);

            validateBuyerFunds(trade, buyer);
            validateSellerSymbolQuantity(trade, seller);

            buyer.applyTrade(trade);
            seller.applyTrade(trade);

            // 종목 최종 체결가 업데이트
            Map<Symbol, List<Trade>> collect = trades.stream()
                    .collect(Collectors.groupingBy(Trade::getSymbol));

            collect.forEach((symbol, tradeList) -> {
                if (!tradeList.isEmpty()) {
                    int lastPrice = tradeList.stream()
                            .max(Comparator.comparing(Trade::getCreatedAt))
                            .map(Trade::getPrice)
                            .get();

                    symbolPriceProvider.save(symbol, lastPrice);
                }
            });
        }
    }

    // =============== private method =====================

    private static void validateSellerSymbolQuantity(Trade trade, Account seller) {
        if (seller.getQuantity(trade.getSymbol()) < trade.getQuantity()) {
            throw new IllegalArgumentException(ACCOUNT_SYMBOL_QUANTITY_IS_NOT_ENOUGH.getMessage());
        }
    }

    private static void validateBuyerFunds(Trade trade, Account buyer) {
        if (buyer.getFunds() < trade.getPrice() * trade.getQuantity()) {
            throw new IllegalArgumentException(ACCOUNT_FUNDS_IS_NOT_ENOUGH.getMessage());
        }
    }

    private Account getSeller(Trade trade) {
        return accountRepository.fingById(trade.getSellerAccountId())
                .orElseThrow(() -> new IllegalArgumentException(ACCOUNT_NOT_FOUND.getMessage()));
    }

    private Account getBuyer(Trade trade) {
        return accountRepository.fingById(trade.getBuyerAccountId())
                .orElseThrow(() -> new IllegalArgumentException(ACCOUNT_NOT_FOUND.getMessage()));
    }
}
