package stockTrading.domain.service;

import stockTrading.domain.model.Account;
import stockTrading.domain.model.Trade;
import stockTrading.domain.repository.AccountRepository;

import java.util.List;

import static stockTrading.global.Exception.ErrorMessage.*;

public class SettlementService {

    private final AccountRepository accountRepository;

    public SettlementService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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
