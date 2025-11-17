package stockTrading.domain.service;

import stockTrading.domain.model.Account;
import stockTrading.domain.model.Order;
import stockTrading.domain.model.Trade;
import stockTrading.domain.repository.AccountRepository;
import stockTrading.domain.repository.OrderRepository;
import stockTrading.domain.repository.SymbolRegistry;
import stockTrading.domain.repository.TradeRepository;
import stockTrading.dto.*;

import java.util.List;

public class SummaryService {

    private final AccountRepository accountRepository;
    private final SymbolRegistry symbolRegistry;
    private final OrderRepository orderRepository;
    private final TradeRepository tradeRepository;

    public SummaryService(AccountRepository accountRepository, SymbolRegistry symbolRegistry,
                          OrderRepository orderRepository, TradeRepository tradeRepository) {
        this.accountRepository = accountRepository;
        this.symbolRegistry = symbolRegistry;
        this.orderRepository = orderRepository;
        this.tradeRepository = tradeRepository;
    }

    public Summary summarize() {
        List<TradeSummary> tradeSummaries = summarizeTrades();
        List<AccountSummary> accountSummaries = summarizeAccounts();
        List<SymbolSummary> symbolSummaries = summarizeSymbols();
        OrderSummary orderSummary = summarizeOrders();
        return Summary.create(tradeSummaries, accountSummaries, symbolSummaries, orderSummary);
    }

    public List<TradeSummary> summarizeTrades() {
        List<Trade> trades = tradeRepository.findAll();
        return trades.stream()
                .map(TradeSummary::of)
                .toList();
    }

    public List<AccountSummary> summarizeAccounts() {

    }

    public List<SymbolSummary> summarizeSymbols() {

    }

    public OrderSummary summarizeOrders() {

    }
}
